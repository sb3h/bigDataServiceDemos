package com.hhh.temp.tools;

import redis.clients.jedis.Jedis;

/**
 * Created by huanghh on 2017/5/22.
 */
public class RedisTools {

    private static Jedis jedis = new Jedis("192.168.101.121");

    public static Jedis getJedis() {
        return jedis;
    }
}
