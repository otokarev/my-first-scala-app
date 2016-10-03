package cracker.actors.workers

import akka.actor.ActorRef
import cracker.models.EventModel

class DummyActor(parent: ActorRef) extends WorkerActor {
  override def processEvent(ev: EventModel) = {
    log.info(s"Event processed: uuid: ${ev.uuid}; ev: ${ev.event}")
  }
}
