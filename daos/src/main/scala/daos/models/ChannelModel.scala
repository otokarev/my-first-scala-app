package daos.models
import java.util.UUID

case class ChannelModel(id: Option[UUID], title: String, actorClass: String) extends BaseModel[ChannelModel] {
  override def copy(id: Option[UUID]) = ChannelModel(id, title, actorClass)
}
