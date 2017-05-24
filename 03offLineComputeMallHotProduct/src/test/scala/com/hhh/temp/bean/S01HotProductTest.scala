package com.hhh.temp.bean

import com.hhh.temp.tools.{DataTools, RandomTools}
import org.apache.commons.lang.math.RandomUtils
import org.json.JSONObject
import org.junit.Test

/**
  * Created by huanghh on 2017/5/24.
  */
class S01HotProductTest {
  @Test def testToString() {
    val bean: HotProduct = new HotProduct(1, 1,
      RandomTools.getRandomI(500, 1000),
      DataTools.getRandomProduct(),
      DataTools.getRandomArea()
    );
    println(s"$bean")
  }

  @Test def testGetField(): Unit = {
    //    val clazz = classOf[HotProduct]
    //
    //    for(field <- clazz.getDeclaredFields){
    //      println(field)
    //    }
    //    println(clazz.getSimpleName)
    val bean: HotProduct = new HotProduct(1, 1,
      RandomTools.getRandomI(500, 1000),
      DataTools.getRandomProduct(),
      DataTools.getRandomArea()
    );

    val json = new JSONObject(bean.toString)
    val keys = json.keys
    while (keys.hasNext) {
      val key = keys.next()
      println(key)
    }
  }
}
