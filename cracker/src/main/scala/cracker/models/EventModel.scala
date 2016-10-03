package cracker.models

import java.util.UUID

case class EventModel(uuid: UUID, event: String, subscriberId: Long, channelId: Long, payload: String)

