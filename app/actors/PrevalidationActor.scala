package actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import models.EventModel

class PrevalidationActor(parent: ActorRef, out: ActorRef) extends Actor with ActorLogging {
  override def receive = {
    case ev @ EventModel(uuid, event, channelId, subscriberId, payload) =>
      /**
        * TODO: insert here validation code
        */
      out ! ev
    case _ => log.error("Wrong message received")
  }
}

object PrevalidationActor {
  def props(parent: ActorRef, out: ActorRef) = Props(new PrevalidationActor(parent, out))
}
