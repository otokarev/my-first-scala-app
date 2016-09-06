package actors

import akka.actor.{Actor, ActorLogging, ActorRef}
import models.EventModel

case class PrevalidationActorConfig(in: ActorRef, out: ActorRef)

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
