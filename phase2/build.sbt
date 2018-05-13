organization in ThisBuild := "com.walcron.lego"
version in ThisBuild := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.12.4"


lagomKafkaEnabled in ThisBuild := false
lagomKafkaAddress in ThisBuild := "localhost:9092"

lagomCassandraEnabled in ThisBuild := false
lagomUnmanagedServices in ThisBuild := Map("cas_native" -> "http://localhost:9042")

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test
val kamonCore = "io.kamon" %% "kamon-core" % "1.1.0"
val kamonLogback = "io.kamon" %% "kamon-logback" % "1.0.0"
val kamonPrometheus = "io.kamon" %% "kamon-prometheus" % "1.0.0"
val kamonZipkin = "io.kamon" %% "kamon-zipkin" % "1.0.0"

lazy val `roller-lagom` = (project in file(".")).aggregate(`roller-impl`, `roller-api`)

lazy val `roller-api` = (project in file("roller-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `roller-impl` = (project in file("roller-impl"))
  .enablePlugins(LagomScala)
  .settings(
     libraryDependencies ++= Seq(
       lagomScaladslPersistenceCassandra,
       lagomScaladslPubSub,
       lagomScaladslKafkaBroker,
       lagomScaladslTestKit,
       macwire,
       kamonCore,
       kamonLogback,
       kamonPrometheus,
       kamonZipkin,
       scalaTest
     )
   )
  .dependsOn(`roller-api`)
