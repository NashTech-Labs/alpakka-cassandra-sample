package com.common.date

import java.sql.Timestamp

import org.scalatest.FunSuite


class DateUtilTest extends FunSuite {

  object DateUtilTestObj extends DateUtil

  test("get current timestamp") {
    val timestamp = new Timestamp(System.currentTimeMillis)
    assert(timestamp.compareTo(DateUtilTestObj.currentTimestamp) <= 0)
  }

}
