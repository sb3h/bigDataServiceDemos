package com.hhh.temp



import java.text.SimpleDateFormat
import java.util
import java.util.Date

import com.google.gson.Gson
import com.hhh.temp.bean.Order
import com.hhh.temp.tools.RedisTools
import org.apache.commons.lang.StringUtils
import org.apache.spark.SparkConf
import org.apache.spark.api.java.function.{PairFunction, VoidFunction}
import org.apache.spark.examples.streaming.StreamingExamples
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Duration, Minutes, Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils
import redis.clients.jedis.Jedis

/**
  * Created by huanghh on 2017/5/19.
  */
object Scala03KafkaWordCount {

  @throws[InterruptedException]
  def main(_args: Array[String]) {
    //        if (args.length < 4) {
    //            System.err.println("Usage: KafkaWordCount <zkQuorum> <group> <topics> <numThreads>");
    //            System.exit(1);
    //        }
    var master: String = ""
    if (_args.length > 0) master = _args(0)
    else master = "local[4]"

    val zkQuorum: String = "192.168.101.121:2181"
    val group: String = "test-consumer-group"
    val topics: String = "myTopic"
    val numThreads: String = "1"

    StreamingExamples.setStreamingLogLevels()

//    val Array(zkQuorum, group, topics, numThreads) = args
    val sparkConf = new SparkConf().setMaster(master).setAppName("Scala03KafkaWordCount")
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    ssc.checkpoint("checkpoint")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
//    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
//    val words = lines.flatMap(_.split(" "))
//    val wordCounts = words.map(x => (x, 1L))
//      .reduceByKeyAndWindow(_ + _, _ - _, Minutes(10), Seconds(2), 2)

    val messages = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap)

    val orders = messages.map( tuple2 => {
      val line = tuple2._2
      System.out.println("messages.map-line:" + line)

      var json: String = ""
      if (line.startsWith("{")) json = line
      else throw new Exception("data format is not OK,Please check your data format is json")

      System.out.println("messages.map-json:" + json)
      val gson: Gson = new Gson
      val clazz: Class[Order] = classOf[Order]
      //转换为order
      val order: Order = gson.fromJson(json, clazz)
      order
    })
    val sdf: SimpleDateFormat = new SimpleDateFormat("yyyyMMdd")

    val words = orders.map((order ) => (
      String.format("%s_%s", order.getAddress, sdf.format(new Date(order.getDate))),
      order.getOrderAmount)
      /*{
      System.out.println("wordCounts:" + order)

      //                //在转化为相应的词
      val key: String = String.format("%s_%s", order.getAddress, sdf.format(new Date(order.getDate)))

      return (key, order.getOrderAmount)
    }*/)

    val wordCounts = words.reduceByKey(_ + _)

    wordCounts.foreachRDD(wordCount => {
      val output = wordCount.collect
      val hashKey: String = "hash_address_amount_key"
      val len: Long = jedis.hlen(hashKey)

      var key: String = ""
      var count: Int = 0
      import scala.collection.JavaConversions._
      for (tuple <- output) {
        key = tuple._1
        count = tuple._2
        System.out.println("key:" + key)
        System.out.println("count:" + count)
        var value: String = ""
        if (len > 0) {
          var redis_value: String = jedis.hget(hashKey, key)
          if (StringUtils.isEmpty(redis_value)) redis_value = "0"
          value = String.format("%s", (redis_value.toInt + count)+ "")
        }
        else value = String.valueOf(count)
        jedis.hset(hashKey, key, value)
      }
    })

    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }

  private val jedis: Jedis = RedisTools.getJedis()
}
