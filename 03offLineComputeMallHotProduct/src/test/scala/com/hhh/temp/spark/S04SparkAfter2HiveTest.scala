package com.hhh.temp.spark

import com.hhh.temp.tools.HiveTools
import org.apache.spark.sql.SparkSession
import org.junit.Test


/**
  * Created by huanghh on 2017/5/24.
  */
class S04SparkAfter2HiveTest {

  @Test def test00temp(): Unit = {

    val doWhatMsg = "";
    val sql = s"";
    executeSQL(doWhatMsg, sql)
  }

  @Test def test01GetAllData(): Unit = {

    val doWhatMsg = "";
    val sql =
      s"""
         |select count(buyarea) from default.${HiveTools.tableName}
         | where 1=1
         | and year='2017'
         | and month='5'
         | and day='11'
       """.stripMargin
    //    val sql = "select buyarea from hot_product where year='2017' and month='5' and day='11'"
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

    import spark.sql


    println("execute:" + execute_sql)

    //    val data =
    sql(execute_sql)
      //      .show()
      .collect()
      .foreach(println)
  }


}
