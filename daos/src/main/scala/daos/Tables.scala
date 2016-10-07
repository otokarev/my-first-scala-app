package daos

import daos.models.{ChannelModel, ChannelSubscriberModel, SubscriberModel}

object Tables {
  lazy val dbConfig = Utils.getDatabaseConfig
  import dbConfig.driver.api._

  abstract class BaseTable[T](tag: Tag, name: String) extends Table[T](tag, name) {
    def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  }

  class SubscriberTable(tag: Tag) extends BaseTable[SubscriberModel](tag, "subscriber") {
    def title = column[String]("title")
    def * = (id.?, title) <> (SubscriberModel.tupled, SubscriberModel.unapply)
  }

  class ChannelTable(tag: Tag) extends BaseTable[ChannelModel](tag, "channel") {

    def title = column[String]("title")
    def actorClass = column[String]("actor_class")

    def * = (id.?, title, actorClass) <> (ChannelModel.tupled, ChannelModel.unapply)
  }

  class ChannelSubscriberTable(tag: Tag) extends BaseTable[ChannelSubscriberModel](tag, "channel_subscriber") {

    def title = column[String]("title")
    def channelId = column[Long]("channel_id")
    def subscriberId = column[Long]("subscriber_id")
    def cfg = column[String]("cfg")

    def * = (id.?, title, channelId, subscriberId, cfg) <> (ChannelSubscriberModel.tupled, ChannelSubscriberModel.unapply)
  }


  implicit val subscriberTableQuery : TableQuery[SubscriberTable] = TableQuery[SubscriberTable]
  implicit val channelTableQuery : TableQuery[ChannelTable] = TableQuery[ChannelTable]
  implicit val channelSubscriberTableQuery : TableQuery[ChannelSubscriberTable] = TableQuery[ChannelSubscriberTable]
}



