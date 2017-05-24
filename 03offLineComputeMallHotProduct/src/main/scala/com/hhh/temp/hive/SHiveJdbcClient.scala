package com.hhh.temp.hive

import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet, SQLException, Statement}


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
    doSqlByHive("select * from psn5")
  }

  def doSqlByHive(sql:String): Unit = {
    val con: Connection = getConn
    val stmt: Statement = con.createStatement()
//    val sql: String = sql
    val res: ResultSet = stmt.executeQuery(sql);
    while (res.next) {
      System.out.println(res.getString(1))
    }
    res.close()
    stmt.close()
    con.close()
  }

  def getConn: Connection = {
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
    con
  }
}
