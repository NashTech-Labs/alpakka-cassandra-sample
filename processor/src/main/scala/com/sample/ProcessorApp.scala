package com.sample

import java.util.concurrent.TimeUnit
import akka.Done
import akka.actor.ActorSystem
import akka.routing.{Pool, RoundRobinPool}
import akka.stream.ActorMaterializer
import akka.stream.alpakka.cassandra.scaladsl.CassandraSource
import akka.stream.scaladsl.Sink
import akka.util.Timeout
import com.common.cassandra.CassandraSessionProvider
import com.common.logger.Logging
import com.datastax.driver.core.{Row, Session}
import com.sample.model.Student
import com.sample.repositories.StudentRepo

import scala.concurrent.ExecutionContext.Implicits.global

object ProcessorApp extends App with StudentRepo with CassandraSessionProvider with Logging {

  implicit val actorSystem: ActorSystem = ActorSystem()
  implicit lazy val materializer = ActorMaterializer()
  override implicit val session: Session= cassandraSession
  implicit val timeout: Timeout = Timeout(50, TimeUnit.MILLISECONDS)

  // A pool of actors to process the messages
  val actorRouter = actorSystem.actorOf(
    RoundRobinPool(10, supervisorStrategy = Pool.defaultSupervisorStrategy)
      .props(ProcessorActor.props(actorSystem))
  )

  val result = CassandraSource(prepareStatementSelect)
    .map (transform)
    .ask[Done](10)(actorRouter)
    .runWith(Sink.ignore)

  result.recover {
    case ex: Exception =>
      error(s"Error found while fetching data from cassandra table: [$ex]")
      throw ex
  }

  private def transform(row: Row): Student = {
    info(s"Got a new row from cassandra table : [$row]")
    Student(
      row.getLong("id"),
      row.getString("name")
    )
  }

}
