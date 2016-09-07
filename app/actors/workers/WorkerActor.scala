package actors.workers

import akka.actor.{Actor, ActorLogging}
import models.EventModel

trait WorkerActor extends Actor with ActorLogging {
  def processEvent(ev: EventModel)
  override def receive = {

    case ev @ EventModel(uuid, event, channelId, subscriberId, payload) =>
      log.debug(s"Message received: uuid: $uuid")

      processEvent(ev)

      log.debug(s"Message processed: msg uuid: $uuid;")

    case _ => log.error("Wrong message received")
  }

}
