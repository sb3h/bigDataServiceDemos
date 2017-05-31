package com.hhh.temp.bean

import org.apache.spark.sql.SparkSession
import org.junit.Test


/**
  * Created by huanghh on 2017/5/24.
  */
class S04SparkAfter2HiveTest {
  private val tableName: String = "hot_product"
  //  private val encoding: String = "latin1"
  private val in_encoding: String = "latin1"
  private val out_encoding: String = "utf-8"


  @Test def test00temp(): Unit = {

    val doWhatMsg = "";
    val sql = s"";
    executeSQL(doWhatMsg, sql)
  }

  @Test def test01GetAllData(): Unit = {

    val doWhatMsg = "";
    val sql = s"select * from $tableName"
    executeSQL(doWhatMsg, sql)
  }


  def executeSQL(doWhatMsg: String, execute_sql: String): Unit = {
    val warehouseLocation = "file:${system:user.dir}/spark-warehouse"

    val spark = SparkSession
      .builder()
      .master("local[4]")
      .appName("Spark Hive Example")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._
    import spark.sql


    println("execute:"+execute_sql)

    sql(execute_sql)
      //.show()
      .collect().foreach(println)
  }


}
