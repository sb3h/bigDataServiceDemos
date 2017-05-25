package com.hhh.temp

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.hive.HiveContext

/**
  * Created by huanghh on 2017/5/25.
  */
object ScalaSparkComputeHive {
  def main(args: Array[String]): Unit = {
    val sConf = new SparkConf()
        .setMaster("local[4]")
        .setAppName("ScalaSparkComputeHive")
    val sc = new SparkContext(sConf)
    val hc = new HiveContext(sc)

    hc.sql("show databases").collect().foreach(println)
    hc.sql("show tables").collect().foreach(println)

    sc.stop()
  }
}
