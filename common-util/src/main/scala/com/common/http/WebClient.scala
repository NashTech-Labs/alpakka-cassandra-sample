package com.common.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpHeader.ParsingResult
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import com.common.json.JsonHelper
import com.common.logger.Logging

import scala.concurrent.{ExecutionContextExecutor, Future}

trait WebClient extends Logging with JsonHelper {

  implicit val system: ActorSystem
  implicit lazy val materializer = ActorMaterializer()

  def getRequest(url: String, header: Map[String, String] = Map.empty[String, String])
                (implicit system: ActorSystem, materializer: ActorMaterializer, ec: ExecutionContextExecutor)
  : Future[HttpResponse] = {
    info(s"GET request................. $url")
    val httpRequest = HttpRequest(GET, uri = url, headers = getHeaderData(header))
    Http().singleRequest(httpRequest)
  }

  def postRequest(url: String, body: String, header: Map[String, String] = Map.empty[String, String])
                 (implicit system: ActorSystem, materializer: ActorMaterializer, ec: ExecutionContextExecutor)
  : Future[HttpResponse] = {
    val httpRequest = HttpRequest(POST, uri = url, entity = HttpEntity(ContentTypes.`application/json`, body),
      headers = getHeaderData(header))
    info(s"POST request................. $url")
    Http().singleRequest(httpRequest)
  }

  private def getHeaderData(header: Map[String, String]) = {
    (header flatMap {
      case (k, v) => HttpHeader.parse(k, v) match {
        case ParsingResult.Ok(h, errors) => Some(h)
        case _ => None
      }
    }).toList
  }

}
