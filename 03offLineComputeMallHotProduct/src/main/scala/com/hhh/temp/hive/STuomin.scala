package com.hhh.temp.hive

import org.apache.hadoop.hive.ql.exec.UDF
import org.apache.hadoop.io.Text

/**
  * Created by huanghh on 2017/5/24.
  */
object STuomin {
  def main(args: Array[String]) {
    System.out.println(getTuomin("xiaoming"))
  }

  def getTuomin(str: String): String = {
    var afterTuomin: String = ""
    val first: Char = str.charAt(0)
    val last: Char = str.charAt(str.length - 1)
    val sb: StringBuffer = new StringBuffer
    sb.append(first)
    var i: Int = 0
    while (i < str.length - 2) {
      {
        sb.append("*")
      }
      {
        i += 1; i - 1
      }
    }
    sb.append(last)
    afterTuomin = sb.toString
    afterTuomin
  }
}

class STuomin extends UDF{
  def evaluate(s: Text): Text = {
    if (s == null) return null
    val str: String = s.toString
    val afterTuomin: String = STuomin.getTuomin(str)
    System.out.println(afterTuomin)
    new Text(s.toString.toLowerCase)
  }


}