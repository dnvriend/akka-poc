package org.example.springboot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

object SearchServiceSpringBoot extends App {
  SpringApplication.run(Array(classOf[SearchServiceSpringBoot].asInstanceOf[AnyRef]), args)
}

@SpringBootApplication
class SearchServiceSpringBoot {

}
