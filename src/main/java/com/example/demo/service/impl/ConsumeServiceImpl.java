package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.config.R;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.Consume;
import com.example.demo.pojo.User;
import com.example.demo.service.ConsumeService;
import com.example.demo.mapper.ConsumeMapper;
import com.example.demo.service.UserService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Service
public class ConsumeServiceImpl extends ServiceImpl<ConsumeMapper, Consume>
        implements ConsumeService{
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ConsumeMapper consumeMapper;
    @Override
    public R comsume(Integer id, String money) {
        //锁
        RLock lock = redissonClient.getLock("comsume:" + id);
        //最多等待锁五秒
        try {
            boolean b = lock.tryLock(5, TimeUnit.SECONDS);
            //如果没有获取到写锁
            if (!b)
            {
                return R.error("消费失败");
            }
            else
            {
                //获取到写锁,判断余额是不是大于消费额度
                //通过id查询用户信息,由于此处数据库字段只有id和deposit，故直接查询，不指定查询特定字段
                User user = userService.getById(id);
                //余额
                BigDecimal deposit = user.getDeposit();
                //消费金额
                BigDecimal tempMoney = new BigDecimal(money);
                //余额大于消费额度
                int i = deposit.compareTo(tempMoney);
                //消费成功
                if(i>=0)
                {
                    //减去消费额度
                    BigDecimal subtract = deposit.subtract(tempMoney);
                    user.setDeposit(subtract);
                    //lambda表达式
                    LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
                    //选择用户id
                    userWrapper.eq(User::getId,id);
                    //修改金额
                    userMapper.update(user,userWrapper);

                    //增加消费记录
                    Consume consume = new Consume();
                    //用户id
                    consume.setId(id);
                    //消费金额
                    consume.setConsumption(tempMoney);
                    //表示消费
                    consume.setSign((short) 1);
                    //消费时间
                    consume.setTTime(LocalDateTime.now());
                    //此时额度
                    consume.setDeposit(subtract);
                    //插入表
                    consumeMapper.insert(consume);
                    return R.success("消费成功");
                }
                else
                {
                    return R.error("额度不足");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //释放锁
            lock.unlock();
        }
        return null;
    }
    //退款
    @Override
    public R refund(Integer id, String money) {
        //通过id查询用户信息,由于此处数据库字段只有id和deposit，故直接查询，不指定查询特定字段
        User user = userService.getById(id);
        //余额
        BigDecimal deposit = user.getDeposit();
        //退款金额
        BigDecimal tempMoney = new BigDecimal(money);
        //加退款额度
        BigDecimal add = deposit.add(tempMoney);
        user.setDeposit(add);
        //lambda表达式
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        //选择用户id
        userWrapper.eq(User::getId,id);
        //修改金额
        userMapper.update(user,userWrapper);

        //增加消费记录
        Consume consume = new Consume();
        //用户id
        consume.setId(id);
        //退款金额
        consume.setConsumption(tempMoney);
        //表示退款
        consume.setSign((short) 0);
        //消费时间
        consume.setTTime(LocalDateTime.now());
        //此时额度
        consume.setDeposit(add);
        //插入表
        consumeMapper.insert(consume);
        return R.success("退款成功");
    }

    @Override
    public R detail(Integer id) {
        LambdaQueryWrapper<Consume> consumeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //根据id按时间降序查询用户钱包金额变动明细
        consumeLambdaQueryWrapper.eq(Consume::getId,id).orderByDesc(Consume::getTTime);
        List<Consume> consumes = consumeMapper.selectList(consumeLambdaQueryWrapper);
        return R.success(consumes);
    }
}













