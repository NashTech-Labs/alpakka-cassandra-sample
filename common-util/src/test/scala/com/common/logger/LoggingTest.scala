package com.common.logger

import org.scalatest.FunSuite


class LoggingTest extends FunSuite with Logging {

  test("Ping logger generates debug log") {
    assert(debug("Some debug message...").isInstanceOf[Unit])
  }

  test("Ping logger generates debug log with some exception") {
    assert(debug("Some debug message...", new Exception("some exception")).isInstanceOf[Unit])
  }

  test("Ping logger generates info log") {
    assert(info("Some info message...").isInstanceOf[Unit])
  }

  test("Ping logger generates warn log") {
    assert(warn("Some warn message...").isInstanceOf[Unit])
  }

  test("Ping logger generates warn log with exception") {
    assert(warn("Some warn message...", new Exception("some exception")).isInstanceOf[Unit])
  }

  test("Ping logger generates error log") {
    assert(error("Some warn message...").isInstanceOf[Unit])
  }

  test("Ping logger generates error log with exception") {
    assert(error("Some warn message...", new Exception("some exception")).isInstanceOf[Unit])
  }

}
