package daos.models

case class SubscriberModel(id: Option[Long], title: String) extends BaseModel[SubscriberModel] {
  override def copy(id: Option[Long]) = SubscriberModel(id, title)
}


