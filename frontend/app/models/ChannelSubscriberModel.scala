package models

case class ChannelSubscriberModel(id: Option[Long], title: String, subscriberId: Long, channelId: Long, cfg: String) extends BaseModel[ChannelSubscriberModel] {
  override def copy(id: Option[Long]) = ChannelSubscriberModel(id, title, subscriberId, channelId, cfg)
}


