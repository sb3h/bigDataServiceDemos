package com.hhh.temp

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.hive.HiveContext

/**
  * Created by huanghh on 2017/5/25.
  */
object ScalaSparkComputeHive {
  val warehouseLocation = "file:${system:user.dir}/spark-warehouse"

  private val tableName: String = "hot_productF"

  val sql = s"select * from $tableName"

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[4]")
      .appName("Spark Hive Example")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._
    import spark.sql


//    println("execute:"+execute_sql)
//
//    sql(execute_sql)
  }
}
