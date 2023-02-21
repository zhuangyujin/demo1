package com.example.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.redisson.config.Config;
//redis锁
@Configuration
public class RedissonConfig {
    @Bean
    public RedissonClient redissonClient()
    {
        Config config = new Config();
        //配置redis单机信息
        config.useSingleServer().setAddress("redis://175.178.236.116:6379").setPassword("mdjfbzyj515");
        //创建redisson客户端
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}