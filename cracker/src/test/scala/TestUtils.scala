import java.util.UUID

import cracker.models.EventModel

import scala.util.Random

object TestUtils {
//  val subscriberList = List(
//    SubscriberModel(Option(1), "Subscriber #1"),
//    SubscriberModel(Option(2), "Subscriber #2"),
//    SubscriberModel(Option(3), "Subscriber #3")
//  )
//  val channelList = List(
//    ChannelModel(Option(1), "Channel #1", "cracker.actors.workers.DummyActor"),
//    ChannelModel(Option(2), "Channel #2", "cracker.actors.workers.DummyActor"),
//    ChannelModel(Option(3), "Channel #3", "cracker.actors.workers.DummyActor")
//  )
//  val channelSubscriberList = List(
//    ChannelSubscriberModel(id = Option(1),  title = "Channel for subscriber", subscriberId = 1, channelId = 3, cfg = ""),
//    ChannelSubscriberModel(id = Option(2),  title = "Channel for subscriber", subscriberId = 2, channelId = 2, cfg = ""),
//    ChannelSubscriberModel(id = Option(3),  title = "Channel for subscriber", subscriberId = 3, channelId = 1, cfg = ""),
//    ChannelSubscriberModel(id = Option(4),  title = "Channel for subscriber", subscriberId = 1, channelId = 2, cfg = ""),
//    ChannelSubscriberModel(id = Option(5),  title = "Channel for subscriber", subscriberId = 2, channelId = 1, cfg = ""),
//    ChannelSubscriberModel(id = Option(6),  title = "Channel for subscriber", subscriberId = 3, channelId = 3, cfg = ""),
//    ChannelSubscriberModel(id = Option(7),  title = "Channel for subscriber", subscriberId = 1, channelId = 3, cfg = ""),
//    ChannelSubscriberModel(id = Option(8),  title = "Channel for subscriber", subscriberId = 2, channelId = 2, cfg = ""),
//    ChannelSubscriberModel(id = Option(9),  title = "Channel for subscriber", subscriberId = 3, channelId = 1, cfg = ""),
//    ChannelSubscriberModel(id = Option(10), title = "Channel for subscriber", subscriberId = 1, channelId = 2, cfg = ""),
//    ChannelSubscriberModel(id = Option(11), title = "Channel for subscriber", subscriberId = 2, channelId = 3, cfg = ""),
//    ChannelSubscriberModel(id = Option(12), title = "Channel for subscriber", subscriberId = 3, channelId = 1, cfg = "")
//  )
//  val channelForSubscriberModel = channelSubscriberList map {
//    a: ChannelSubscriberModel => ChannelForSubscriberModel(
//      id = a.id,
//      title=a.title,
//      subscriber = subscriberList(a.subscriberId.toInt - 1),
//      channel = channelList(a.channelId.toInt - 1),
//      cfg=s"Config for reseller ID#${a.subscriberId} and channel ID#${a.channelId}"
//    )
//  }

//  def getSubscriber(id: Int) = subscriberList(id - 1)
//
//  def getChannel(id: Int) = channelList(id - 1)
//
//  def getChannelSubscriber(id: Int) = channelSubscriberList(id - 1)
//
//  def getChannelForSubscriber(id: Int) = channelForSubscriberModel(id - 1)

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
}
