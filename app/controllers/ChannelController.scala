package controllers

import javax.inject._

import models.{BaseDao, ChannelModel}
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import tables.Tables.ChannelTable

import scala.concurrent.ExecutionContext

@Singleton
class ChannelController @Inject() (s: BaseDao[ChannelTable, ChannelModel])(implicit exec: ExecutionContext)  extends AbstractController(s)(exec) {

  implicit val itemFormat: Format[ChannelModel] = (
    (JsPath \ "id").formatNullable[Long] and
      (JsPath \ "title").format[String](minLength[String](1))
    )(ChannelModel.apply, unlift(ChannelModel.unapply))
}
