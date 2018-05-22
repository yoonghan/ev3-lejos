organization in ThisBuild := "com.walcron.lego"
version in ThisBuild := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.1"

publishTo := Some(Resolver.file("file", new File(Path.userHome.absolutePath+"/.m2/repository")))

lazy val `roller-lejos` = (project in file(".")).aggregate(`roller-lego`, `roller-builder`)

lazy val `roller-lego` = (project in file("roller-lego"))
  .settings(
    libraryDependencies ++= Seq(
      "com.github.bdeneuter" % "lejos-ev3-api" % "0.9.1-beta",
      "com.typesafe.akka" %% "akka-actor" % "2.3.8",
      "com.google.code.gson" % "gson" % "1.7.1"
    )
  )

lazy val `roller-builder` = (project in file("roller-builder"))
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.3.8",
      "org.glassfish.grizzly" % "grizzly-framework" % "2.4.3" % "test",
      "org.glassfish.grizzly" % "grizzly-websockets-server" % "2.4.3" % "test",
      "com.google.code.gson" % "gson" % "1.7.1",
      "org.scalactic" %% "scalactic" % "3.0.5",
      "org.scalatest" %% "scalatest" % "3.0.5" % "test",
      "org.scalacheck" %% "scalacheck" % "1.14.0" % "test"
     )
   )
  .dependsOn(`roller-lego`)
