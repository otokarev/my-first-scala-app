package models

case class ChannelSubscriberModel(id: Option[Long], title: String, subscriberId: Long, channelId: Long, cfg: String) extends BaseModel


