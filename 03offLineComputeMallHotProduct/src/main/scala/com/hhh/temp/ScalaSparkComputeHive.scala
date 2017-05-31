package com.hhh.temp

import com.hhh.temp.tools.HiveTools
import org.apache.spark.sql.SparkSession


/**
  * Created by huanghh on 2017/5/25.
  */
object ScalaSparkComputeHive {
  val warehouseLocation = "file:${system:user.dir}/spark-warehouse"


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


    val execute_sql = s"select * from ${HiveTools.tableName}"
    println("execute:" + execute_sql)

//    sql(execute_sql)
//      .show()

    val dataFromHive = sql(execute_sql).collect()
    dataFromHive.map(i =>
      println(i.getAs("product"))
    )
  }
}
