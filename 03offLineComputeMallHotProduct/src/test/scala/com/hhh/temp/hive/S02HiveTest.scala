package com.hhh.temp.hive

import java.sql.{Connection, Statement}
import java.util.Calendar

import com.hhh.temp.bean.HotProduct
import com.hhh.temp.tools.{DataTools, HiveTools, RandomTools, StringTools}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FSDataOutputStream, FileSystem, Path}
import org.json.JSONObject
import org.junit.{After, Before, Test}

/**
  * Created by huanghh on 2017/5/24.
  */
class S02HiveTest {

  val tableName = "hot_product"

  var fs :FileSystem = null;

  @Before def init(): Unit = {
    fs = FileSystem.get(new Configuration)
  }

  @After def end(): Unit = {
    fs.close()
  }

  @Test def test00temp(): Unit = {


    val doWhatMsg = "";
    val sql = s"";
    executeSQL(doWhatMsg, sql)
  }

  @Test def test08loadDataMonthByData(): Unit = {
    for(i <- 1 to 30){
//      println(i)
      loadDataByDay(i)
    }

  }


  @Test def test07createExternalTable(): Unit = {


    val doWhatMsg = "create ";
    val sql =
      s"""
         |CREATE EXTERNAL TABLE hot_product(
         |  buyArea string,
         |  buyAmount string,
         |  buyProduct string,
         |  orderId string,
         |  timeLong string,
         |  userId string
         |  )
         |  PARTITIONED BY(year string, month string, day string)
         |   ROW FORMAT DELIMITED
         |   FIELDS TERMINATED BY ','
         |  location '/etldata/datacenter.db/temp/hot_product'
       """.stripMargin;
    executeSQL(doWhatMsg, sql)
  }

  /**
    * 不能用，因为hive不支持delete和update操作,所以如果是外部表的话，可以手动使用hdfs去删除数据
    */
  @Test def test04clearTableData(): Unit = {

    val doWhatMsg = "clear table";
    //    val sql = s"delete from $tableName where 1=1";
    //    executeSQL(doWhatMsg, sql)
    val pathStr = "/etldata/datacenter.db/temp/hot_product"
    val path = new Path(pathStr)
    val childFileS = fs.listFiles(path,false)
    while(childFileS.hasNext){
      val childFile = childFileS.next()

      if(fs.exists(childFile.getPath)){
        fs.delete(childFile.getPath,true)
      }
    }



  }

  @Test def test03GetData(): Unit = {
    val conn = SHiveJdbcClient.getConn

    val doWhatMsg = "";
    val sql = s"select * from $tableName";
    executeSQL(doWhatMsg, sql)
  }

  def executeSQL(doWhatMsg: String, sql: String): Unit = {
    val conn = SHiveJdbcClient.getConn
    val stat: Statement = conn.createStatement()
    try {
      println(s"execute:$sql")
      val resultSet = stat.execute(sql);
      if (resultSet) {
        println(s"$doWhatMsg success by has ResultSet")
        val rs = stat.getResultSet
        while (rs.next) {
          println(new String(rs.getString(1).getBytes(), HiveTools.out_encoding))
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

  @Test def test06insertDatas(): Unit = {
    for (i <- 0 until 100) {
      val bean: HotProduct = new HotProduct(i, i,
        RandomTools.getRandomI(500, 1000),
        DataTools.getRandomProduct(),
        DataTools.getRandomArea()
      );

      /** 这样插入语句好慢，不知道等到何年何月才插入一百条数据,还是用load data的方式插入吧 */
      insertSingleData(bean, false)
    }
  }

  def insertSingleData(bean: HotProduct, isPrint: Boolean): Unit = {

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
      valueSB.append("\"").append(new String(value.getBytes(), HiveTools.in_encoding)).append("\"").append(",")
    }
    //    println(StringTools.getWithOutLastChar(columnSB))
    //    println(StringTools.getWithOutLastChar(valueSB))
    val columns = StringTools.getWithOutLastChar(columnSB).toLowerCase()
    val values = StringTools.getWithOutLastChar(valueSB)

    val doWhatMsg = "insert data";
    val sql = s"insert into $tableName($columns) values($values)";
    if (isPrint) {
      println(sql)
    } else {
      executeSQL(doWhatMsg, sql)
    }
  }

  def writeFile(fSys: FileSystem, path: Path, b: Array[Byte]) = {
    val out: FSDataOutputStream = fSys.create(path)
    out.write(b)
    out.close()
  }

  /*
    提取数据，
    写成文件，再操作hdfs上次到某个路径，再调用jdbc进行操作hive来load data
    * */
  @Test def test05LoadData(): Unit = {
    loadDataByDay(1)

  }

  def loadDataByDay(day:Int): Unit = {
    var valuesReturn = new StringBuilder;


    for (i <- 0 until 100) {
      val bean: HotProduct = new HotProduct(i, i,
        RandomTools.getRandomI(500, 1000),
        DataTools.getRandomProduct(),
        DataTools.getRandomArea()
      );
      val json = new JSONObject(bean.toString)
      val keys = json.keys


      val columnSB = new StringBuilder
      val valueSB = new StringBuilder
      while (keys.hasNext) {
        val key = keys.next()
        val value = json.getString(String.valueOf(key))
        //      println(key)
        columnSB.append(key).append(",")
        //      valueSB.append(s"decode(binary('$value'),'utf-8')").append(",")
        valueSB
          //          .append("\"")
          .append(new String(value.getBytes(), HiveTools.out_encoding))
          //          .append("\"")
          .append(",")
      }
      //      val calendar = Calendar.getInstance()
      //      valueSB.append(calendar.get(Calendar.YEAR)).append(",")
      //      valueSB.append(RandomTools.getRandomI(1, 12)).append(",")
      //      valueSB.append(calendar.get(Calendar.DAY_OF_MONTH)).append(",")
      //      val columns = StringTools.getWithOutLastChar(columnSB).toLowerCase()
      val values = StringTools.getWithOutLastChar(valueSB)
      //    println(StringTools.getWithOutLastChar(columnSB))
      valuesReturn.append(values).append("\n")
    }

    val outVal = valuesReturn.toString()
    println(outVal)
    var outPathStr = "/etldata/datacenter.db/temp/hot_product/data"
    val outPath = new Path(outPathStr);

    if (fs.exists(outPath)) {
      fs.delete(outPath, true);
    }
    writeFile(fs, outPath, outVal.getBytes("utf-8"))


    val doWhatMsg = s"load data into table $tableName";
    val c = Calendar.getInstance()
    val sql =
      s"""
         |load data inpath '$outPathStr'
         |into table $tableName
         |partition (
         |year='${c.get(Calendar.YEAR)}',
         |month='${c.get(Calendar.MONTH)}',
         |day='${day}'
         |)
       """.stripMargin
    //         |day='${c.get(Calendar.DAY_OF_MONTH)}'
    executeSQL(doWhatMsg, sql)
  }

  @Test def test02InsertSingleData() {

    val bean: HotProduct = new HotProduct(1, 1,
      RandomTools.getRandomI(500, 1000),
      DataTools.getRandomProduct(),
      DataTools.getRandomArea()
    );
    insertSingleData(bean, false)


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
    executeSQL(doWhatMsg, sql)

  }
}
