package cracker

import akka.actor._
import com.typesafe.config.ConfigFactory
import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {

  val config = ConfigFactory.load()
  val system = ActorSystem("Cracker", config.getConfig("cracker").withFallback(ConfigFactory.load()))

  Await.result(system.whenTerminated, Duration.Inf)
}