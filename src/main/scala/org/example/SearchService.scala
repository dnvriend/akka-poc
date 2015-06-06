package org.example

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorFlowMaterializer, FlowMaterializer}
import akka.stream.scaladsl._
import spray.json.DefaultJsonProtocol

import scala.concurrent.ExecutionContextExecutor

case class Person(name: String, age: Int)

trait CoreServices {
  implicit val system: ActorSystem = ActorSystem()
  implicit val log: LoggingAdapter = Logging(system, this.getClass)
  implicit val materializer: FlowMaterializer = ActorFlowMaterializer()
  implicit val ec: ExecutionContextExecutor = system.dispatcher
}

trait Marshallers extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val personJsonFormat = jsonFormat2(Person)
}

trait Service extends Marshallers {
  implicit def system: ActorSystem
  implicit def ec: ExecutionContextExecutor
  implicit def materializer: FlowMaterializer
  implicit def log: LoggingAdapter

  def routes: Flow[HttpRequest, HttpResponse, Unit] =
    pathPrefix("cr" / "1" / "search") {
      complete {
        Person("John Doe", 50)
      }
    }
}

object SearchService extends App with Service with CoreServices {
  Http().bindAndHandle(routes, "0.0.0.0", 8080)
}