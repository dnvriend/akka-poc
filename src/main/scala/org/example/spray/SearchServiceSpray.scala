package org.example.spray

import org.example.{CoreServices, Person}
import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import spray.routing.SimpleRoutingApp

trait SprayMarshallers extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val personJsonFormat = jsonFormat2(Person)
}

object SearchServiceSpray extends App with SprayMarshallers with SimpleRoutingApp with CoreServices {
  startServer("0.0.0.0", 8082) {
    path("search") {
      complete {
        Person("John Doe", 50)
      }
    }
  }
}