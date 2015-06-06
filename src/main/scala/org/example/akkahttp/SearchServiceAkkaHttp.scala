package org.example.akkahttp

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.FlowMaterializer
import akka.stream.scaladsl._
import org.example.{CoreServices, Person}
import spray.json.DefaultJsonProtocol

import scala.concurrent.ExecutionContextExecutor

trait AkkaHttpMarshallers extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val personJsonFormat = jsonFormat2(Person)
}

trait Service extends AkkaHttpMarshallers {
  implicit def system: ActorSystem
  implicit def ec: ExecutionContextExecutor
  implicit def materializer: FlowMaterializer
  implicit def log: LoggingAdapter

  def routes: Flow[HttpRequest, HttpResponse, Unit] =
    path("search") {
      complete {
        Person("John Doe", 50)
      }
    }
}

object SearchServiceAkkaHttp extends App with Service with CoreServices {
  Http().bindAndHandle(routes, "0.0.0.0", 8080)
}