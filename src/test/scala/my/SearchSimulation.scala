package my

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class SearchSimulation extends Simulation {

	val rampUpTimeSecs = 20
	val testTimeSecs   = 30
	val noOfUsers      = 2000
	val minWaitMs      = 10 milliseconds
	val maxWaitMs      = 1000 milliseconds

	val httpConfBoot = http
		.baseURL("http://localhost:8080")
		.doNotTrackHeader("1")
		.disableFollowRedirect

	val httpConfAkka = http
		.baseURL("http://localhost:8081")
    .doNotTrackHeader("1")
		.disableFollowRedirect

	val httpConfSpray = http
		.baseURL("http://localhost:8082")
		.doNotTrackHeader("1")
		.disableFollowRedirect

	val headers_1 = Map(
		"Keep-Alive" -> "115")


	val scnBoot = scenario("QuerySpringBoot")
		.during(testTimeSecs) {
		exec(
			http("(akka) Search without query")
				.get("/search")
				.headers(headers_1)
				.check(status.is(200)))
			.pause(minWaitMs, maxWaitMs)
	}

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
//		scnBoot.inject(rampUsers(noOfUsers) over (rampUpTimeSecs seconds)).protocols(httpConfBoot)
		// scnBoot.inject(rampUsers(noOfUsers) over (10 minutes)).protocols(httpConfBoot)
		// scnBoot.inject(rampUsersPerSec(1) to (noOfUsers) during(10 minutes)).protocols(httpConfBoot)
		//scnAkka.inject(rampUsers(noOfUsers) over (rampUpTimeSecs seconds)).protocols(httpConfAkka)
		// scnAkka.inject(rampUsers(2000) over (5 minutes)).protocols(httpConfAkka)
		// scnAkka.inject(rampUsersPerSec(1) to (10000) during (10 minutes)).protocols(httpConfAkka)
		scnAkka.inject(heavisideUsers(1000000) over(30 minutes)).protocols(httpConfAkka)
//		scnSpray.inject(rampUsers(noOfUsers) over (rampUpTimeSecs seconds)).protocols(httpConfSpray)
	)
}
