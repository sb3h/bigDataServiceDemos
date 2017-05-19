package com.hhh.temp

import com.google.gson.Gson
import com.hhh.temp.bean.Order
import kafka.javaapi.producer.Producer
import kafka.producer.KeyedMessage
import kafka.producer.ProducerConfig
import java.util.Properties
import com.hhh.temp.tools.RandomTools._

/**
  * Created by huanghh on 2017/5/16.
  */
object Scala01KafkaProduct {
  @throws[InterruptedException]
  def main(args: Array[String]) {
    System.out.println("com.hhh.temp.Scala01KafkaProduct")
    val addresss: Array[String] = Array("广州", "深圳", "珠海", "汕头", "韶关", "佛山", "江门", "湛江", "茂名", "肇庆", "梅洲", "汕尾", "河源", "阳江", "清远", "东莞", "中山", "潮州", "揭阳", "云浮")
    val minI: Int = 500
    val maxI: Int = 1000
    var address: String = ""
    var randomI: Int = 0
    val producer: Producer[Integer, String] = initKafkaProducer
//    var i: Int = 0
//    while (i < 100) {
//      {
//        //            randomI = getRandomI(minI, maxI);
//        address = addresss(getRandomI(addresss.length))
//        //为了测试程序是否正确，特意写这样
//        randomI = 1
//        //            address = addresss[0];
//        val order: Order = new Order(i, randomI, address)
//        //            System.out.println(order);
//        Thread.sleep(10)
//        System.out.println(order)
//        sendToKafka(order, producer)
//      }
//      {
//        i += 1; i - 1
//      }
//    }
    for(i <- 0 until 100){
      randomI = getRandomI(minI, maxI);
      address = addresss(getRandomI(addresss.length))
      //为了测试程序是否正确，特意写这样
      randomI = 1
      //            address = addresss[0];
      val order: Order = new Order(i, randomI, address)
      //            System.out.println(order);
      Thread.sleep(10)
      System.out.println(order)
      sendToKafka(order, producer)
    }

    producer.close
  }

  private def initKafkaProducer: Producer[Integer, String] = {
    //设置配置文件
    val props: Properties = new Properties
    props.put("metadata.broker.list", "192.168.101.121:9092")
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    props.put("serializer.encoding", "UTF-8")
    //        props.put("request.required.acks", "1");
    //创建Producer
    val producer: Producer[Integer, String] = new Producer[Integer, String](new ProducerConfig(props))
    producer
  }

  private def sendToKafka(order: Order, producer: Producer[Integer, String]) {
    val TOPIC: String = "myTopic"
    //发送数据
    //        producer.send(new KeyedMessage<Integer, String>(TOPIC, "Message_"+order.toString()));//暂时使用没有格式的数据
    producer.send(new KeyedMessage[Integer, String](TOPIC, "" + order.toString))
  }
}
