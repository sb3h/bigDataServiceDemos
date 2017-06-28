package com.hhh.temp.bean

import com.google.gson.Gson

/**
  * Created by huanghh on 2017/5/24.
  * 用户ID	sessionID	访问产品ID	访问的产品类别ID	访问时间
  */
class HotProduct(
                  //都弄成相同就好了
                  var userId: Long, var orderId: Long,
                  //随机金额
                  var amount: Long,
                  //随机
                  // productCategory :String,
                  var product: String,
                  var buyArea: String
                ) extends BaseBean {
  /*//都弄成相同就好了
  userId :Long
  orderId :Long
  //随机金额
  amount :Long
  //随机
  productCategory :String
  product :String
  area :String*/


  override def toString: String = new Gson().toJson(this, this.getClass)
}
