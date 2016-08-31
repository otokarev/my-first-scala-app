package formats

import models.{ChannelModel, ChannelSubscriberModel, SubscriberModel}
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{Format, JsPath, _}



object Formats {
  def format4Channel: Format[ChannelModel] = (
    (JsPath \ "id").formatNullable[Long] and
      (JsPath \ "title").format[String](minLength[String](1))
    )(ChannelModel.apply, unlift(ChannelModel.unapply))

  def format4ChannelSubscriber: Format[ChannelSubscriberModel] = (
    (__ \ "id").formatNullable[Long] and
      (__ \ "title").format[String](minLength[String](1)) and
      (__ \ "subscriberId").format[Long] and
      (__ \ "channelId").format[Long] and
      (__ \ "cfg").format[String]
    )(ChannelSubscriberModel.apply, unlift(ChannelSubscriberModel.unapply))

  def format4Subscriber: Format[SubscriberModel] = (
    (JsPath \ "id").formatNullable[Long] and
      (JsPath \ "title").format[String](minLength[String](1))
    )(SubscriberModel.apply, unlift(SubscriberModel.unapply))
}
