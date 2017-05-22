package com.hhh.temp.bean

import com.google.gson.Gson
import java.io.Serializable

/**
  * Created by huanghh on 2017/5/16.
  */
object Order {
  val thisC: Class[_] = classOf[Order]
  val thisName: String = thisC.getSimpleName

  def main(args: Array[String]) {
    System.out.println("Order")
  }
}

class Order(id: Int, var orderAmount: Int, var address: String) extends BaseBean(id) with Serializable {

  private var date: Long = System.currentTimeMillis

  def this(orderAmount: Int, address: String) {
    this(0, orderAmount, address)
  }

  override def toString: String = new Gson().toJson(this, this.getClass)

  def getOrderAmount: Int = {
    if (orderAmount == 0) orderAmount = 1
    return orderAmount
  }

  def getAddress: String = address

  def getDate: Long = date

  def setOrderAmount(orderAmount: Int) {
    this.orderAmount = orderAmount
  }

  def setAddress(address: String) {
    this.address = address
  }

  def setDate(date: Long) {
    this.date = date
  }
}
