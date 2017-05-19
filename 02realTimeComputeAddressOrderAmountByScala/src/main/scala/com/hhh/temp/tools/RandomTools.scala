package com.hhh.temp.tools

import java.util.Random

/**
  * Created by huanghh on 2017/5/16.
  */
object RandomTools {
  def getRandomI(maxI: Int): Int = {
    val randomI: Int = getRandomI(0, maxI)
    randomI
  }

  def getRandomI(minI: Int, maxI: Int): Int = {
    var _minI: Int = minI
    var _maxI: Int = maxI
    if (_minI > _maxI) {
      _maxI = minI
      _minI = 0
    }
    val random: Random = new Random
    var randomI: Int = random.nextInt(_maxI)
    if (randomI < _minI) randomI = randomI + _minI
    randomI
  }
}
