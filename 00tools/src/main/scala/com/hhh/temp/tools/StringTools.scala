package com.hhh.temp.tools

/**
  * Created by huanghh on 2017/5/24.
  */
object StringTools {
  def getWithOutLastChar(fieldSB: StringBuilder): String = {
    fieldSB.substring(0, fieldSB.length - 1)
  }
}
