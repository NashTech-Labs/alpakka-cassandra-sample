package com.common.cassandra


import com.datastax.driver.core._

trait CassandraSessionProvider {

  private val cassandraHost = "127.0.0.1"

  implicit val cassandraSession: Session = Cluster.builder
    .addContactPoint(cassandraHost)
    .withPort(9042)
    .build
    .connect()

}
