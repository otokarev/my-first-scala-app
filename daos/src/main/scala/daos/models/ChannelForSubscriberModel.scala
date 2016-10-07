package daos.models

case class ChannelForSubscriberModel(id: Option[Long], title: String, subscriber: SubscriberModel, channel: ChannelModel, cfg: String)

