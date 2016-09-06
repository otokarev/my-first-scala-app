import java.util.UUID

import actors.PrevalidationActor
import akka.actor.{ActorSystem, Props}
import akka.testkit.{EventFilter, ImplicitSender, TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import models._
import org.scalatest._

import scala.concurrent.duration._
import scala.util.Random

/**
 * SupervisorActor:
 * - start FakeInputActor
 * - start PrevalidationActor (validate subscriberId, channelId, channel.cfg)
 * - start ChannelMessageRouterActor
 *
 * Check FakeInputActor:
 * - emit test messages
 *
 * ChannelMessageRouterActor
 * - launch ChannelProcessActors
 * - collect references to ChannelProcessActors pointed to by corresponding channelId,
 * - routes messages to actor instantiated for certain channelId)
 *
 * <name>ChannelProcessActor:
 * - read messages for given channel and process them.
 *
 * LoggerActor:
 * - collects errors from other actors
 */

class PrevalidationActorSpec(_system: ActorSystem) extends TestKit(_system: ActorSystem)
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
      val sourceRef = system.actorOf(Props(new PrevalidationActor(parent=parentProbe.ref, out=channelMsgRouterProbe.ref)))

      val sentMsg = FakeSource.get()
      sourceRef ! sentMsg
      channelMsgRouterProbe.expectMsg(500 millis, sentMsg)
    }
    "rise RuntimeException on wrong message" in {

      val parentProbe = TestProbe()
      val channelMsgRouterProbe = TestProbe()
      val sourceRef = system.actorOf(Props(new PrevalidationActor(parent=parentProbe.ref, out=channelMsgRouterProbe.ref)))
      EventFilter.error(message = "Wrong message received", occurrences = 1) intercept {
        sourceRef ! "BAD MESSAGE"
      }
    }
    "filter out events with bad channelId" in {

    }
    "filter out events with bad subscriberId" in {

    }

  }

}

object Utils {
  val subscriberList = List(
    SubscriberModel(Option(1), "Subscriber #1"),
    SubscriberModel(Option(2), "Subscriber #2"),
    SubscriberModel(Option(3), "Subscriber #3")
  )
  val channelList = List(
    ChannelModel(Option(1), "Channel #1"),
    ChannelModel(Option(2), "Channel #2"),
    ChannelModel(Option(3), "Channel #3")
  )
  val channelSubscriberList = List(
    ChannelSubscriberModel(id = Option(1),  title = "Channel for subscriber", subscriberId = 1, channelId = 3, cfg = ""),
    ChannelSubscriberModel(id = Option(2),  title = "Channel for subscriber", subscriberId = 2, channelId = 2, cfg = ""),
    ChannelSubscriberModel(id = Option(3),  title = "Channel for subscriber", subscriberId = 3, channelId = 1, cfg = ""),
    ChannelSubscriberModel(id = Option(4),  title = "Channel for subscriber", subscriberId = 1, channelId = 2, cfg = ""),
    ChannelSubscriberModel(id = Option(5),  title = "Channel for subscriber", subscriberId = 2, channelId = 1, cfg = ""),
    ChannelSubscriberModel(id = Option(6),  title = "Channel for subscriber", subscriberId = 3, channelId = 3, cfg = ""),
    ChannelSubscriberModel(id = Option(7),  title = "Channel for subscriber", subscriberId = 1, channelId = 3, cfg = ""),
    ChannelSubscriberModel(id = Option(8),  title = "Channel for subscriber", subscriberId = 2, channelId = 2, cfg = ""),
    ChannelSubscriberModel(id = Option(9),  title = "Channel for subscriber", subscriberId = 3, channelId = 1, cfg = ""),
    ChannelSubscriberModel(id = Option(10), title = "Channel for subscriber", subscriberId = 1, channelId = 2, cfg = ""),
    ChannelSubscriberModel(id = Option(11), title = "Channel for subscriber", subscriberId = 2, channelId = 3, cfg = ""),
    ChannelSubscriberModel(id = Option(12), title = "Channel for subscriber", subscriberId = 3, channelId = 1, cfg = "")
  )
  val channelForSubscriberModel = channelSubscriberList map {
    a: ChannelSubscriberModel => ChannelForSubscriberModel(
      id = a.id,
      title=a.title,
      subscriber = subscriberList(a.subscriberId.toInt - 1),
      channel = channelList(a.channelId.toInt - 1),
      cfg=s"Config for reseller ID#${a.subscriberId} and channel ID#${a.channelId}"
    )
  }

  def getSubscriber(id: Int) = subscriberList(id - 1)

  def getChannel(id: Int) = channelList(id - 1)

  def getChannelSubscriber(id: Int) = channelSubscriberList(id - 1)

  def getChannelForSubscriber(id: Int) = channelForSubscriberModel(id - 1)
}

object FakeSource {
  val adjs = List("autumn", "hidden", "bitter", "misty", "silent",
    "reckless", "daunting", "short", "rising", "strong", "timber", "tumbling",
    "silver", "dusty", "celestial", "cosmic", "crescent", "double", "far",
    "terrestrial", "huge", "deep", "epic", "titanic", "mighty", "powerful")

  val nouns = List("waterfall", "river", "breeze", "moon", "rain",
    "wind", "sea", "morning", "snow", "lake", "sunset", "pine", "shadow", "leaf",
    "sequoia", "cedar", "wrath", "blessing", "spirit", "nova", "storm", "burst",
    "giant", "elemental", "throne", "game", "weed", "stone", "apogee", "bang")

  def get(values: (String, Any)*) = {
    var uuid = UUID.randomUUID()
    var subscriberId: Long = Random.nextInt(3) + 1
    var channelId: Long = Random.nextInt(3) + 1
    var event = adjs(Random.nextInt(adjs.length)) + " " + nouns(Random.nextInt(nouns.length))
    var payload = "payload"

    values foreach {
      case ("uuid", v: UUID) => uuid = v
      case ("event", v: String) => event = v
      case ("subscriberId", v: Long) => subscriberId = v
      case ("event", v: Long) => channelId = v
      case ("payload", v: String) => payload = v
    }

    EventModel(uuid, event, subscriberId, channelId, payload)
  }
}


