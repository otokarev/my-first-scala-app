package controllers

import javax.inject._

import models.{BaseDao, ChannelSubscriberModel}
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import tables.Tables.ChannelSubscriberTable

import scala.concurrent.ExecutionContext

@Singleton
class ChannelSubscriberController @Inject() (s: BaseDao[ChannelSubscriberTable, ChannelSubscriberModel])(implicit exec: ExecutionContext)  extends AbstractController(s)(exec) {

  implicit val itemFormat: Format[ChannelSubscriberModel] = (
    (__ \ "id").formatNullable[Long] and
      (__ \ "title").format[String](minLength[String](1)) and
      (__ \ "subscriberId").format[Long] and
      (__ \ "channelId").format[Long] and
      (__ \ "cfg").format[String]
    )(ChannelSubscriberModel.apply, unlift(ChannelSubscriberModel.unapply))
}
