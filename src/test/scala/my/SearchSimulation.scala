package my

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class SearchSimulation extends Simulation {

	val rampUpTimeSecs = 20
	val testTimeSecs   = 60
	val noOfUsers      = 10000
	val minWaitMs      = 10 milliseconds
	val maxWaitMs      = 1000 milliseconds
  
	val httpConf = http
		.baseURL("http://localhost:8080/cr/1")
    .doNotTrackHeader("1")
		.disableFollowRedirect

	val headers_1 = Map(
		"Keep-Alive" -> "115")

	val scn = scenario("Query")
		.during(testTimeSecs) {
			exec(
				http("(akka) Search without query")
					.get("/search")
					.headers(headers_1)
					.check(status.is(200)))
			.pause(minWaitMs, maxWaitMs)
		}

	setUp(scn.inject(
      rampUsers(noOfUsers) over (rampUpTimeSecs seconds)
    ).protocols(httpConf))
}
