package my

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class SearchSimulation extends Simulation {

	val rampUpTimeSecs = 20
	val testTimeSecs   = 30
	val noOfUsers      = 1000
	val minWaitMs      = 10 milliseconds
	val maxWaitMs      = 1000 milliseconds
  
	val httpConfAkka = http
		.baseURL("http://localhost:8080")
    .doNotTrackHeader("1")
		.disableFollowRedirect

	val httpConfSpray = http
		.baseURL("http://localhost:8081")
		.doNotTrackHeader("1")
		.disableFollowRedirect

	val headers_1 = Map(
		"Keep-Alive" -> "115")

	val scnAkka = scenario("QueryAkka")
		.during(testTimeSecs) {
			exec(
				http("(akka) Search without query")
					.get("/search")
					.headers(headers_1)
					.check(status.is(200)))
			.pause(minWaitMs, maxWaitMs)
		}

	val scnSpray = scenario("QuerySpray")
		.during(testTimeSecs) {
		exec(
			http("(akka) Search without query")
				.get("/search")
				.headers(headers_1)
				.check(status.is(200)))
			.pause(minWaitMs, maxWaitMs)
	}

	setUp(
		scnAkka.inject(rampUsers(noOfUsers) over (rampUpTimeSecs seconds)).protocols(httpConfAkka),
		scnSpray.inject(rampUsers(noOfUsers) over (rampUpTimeSecs seconds)).protocols(httpConfSpray)
	)
}
