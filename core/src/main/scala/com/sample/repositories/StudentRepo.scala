package com.sample.repositories

import com.datastax.driver.core.{PreparedStatement, Session, SimpleStatement, Statement}
import com.sample.model.Student


trait KeySpace {

  val keyspace: String = "alpakka_sample"

}


trait StudentRepo extends KeySpace {

  implicit val session: Session
  val fetchSize = 20

  lazy val preparedStatementInsert: PreparedStatement = session.prepare(s"INSERT INTO $keyspace.student(id, name) VALUES (?, ?)")
  lazy val prepareStatementSelect: Statement = new SimpleStatement(s"SELECT * FROM $keyspace.student").setFetchSize(fetchSize)

  lazy val statementBinder = (student: Student, statement: PreparedStatement) => statement.bind(student.toBindSeq: _*)

}
