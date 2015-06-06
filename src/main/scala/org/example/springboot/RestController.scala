package org.example.springboot

import org.example.Person
import org.example.spray.SprayMarshallers
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation._
import spray.json._

@Controller
@RequestMapping(value = Array("/search"))
class RestController extends SprayMarshallers {
  @RequestMapping(method = Array(RequestMethod.GET))
  @ResponseBody def person: String = Person("John Doe", 50).toJson.compactPrint
}
