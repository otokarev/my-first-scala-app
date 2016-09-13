name := """my-first-app"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  cache,
  ws,
  evolutions,
  "com.typesafe.akka" %% "akka-actor" % "2.4.8",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.8",
  "mysql" % "mysql-connector-java" % "5.1.39",
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "org.webjars" % "jquery" % "3.1.0",
  "org.webjars" % "requirejs" % "2.3.1",
  "org.webjars" % "d3js" % "4.2.1",
  "org.webjars" % "underscorejs" % "1.8.3",
  "org.webjars" % "bootstrap" % "3.3.7-1" exclude ("org.webjars", "jquery"),
  "org.webjars" % "angularjs" % "1.5.8" exclude ("org.webjars", "jquery"),
  "org.webjars" % "bootswatch-yeti" % "3.3.5+4"  exclude ("org.webjars", "jquery"),
  "org.webjars.bower" % "ng-table" % "1.0.0"
)



fork in run := true