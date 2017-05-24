package com.hhh.temp.bean

import java.sql.{Connection, Statement}

import com.hhh.temp.hive.SHiveJdbcClient
import com.hhh.temp.tools.{DataTools, RandomTools, StringTools}
import org.json.JSONObject
import org.junit.Test

/**
  * Created by huanghh on 2017/5/24.
  */
class S02HiveTest {
  private val tableName: String = "hot_product"

  @Test def test00temp(): Unit = {
    val conn = SHiveJdbcClient.getConn

    val doWhatMsg = "";
    val sql = s"";
    executeSQL(conn, doWhatMsg, sql)
  }

  /**
    * 不能用，因为hive不支持delete和update操作
    */
  @Test def test04clearTableData(): Unit = {
    val conn = SHiveJdbcClient.getConn

    val doWhatMsg = "clear table";
    val sql = s"delete from $tableName where 1=1";
    executeSQL(conn, doWhatMsg, sql)
  }

  @Test def test03GetData(): Unit = {
    val conn = SHiveJdbcClient.getConn

    val doWhatMsg = "";
    val sql = s"select * from $tableName";
    executeSQL(conn, doWhatMsg, sql)
  }

  @Test def test02InsertSingleData() {
    val conn = SHiveJdbcClient.getConn

    val bean: HotProduct = new HotProduct(1, 1,
      RandomTools.getRandomI(500, 1000),
      DataTools.getRandomProduct(),
      DataTools.getRandomArea()
    );

    val json = new JSONObject(bean.toString)
    val keys = json.keys

    val valueSB = new StringBuilder
    val columnSB = new StringBuilder

    while (keys.hasNext) {
      val key = keys.next()
      val value = json.getString(String.valueOf(key))
      //      println(key)
      columnSB.append(key).append(",")
//      valueSB.append(s"decode(binary('$value'),'utf-8')").append(",")
      valueSB.append("\"").append(new String(value.getBytes(),"utf-8")).append("\"").append(",")
    }
    //    println(StringTools.getWithOutLastChar(columnSB))
    //    println(StringTools.getWithOutLastChar(valueSB))
    val columns =  StringTools.getWithOutLastChar(columnSB).toLowerCase()
    val values = StringTools.getWithOutLastChar(valueSB)

    val doWhatMsg = "insert data";
    val sql = s"insert into $tableName($columns) values($values)";
    executeSQL(conn, doWhatMsg, sql)
  }


  @Test def test01CreateTable() {
    val conn = SHiveJdbcClient.getConn

    val bean: HotProduct = new HotProduct(1, 1,
      RandomTools.getRandomI(500, 1000),
      DataTools.getRandomProduct(),
      DataTools.getRandomArea()
    );

    val json = new JSONObject(bean.toString)
    val keys = json.keys

    val columnSB = new StringBuilder;
    while (keys.hasNext) {
      val key = keys.next()
      columnSB.append(key).append(" string,")
    }
    val columnSQL = columnSB.toString().substring(0, columnSB.length - 1);
    //    println(columnSQL)

    val sql = s"create table if not exists $tableName($columnSQL) ROW FORMAT DELIMITED FIELDS TERMINATED BY ','"
    //    println(sql)
    //
    val doWhatMsg = "create table";
    executeSQL(conn, doWhatMsg, sql)

  }

  def executeSQL(conn: Connection, doWhatMsg: String, sql: String): Unit = {
    val stat: Statement = conn.createStatement()
    try {
      println(s"execute:$sql")
      val resultSet = stat.execute(sql);
      if (resultSet) {
        println(s"$doWhatMsg success by has ResultSet")
        val rs = stat.getResultSet
        while (rs.next) {
          println(rs.getString(1))
        }
      } else {
        println(s"$doWhatMsg success")
      }
    } catch {
      case ex: Exception => {
        println(ex.printStackTrace())
      }
    }
    endDbWork(conn, stat)
  }

  def endDbWork(conn: Connection, stat: Statement): Any = {
    stat.close()
    conn.close()
  }
}
