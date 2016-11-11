package cracker.actors.workers.source

import akka.actor.{Actor, ActorLogging}

trait WorkerActor extends Actor with ActorLogging {

  val config: String

  def init(cfg: String)

  init(config)
  
  override def receive = {

    case _ => log.error("Wrong message received")
  }

}
