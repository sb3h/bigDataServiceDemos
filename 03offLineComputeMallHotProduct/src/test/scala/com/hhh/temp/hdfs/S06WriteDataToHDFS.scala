package com.hhh.temp.hdfs


import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FSDataOutputStream, FileSystem,  Path}
import org.junit.Test

/**
  * Created by huanghh on 2017/6/28.
  */
class S06WriteDataToHDFS {
  /** 不知道怎么写，就去看管demo，看IO方面的 */
  @Test def testHDFSFs(): Unit = {
    val conf = new Configuration();

    val fs = FileSystem.get(conf);
    println("123")

  }
  @Test def testWriteDataToHDFS() {
    //    System.out.println("com.hhh.temp.HelloScalaJunit")
    val conf = new Configuration(); //此处的配置是因为你的resources有core-site.xml，hdfs-site.xml进行配置，不然默认是读取local


    val fSys = FileSystem.get(conf);
    val path = new Path("/etldata/datacenter.db/temp/hot_product/data")
    val b = "helloWorld".getBytes("utf-8");
//    FileSystemTestHelper.writeFile(fs,path,b)
    val out: FSDataOutputStream = fSys.create(path)
    out.write(b)
    out.close()
  }
}
