package controllers

import javax.inject._

import models.{BaseDao, SubscriberModel}
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import tables.Tables.SubscriberTable

import scala.concurrent.ExecutionContext

@Singleton
class SubscriberController @Inject() (s: BaseDao[SubscriberTable, SubscriberModel])(implicit exec: ExecutionContext)  extends AbstractController(s)(exec) {

  implicit val itemFormat: Format[SubscriberModel] = (
    (JsPath \ "id").formatNullable[Long] and
      (JsPath \ "title").format[String](minLength[String](1))
    )(SubscriberModel.apply, unlift(SubscriberModel.unapply))
}
