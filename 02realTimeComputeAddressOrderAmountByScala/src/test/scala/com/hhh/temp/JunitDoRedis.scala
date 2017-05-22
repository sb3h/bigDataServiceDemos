package com.hhh.temp

import com.hhh.temp.tools.RedisTools
import org.junit.Test
import redis.clients.jedis.Jedis
import java.util.Set
import scala.collection.JavaConversions._

/**
  * Created by huanghh on 2017/5/22.
  */
class JunitDoRedis {
  private val jedis: Jedis = RedisTools.getJedis

  @Test def testInitRedis() {
    jedis.del("hash_address_amount_key")
    val keys :Set[String] = jedis.keys("*")
    if(keys == null){
      return
    }
    for(key:String <- keys){
      println(key)
    }
  }
}
