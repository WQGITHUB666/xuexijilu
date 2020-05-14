package com.imooc.miaosha.domain.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

@Service
public class RedisService {
    @Autowired
    JedisPool jedisPool;
    @Autowired
    RedisConfig redisConfig;

    public <T> T get(Prefix prefix,String key,Class<T>clazz){
        Jedis jedis=null;
        try{
            jedisPool.getResource();
            String str=jedis.get(key);
            T t=StringToBran(str);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }
    public <T> T stringToBean(String str){
        return null;
    }
    private void returnToPool(Jedis jedis){
        if(jedis!=null){
            jedis.close();
        }
    }
    @Bean
    public JedisPool JedisPoolFactory(){
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxIdlle(redisConfig.getPoolMaxIdlle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait()*1000);
        JedisPool jp=new JedisPool(poolConfig,redisConfig.getHost(),redisConfig.getPort(),
                redisConfig.getTimeout()*1000,redisConfig.getPassword());
        return  jp;
    }
    private <T>String beanToString(T value){
        if(value==null){
            return null;
        }
        Class<?>clazz=value.getClass();
        if(clazz==int.clazz||clazz==Integer.class){
            return ""+value;
        }else if(clazz==String.class){
            return (String)value;
        }else if(clazz==long.clazz||clazz==Long.class){
            return ""+value;
        }else{
            return JSON.toJSONString(value);
        }
        return JSON.toJSONString(object);
    }
    private <T> T stringToBean(String str,Class<T>clazz){
        if(str==null||str.length()<=0||clazz==null){
            return null;
        }
        if(clazz==int.clazz||clazz==Integer.class){
            return (T)Integer.valueOf(str);
        }else if(clazz==String.class){
            return (T)str;
        }else if(clazz==long.clazz||clazz==Long.class){
            return (T)Long.valueOf(str);
        }else{
            return JSON.toJavaObject(JSON.parseObject(str,clazz));
        }
        return null);
    }

    }
    public <T> boolean get(String key,T value){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            String str=beanToString(value);
            if(str==null||str.length()<=0){
                return false;
            }
            jedis.set(key,value);
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

}
