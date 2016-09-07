package models

case class ChannelModel(id: Option[Long], title: String, actorClass: String) extends BaseModel[ChannelModel] {
  override def copy(id: Option[Long]) = ChannelModel(id, title, actorClass)
}
