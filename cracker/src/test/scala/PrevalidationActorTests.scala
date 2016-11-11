import TestUtils.FakeSource
import akka.actor.{ActorSystem, Props}
import akka.testkit.{EventFilter, ImplicitSender, TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, WordSpecLike}

import scala.concurrent.duration._
import cracker.actors.PrevalidationActor

class PrevalidationActorTests(_system: ActorSystem) extends TestKit(_system: ActorSystem)
  with ImplicitSender
  with WordSpecLike
  with BeforeAndAfterAll
  with BeforeAndAfterEach
{
  def this() = this(
    ActorSystem(
      "PrevalidationActorSpec",
      ConfigFactory.parseString("""
  akka.loggers = ["akka.testkit.TestEventListener"]
""")))

  override def afterAll() = TestKit.shutdownActorSystem(system)

  "Prevalidation actor" must {
    "accept events with correct channel and subscriber ids" in {
      val parentProbe = TestProbe()
      val channelMsgRouterProbe = TestProbe()
      val sourceRef = system.actorOf(PrevalidationActor.props(parent=parentProbe.ref, out=channelMsgRouterProbe.ref))

      val sentMsg = FakeSource.get()
      sourceRef ! sentMsg
      channelMsgRouterProbe.expectMsg(500 millis, sentMsg)
    }
    "send log error on malformed message" in {

      val parentProbe = TestProbe()
      val channelMsgRouterProbe = TestProbe()
      val sourceRef = system.actorOf(Props(new PrevalidationActor(parent=parentProbe.ref, out=channelMsgRouterProbe.ref)))
      EventFilter.error(message = "Wrong message received", occurrences = 1) intercept {
        sourceRef ! "BAD MESSAGE"
      }
    }

  }

}


