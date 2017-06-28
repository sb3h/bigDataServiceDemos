package com.hhh.temp.bean

import com.google.gson.Gson

/**
  * Created by huanghh on 2017/5/19.
  */
class BaseBean() {
  private var time :Long = System.currentTimeMillis()

  def setTime(time :Long): Unit ={
    this.time = time;
  }
}

