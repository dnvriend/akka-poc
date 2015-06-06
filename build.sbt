name := "akka-poc"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= {
  val akkaVersion       = "2.3.11"
  val akkaStreamVersion = "1.0-RC3"
  val scalaTestVersion  = "2.2.4"
  val sprayVersion = "1.3.3"
  val sprayJsonVersion = "1.3.2"
  Seq(
    "com.typesafe.akka"    %% "akka-actor"                           % akkaVersion,
    "com.typesafe.akka"    %% "akka-stream-experimental"             % akkaStreamVersion,
    "com.typesafe.akka"    %% "akka-http-core-experimental"          % akkaStreamVersion,
    "com.typesafe.akka"    %% "akka-http-experimental"               % akkaStreamVersion,
    "com.typesafe.akka"    %% "akka-http-spray-json-experimental"    % akkaStreamVersion,
    "com.typesafe.akka"    %% "akka-http-xml-experimental"           % akkaStreamVersion,
    "com.typesafe.akka"    %% "akka-http-testkit-experimental"       % akkaStreamVersion,
    "io.spray"             %% "spray-http"                           % sprayVersion,
    "io.spray"             %% "spray-httpx"                          % sprayVersion,
    "io.spray"             %% "spray-routing-shapeless2"             % sprayVersion,
    "io.spray"             %% "spray-util"                           % sprayVersion,
    "io.spray"             %% "spray-io"                             % sprayVersion,
    "io.spray"             %% "spray-can"                            % sprayVersion,
    "io.spray"             %% "spray-json"                           % sprayJsonVersion,
    "org.springframework.boot" % "spring-boot-starter-web" % "1.2.4.RELEASE",
    "org.springframework.boot" % "spring-boot-starter-actuator" % "1.2.4.RELEASE",
    "org.scalatest"        %% "scalatest"                            % scalaTestVersion    % Test,
    "io.gatling.highcharts" % "gatling-charts-highcharts"            % "2.1.6"             % Test,
    "io.gatling"            % "gatling-test-framework"               % "2.1.6"             % Test
  )
}

import spray.revolver.RevolverPlugin.Revolver

Revolver.settings

Revolver.enableDebugging(port = 5050, suspend = false)

mainClass in Revolver.reStart := Some("org.example.SearchService")

enablePlugins(io.gatling.sbt.GatlingPlugin)