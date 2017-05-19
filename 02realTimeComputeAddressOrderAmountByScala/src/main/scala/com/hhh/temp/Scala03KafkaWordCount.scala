package com.hhh.temp



import java.text.SimpleDateFormat
import java.util
import java.util.Date

import com.google.gson.Gson
import com.hhh.temp.bean.Order
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
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1L))
      .reduceByKeyAndWindow(_ + _, _ - _, Minutes(10), Seconds(2), 2)
    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }

  private val jedis: Jedis = new Jedis("192.168.101.121")
}
