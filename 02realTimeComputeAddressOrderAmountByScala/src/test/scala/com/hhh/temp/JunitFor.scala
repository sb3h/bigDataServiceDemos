package com.hhh.temp

import org.junit.Test

/**
  * Created by huanghh on 2017/5/16.
  */
class JunitFor {


  @Test def testScala() {
    System.out.println("com.hhh.temp.HelloScalaJunit")
  }

  @Test def testScalaFor01To() {
    //    System.out.println("com.hhh.temp.HelloScalaJunit")
    for (a <- 1 to 10) {
      println("Value of a: " + a);
    }
  }

  @Test def testScalaFor02Until() {
    var a = 0;
    // for loop execution with a range
    for (a <- 1 until 10) {
      println("Value of a: " + a);
    }
  }

  @Test def testScalaFor03Double() {
    var a = 0;
    var b = 0;
    // for loop execution with a range
    //先走完a(b+1)的遍，再走(a+1)(b+1),相当于java双重for
    for (a <- 1 to 3; b <- 1 to 3) {
      println("Value of a: " + a);
      println("Value of b: " + b);
    }
  }

  @Test def testScalaFor04EachCollection() {
    var a = 0;
    val numList = List(1, 2, 3, 4, 5, 6);

    // for loop execution with a collection
    for (a <- numList) {
      println("Value of a: " + a);
    }
  }
}
