package com.imooc.miaosha.domain.redis;

public interface KeyPrefix {
    public int expireSeconds();
    public String getPrefix();
}
