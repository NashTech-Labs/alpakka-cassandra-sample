package com.sample.model
import java.lang.Long

case class Student(id: Long, name: String) {

  def toBindSeq: Seq[AnyRef] = {
    Seq(id, name)
  }

}

