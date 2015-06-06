package org.example

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.stream.{ActorFlowMaterializer, FlowMaterializer}

import scala.concurrent.ExecutionContextExecutor

case class Person(name: String, age: Int)

trait CoreServices {
  implicit val system: ActorSystem = ActorSystem()
  implicit val log: LoggingAdapter = Logging(system, this.getClass)
  implicit val materializer: FlowMaterializer = ActorFlowMaterializer()
  implicit val ec: ExecutionContextExecutor = system.dispatcher
}