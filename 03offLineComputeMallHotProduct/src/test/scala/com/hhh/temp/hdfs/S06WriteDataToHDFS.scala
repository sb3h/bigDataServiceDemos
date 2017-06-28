package com.hhh.temp.hdfs


import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FSDataOutputStream, FileSystem, FileSystemTestHelper, Path}
import org.junit.Test

/**
  * Created by huanghh on 2017/6/28.
  */
class S06WriteDataToHDFS {
  /** 不知道怎么写，就去看管demo，看IO方面的 */
  @Test def testScala() {
    //    System.out.println("com.hhh.temp.HelloScalaJunit")
    val conf = new Configuration(); //此处的配置是因为你的resources有core-site.xml，hdfs-site.xml进行配置，不然默认是读取local
    conf.set("fs.defaultFS","hdfs://ns")
    conf.set("dfs.nameservices","ns")

    val fSys = FileSystem.get(conf);
    val path = new Path("/etldata/datacenter.db/temp/hot_product/data")
    val b = "helloWorld".getBytes("utf-8");
//    FileSystemTestHelper.writeFile(fs,path,)
    val out: FSDataOutputStream = fSys.create(path)
    out.write(b)
    out.close()
  }
}
