package com.hhh.temp.hive

import java.sql.{Connection, DriverManager, ResultSet, SQLException, Statement}


/**
  * Created by huanghh on 2017/5/24.
  */
object SHiveJdbcClient {
  private val driverName: String = "org.apache.hive.jdbc.HiveDriver"

  /**
    * @param args
    * @throws SQLException
    */
  @throws[SQLException]
  def main(args: Array[String]) {
    try
      Class.forName(driverName)

    catch {
      case e: ClassNotFoundException => {
        // TODO Auto-generated catch block
        e.printStackTrace()
        System.exit(1)
      }
    }
    //replace "hive" here with the name of the user the queries should run as
    val con: Connection = DriverManager.getConnection("jdbc:hive2://package:10000", "root", "")
    val stmt: Statement = con.createStatement
    val sql: String = "select * from psn5"
    val res: ResultSet = stmt.executeQuery(sql)
    while (res.next) {
      System.out.println(res.getString(1))
      System.out.println(res.getString(2))
    }
  }
}
