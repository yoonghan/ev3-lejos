organization in ThisBuild := "com.walcron.lego"
version in ThisBuild := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.12.4"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

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
       macwire
     )
   )
  .dependsOn(`roller-api`)
