package com.afterqcd.study.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by afterqcd on 2017/4/11.
 */
public class StringDemo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis.app.svc.fxsoft.co");
        jedis.set("foo", "bar");
        System.out.println(jedis.get("foo"));
        jedis.del("foo");
    }
}
