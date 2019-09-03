package com.common.date

import java.sql.Timestamp

trait DateUtil {

  def currentTimestamp: Timestamp = new Timestamp(currentTime)

  def currentTime: Long = System.currentTimeMillis

}

trait UUIDHelper {

  def getRandomUUID: String = java.util.UUID.randomUUID().toString

}

object DateUtil extends DateUtil

object UUIDHelper extends UUIDHelper
