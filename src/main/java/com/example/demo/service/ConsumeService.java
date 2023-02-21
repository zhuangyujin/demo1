package com.example.demo.service;

import com.example.demo.config.R;
import com.example.demo.pojo.Consume;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface ConsumeService extends IService<Consume> {

    R comsume(Integer id, String money);

    R refund(Integer id, String money);

    R detail(Integer id);
}
