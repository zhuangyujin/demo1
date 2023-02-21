package com.example.demo.controller;

import com.example.demo.config.R;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    //查看用户钱包
    @RequestMapping
    public R find(@RequestParam("id")Integer id)
    {
        //通过id查询用户信息,由于此处数据库字段只有id和deposit，故直接查询，不指定查询特定字段
        User user = userService.getById(id);
        //获取金钱
        BigDecimal deposit = user.getDeposit();
        //返回额度
        String r = deposit.toString();
        //返回结果
        return R.success(r);
    }
}
