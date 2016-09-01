package formats

import models.{BaseModel, ChannelModel, ChannelSubscriberModel, SubscriberModel}
import play.api.data.validation.ValidationError
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{Format, JsPath, _}
import slick.lifted.TableQuery

import scala.concurrent.Await
import scala.concurrent.duration.Duration



object Formats {
  import daos._
  import tables.Tables._

  def format4Channel: Format[ChannelModel] = (
    (JsPath \ "id").formatNullable[Long] and
      (JsPath \ "title").format[String](minLength[String](1))
    )(ChannelModel.apply, unlift(ChannelModel.unapply))

  val write4ChannelSubscriber: Writes[ChannelSubscriberModel] = (
    (__ \ "id").writeNullable[Long] and
      (__ \ "title").write[String] and
      (__ \ "subscriberId").write[Long] and
      (__ \ "channelId").write[Long] and
      (__ \ "cfg").write[String]
    )(unlift(ChannelSubscriberModel.unapply))

  implicit val reads4ChannelSubscriber: Reads[ChannelSubscriberModel] = (
    (__ \ "id").readNullable[Long] and
      (__ \ "title").read[String](minLength[String](1)) and
      (__ \ "subscriberId").read[Long](ifObjectExists[SubscriberTable, SubscriberModel]) and
      (__ \ "channelId").read[Long](ifObjectExists[ChannelTable, ChannelModel]) and
      (__ \ "cfg").read[String]
    )(ChannelSubscriberModel.apply _)


  def ifObjectExists[T <: BaseTable[M], M <: BaseModel[M]](implicit tq: TableQuery[T]) =
    filterNot[Long](ValidationError("object not found")){id =>
      val dao = new Dao[T, M]
      val c = dao.findById(id)
      Await.result(c, Duration.Inf).fold[Boolean](true)(o => false)
    }

  def format4ChannelSubscriber = Format(reads4ChannelSubscriber, write4ChannelSubscriber)

  def format4Subscriber: Format[SubscriberModel] = (
    (JsPath \ "id").formatNullable[Long] and
      (JsPath \ "title").format[String](minLength[String](1))
    )(SubscriberModel.apply, unlift(SubscriberModel.unapply))
}
