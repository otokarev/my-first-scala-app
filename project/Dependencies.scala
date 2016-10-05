import sbt._

object Dependencies {

  object Version {
    val akka = "2.4.8"
    val play = "2.0.2"
  }

  lazy val frontend = common ++ play ++ db ++ webjars ++ tests
  lazy val cracker = common ++ akka ++ tests

  val common = Seq()

  val db = Seq(
    "mysql" % "mysql-connector-java" % "5.1.39",
    "com.h2database" % "h2" % "1.4.192"
  )
  val play = Seq(
    "com.typesafe.play" %% "play-slick" % Version.play,
    "com.typesafe.play" %% "play-slick-evolutions" % Version.play,
    "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test exclude("org.scalatest", "scalatest")
  )

  val akka = Seq(
    "com.typesafe.akka" %% "akka-actor" % Version.akka,
    "com.typesafe.akka" %% "akka-testkit" % Version.akka % Test
  )

  val webjars = Seq(
    "org.webjars" % "jquery" % "3.1.0",
    "org.webjars" % "requirejs" % "2.3.1",
    "org.webjars" % "d3js" % "4.2.1",
    "org.webjars" % "underscorejs" % "1.8.3",
    "org.webjars" % "bootstrap" % "3.3.7-1" exclude ("org.webjars", "jquery"),
    "org.webjars.npm" % "angular-ui-bootstrap" % "2.1.3" exclude ("org.webjars", "jquery"),
    "org.webjars" % "angularjs" % "1.5.8" exclude ("org.webjars", "jquery"),
    "org.webjars" % "bootswatch-yeti" % "3.3.5+4"  exclude ("org.webjars", "jquery"),
    "org.webjars.bower" % "ng-table" % "1.0.0"
  )

  val tests = Seq(
    "org.scalatest" %% "scalatest" % "2.2.6" % Test
  )

}