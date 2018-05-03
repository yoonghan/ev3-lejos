name := "roller"

organization := "com.walcron.teletran"

version := "1.0-SNAPSHOT"

description := "Remote Controlled Roller Robot via WebSockets"

scalaVersion := "2.11.1"

publishTo := Some(Resolver.file("file", new File(Path.userHome.absolutePath+"/.m2/repository")))

libraryDependencies ++= Seq(
  "com.github.bdeneuter" % "lejos-ev3-api" % "0.9.1-beta",
  "com.typesafe.akka" %% "akka-actor" % "2.3.8"
)

scalacOptions += "-feature"
