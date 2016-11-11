package cracker.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

class SourceManagerActor(parent: ActorRef) extends Actor with ActorLogging {

  self ! "init"

  def startChildren: Unit = {}

  override def receive = {
    case "init" => startChildren
    case _ => log.error("Wrong message received")
  }
}

object SourceManagerActor {
  def props(parent: ActorRef) = Props(new SourceManagerActor(parent))
}

