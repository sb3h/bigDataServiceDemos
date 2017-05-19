package com.hhh.temp

import java.text.SimpleDateFormat
import java.util.Date

import com.google.gson.Gson
import com.hhh.temp.bean.Order

/**
  * Created by huanghh on 2017/5/19.
  */
object Scala02JsonToOrder {
  def main(args: Array[String]) {
    val json: String = "{\"id\":9,\"orderAmount\":737,\"address\":\"东莞\",\"date\":1495094289510}"
    val clazz: Class[Order] = classOf[Order]
    val gson: Gson = new Gson
    val order: Order = gson.fromJson(json, clazz)
    val sdf: SimpleDateFormat = new SimpleDateFormat("yyyyMMdd")
    val key: String = String.format("%s_%s", order.getAddress, sdf.format(new Date(order.getDate)))
    val value: Int = order.getOrderAmount
    System.out.println(key)
    System.out.println(value)
  }
}
