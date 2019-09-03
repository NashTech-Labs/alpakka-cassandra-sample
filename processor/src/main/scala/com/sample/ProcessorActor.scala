package com.sample

import akka.Done
import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.RouterConfig
import com.common.logger.Logging
import com.sample.model.Student

class ProcessorActor extends Actor with Logging {

  override def receive: Receive = {

    case student: Student =>
      info(s"Got new student to persist: [$student]")
      sender() ! Done

    case msg =>
      warn(s"Unknown messages found : [$msg]")

  }

}

object ProcessorActor {

  def props(system: ActorSystem) = Props(classOf[ProcessorActor])

}
