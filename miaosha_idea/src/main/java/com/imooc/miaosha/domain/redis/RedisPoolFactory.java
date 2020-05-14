package com.imooc.miaosha.domain.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisPoolFactory {

    @Autowired
    RedisConfig redisConfig;
}
