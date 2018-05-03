name := "roller-builder"

organization in ThisBuild := "com.walcron.teletran"

version in ThisBuild := "1.0-SNAPSHOT"

description := "Remote Controlled Roller Robot via WebSockets"

scalaVersion := "2.11.1"

resolvers += "Local Maven Repository" at Path.userHome.asFile.toURI.toURL + ".m2/repository"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.8",
  "org.glassfish.grizzly" % "grizzly-framework" % "2.4.3" % "test",
  "org.glassfish.grizzly" % "grizzly-websockets-server" % "2.4.3" % "test",
  "org.scalactic" %% "scalactic" % "3.0.5",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.scalacheck" %% "scalacheck" % "1.14.0" % "test",
  "com.walcron.teletran" %% "roller" % "1.0-SNAPSHOT"
)
