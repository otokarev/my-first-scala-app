package cracker.actors.workers.source

import akka.actor.ActorRef

class DummyActor(parent: ActorRef, cfg: String) extends WorkerActor {
  val config = cfg

  override def init(cfg: String) = {
    log.info(s"DummyActor initialized with cfg: '$cfg'")
  }
}
