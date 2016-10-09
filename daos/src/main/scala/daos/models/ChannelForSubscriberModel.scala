package daos.models
import java.util.UUID

case class ChannelForSubscriberModel(id: Option[UUID], title: String, subscriber: SubscriberModel, channel: ChannelModel, cfg: String)

