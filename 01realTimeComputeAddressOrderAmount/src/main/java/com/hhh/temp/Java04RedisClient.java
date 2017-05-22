package com.hhh.temp;

import com.hhh.temp.tools.RedisTools;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

/**
 * Created by huanghh on 2017/5/18.
 */
public class Java04RedisClient {

    private static  Jedis jedis = RedisTools.getJedis();

    public static void main(String[] args) {
        testConnect();


//        testSetStr();
        testHash();
    }

    private static void testHash() {
        Map map = jedis.hgetAll("hash_address_amount_key");
        System.out.println(map);
    }

    private static void testSetStr() {
        String str_key_hello = "str_key_hello";
        jedis.set(str_key_hello, "say hello");

        String value = jedis.get(str_key_hello);

        System.out.println(value);
    }

    /**
     * 测试不通过的话，就需要去检查redis是否打开保护模式
     */
    private static void testConnect() {
        //连接本地的 Redis 服务
        System.out.println("Connection to server sucessfully");
        //查看服务是否运行
        System.out.println("Server is running: "+jedis.ping());
    }
}
