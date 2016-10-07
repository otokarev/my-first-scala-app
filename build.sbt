lazy val root = (project in file("."))
  .settings(
    name := """event-cracker"""
  ).aggregate(
    frontend,
    cracker
  )

val commonSettings = Seq(
  organization := "otokarev@gmail.com",
  version := "0.1.0",
  scalaVersion := "2.11.8"
)

lazy val daos = (project in file("daos"))
  .settings(
    name := "event-cracker-daos",
    libraryDependencies ++= (Dependencies.daos),
    fork in run := true,
    commonSettings
  )

lazy val frontend = (project in file("frontend"))
  .enablePlugins(PlayScala)
  .settings(
    name := "event-cracker-frontend",
    libraryDependencies ++= (Dependencies.frontend  ++ Seq(cache, ws, evolutions)),
    fork in run := true,
    commonSettings
  ).dependsOn(daos)
  .aggregate(daos)

lazy val cracker = (project in file("cracker"))
  .settings(
    name := "event-cracker-cracker",
    libraryDependencies ++= (Dependencies.cracker),
    fork in run := true,
    commonSettings
  ).dependsOn(frontend % "test->compile")

fork in run := true