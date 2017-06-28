package com.hhh.temp.spark

import com.hhh.temp.hive.SHiveJdbcClient
import com.hhh.temp.tools.HiveTools
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

/**
  * Created by huanghh on 2017/5/24.
  */
class S03SparkBegin2HiveTest {


  @Test def test00temp(): Unit = {
    val conn = SHiveJdbcClient.getConn

    val doWhatMsg = "";
    val sql = s"";
    executeSQL(doWhatMsg, sql)
  }

  @Test def test01GetAllData(): Unit = {
    val conn = SHiveJdbcClient.getConn

    val doWhatMsg = "";
//    val sql = s"select buyarea from default.${HiveTools.tableName} where year='2017' and month='06' and day='11'"
    val sql = "select buyarea from hot_product where year='2017' and month='5' and day='11'"
    executeSQL(doWhatMsg, sql)
  }

  def executeSQL(doWhatMsg: String, sql: String): Unit = {
    val sConf = new SparkConf()
      .setMaster("local[4]")
//      .setMaster("spark://master-node:7077")//test is not use cluster
      .setAppName("ScalaSparkComputeHive")
    val sc = new SparkContext(sConf)
    val hc = new HiveContext(sc)

    println(s"execute:$sql")

    hc.sql(sql).collect().foreach(println)
  }


}
