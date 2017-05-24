package com.hhh.temp.tools

import redis.clients.jedis.Jedis

/**
  * Created by huanghh on 2017/5/22.
  */
object RedisTools {
  private val jedis: Jedis = new Jedis("192.168.101.121");
  def getJedis(): Jedis = {
    jedis
  }
}
