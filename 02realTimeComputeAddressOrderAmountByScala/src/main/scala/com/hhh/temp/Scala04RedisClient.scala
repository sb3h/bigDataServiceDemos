package com.hhh.temp

import com.hhh.temp.tools.RedisTools
import redis.clients.jedis.Jedis

/**
  * Created by huanghh on 2017/5/22.
  */
object Scala04RedisClient {
  private val jedis: Jedis = RedisTools.getJedis

  def main(args: Array[String]) {
//    testConnect()
//      testSetStr();
    testHgetAll()
  }

  private def testHgetAll() {
    val map = jedis.hgetAll("hash_address_amount_key")
    System.out.println(map)
  }

  private def testSetStr() {
    val str_key_hello: String = "str_key_hello"
    jedis.set(str_key_hello, "广州")
    val value: String = jedis.get(str_key_hello)
    System.out.println(value)
  }

  /**
    * 测试不通过的话，就需要去检查redis是否打开保护模式
    */
  private def testConnect() {
    //连接本地的 Redis 服务
    System.out.println("Connection to server sucessfully")
    //查看服务是否运行
    System.out.println("Server is running: " + jedis.ping)
  }
}
