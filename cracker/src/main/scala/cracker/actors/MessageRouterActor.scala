package cracker.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import cracker.models.EventModel

class MessageRouterActor(parent: ActorRef) extends Actor with ActorLogging {

  def resolveSymbol(channelId: Long) = "processor-for-channel-" + channelId

  def resolveClass(channelId: Long) = {
    /**
      * TODO: retrieve this string from channel model, or wherever else
      */
    "cracker.actors.workers.DummyActor"
  }

  override def receive = {

    case ev @ EventModel(uuid, event, channelId, subscriberId, payload) =>
      log.debug(s"Message received: uuid: $uuid")

      import cracker.actors.workers.Utils._

      val symbol = resolveSymbol(channelId)

      context.child(symbol) getOrElse {
        val a = context.actorOf(props(resolveClass(channelId)), name = symbol)
        log.info(s"Worker registered: symbol: $symbol")
        a
      } forward ev

      log.debug(s"Message sent to worker: msg uuid: $uuid; worker: `$symbol`")

    case _ => log.error("Wrong message received")
  }
}

object MessageRouterActor {
  def props(parent: ActorRef) = Props(new MessageRouterActor(parent))
}
