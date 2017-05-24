package com.hhh.temp.tools

import com.hhh.temp.tools.RandomTools._
/**
  * Created by huanghh on 2017/5/24.
  */
object DataTools {
  val areas: Array[String] = Array("广州", "深圳", "珠海", "汕头", "韶关", "佛山", "江门", "湛江", "茂名", "肇庆", "梅洲", "汕尾", "河源", "阳江", "清远", "东莞", "中山", "潮州", "揭阳", "云浮")

  def getRandomArea() :String = {
    val area = areas(getRandomI(areas.length))
    area
  }

//  def main(args: Array[String]): Unit = {
//    println(getRandomArea())
//  }
}
