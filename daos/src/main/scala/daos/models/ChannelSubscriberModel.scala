package daos.models
import java.util.UUID

case class ChannelSubscriberModel(id: Option[UUID], title: String, subscriberId: UUID, channelId: UUID, cfg: String) extends BaseModel[ChannelSubscriberModel] {
  override def copy(id: Option[UUID]) = ChannelSubscriberModel(id, title, subscriberId, channelId, cfg)
}


