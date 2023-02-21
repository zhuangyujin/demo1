package com.example.demo.controller;

import com.example.demo.config.R;
import com.example.demo.service.ConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consume")
public class ConsumeController {
    @Autowired
    ConsumeService consumeService;
    //消费
        @PutMapping("/consumption")
    public R comsume(@RequestParam("id")Integer id,@RequestParam("money")String money)
    {
        return consumeService.comsume(id,money);
    }
    //退款
    @PutMapping("refund")
    public R refund(@RequestParam("id")Integer id,@RequestParam("money")String money)
    {
        return consumeService.refund(id,money);
    }
    //消费明细
    @RequestMapping("/detail")
    public R detail(@RequestParam("id")Integer id)
    {
        return consumeService.detail(id);
    }
}
