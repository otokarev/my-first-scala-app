package actors.workers

import akka.actor.{Actor, ActorContext, Props}

object Utils {
  def props(className: String)(implicit context: ActorContext) = {
    Props.apply(Class.forName(className).asInstanceOf[Class[Actor]], context.self)
  }

}
