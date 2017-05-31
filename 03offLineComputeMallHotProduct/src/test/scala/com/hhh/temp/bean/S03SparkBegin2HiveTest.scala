package com.hhh.temp.bean

import java.sql.{Connection, Statement}

import com.hhh.temp.hive.SHiveJdbcClient
import com.hhh.temp.tools.{DataTools, HiveTools, RandomTools, StringTools}
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import org.json.JSONObject
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
    val sql = s"select * from $HiveTools.tableName"
    executeSQL(doWhatMsg, sql)
  }

  def executeSQL(doWhatMsg: String, sql: String): Unit = {
    val sConf = new SparkConf()
      .setMaster("local[4]")
      .setAppName("ScalaSparkComputeHive")
    val sc = new SparkContext(sConf)
    val hc = new HiveContext(sc)

    println(s"execute:$sql")

    //    hc.sql(sql).collect().foreach(println)
  }


}
