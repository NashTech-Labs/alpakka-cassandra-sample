package com.sample

import java.time.Instant

import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.alpakka.cassandra.scaladsl.CassandraSink
import akka.stream.scaladsl.Source
import com.common.cassandra.CassandraSessionProvider
import com.common.logger.Logging
import com.datastax.driver.core.Session
import com.sample.model.Student
import com.sample.repositories.StudentRepo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object GeneratorApp extends App with CassandraSessionProvider with Logging with StudentRepo {

  implicit val actorSystem: ActorSystem = ActorSystem()
  implicit lazy val materializer: ActorMaterializer = ActorMaterializer()
  implicit val session: Session= cassandraSession

  val currentMillies = Instant.now().toEpochMilli
  val students = (10 to 2000).map (elem => Student(currentMillies + elem, "name_" + (currentMillies + elem))).toList
  val sink = CassandraSink[Student](parallelism = 20, preparedStatementInsert, statementBinder)

  val result: Future[Done] = Source(students)
    .map { student =>
      info(s"Student found : [$student]")
      student
    }
    .runWith(sink)

  result.map { result =>
    info(s"Response found: [$result]")
  }.recover {
    case ex: Exception =>
      error(s"Error found while ingesting student into cassandra data: [$ex]")
      throw ex
  }

}
