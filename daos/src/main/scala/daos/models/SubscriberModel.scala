package daos.models
import java.util.UUID

case class SubscriberModel(id: Option[UUID], title: String) extends BaseModel[SubscriberModel] {
  override def copy(id: Option[UUID]) = SubscriberModel(id, title)
}


