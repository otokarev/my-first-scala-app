package formats

import java.util.UUID

import play.api.data.validation.ValidationError
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{Format, JsPath, _}
import slick.lifted.TableQuery

import scala.concurrent.Await
import scala.concurrent.duration.Duration



object Formats {
  import daos.models._
  import daos.Tables._
  import play.api.libs.concurrent.Execution.Implicits._

  def format4Channel: Format[ChannelModel] = (
    (JsPath \ "id").formatNullable[UUID] and
      (JsPath \ "title").format[String](minLength[String](1)) and
      (JsPath \ "actorClass").format[String](minLength[String](1))
    )(ChannelModel.apply, unlift(ChannelModel.unapply))

  val write4ChannelSubscriber: Writes[ChannelSubscriberModel] = (
    (__ \ "id").writeNullable[UUID] and
      (__ \ "title").write[String] and
      (__ \ "subscriberId").write[UUID] and
      (__ \ "channelId").write[UUID] and
      (__ \ "cfg").write[String]
    )(unlift(ChannelSubscriberModel.unapply))

  implicit val reads4ChannelSubscriber: Reads[ChannelSubscriberModel] = (
    (__ \ "id").readNullable[UUID] and
      (__ \ "title").read[String](minLength[String](1)) and
      (__ \ "subscriberId").read[UUID](ifObjectExists[SubscriberTable, SubscriberModel]) and
      (__ \ "channelId").read[UUID](ifObjectExists[ChannelTable, ChannelModel]) and
      (__ \ "cfg").read[String]
    )(ChannelSubscriberModel.apply _)


  def ifObjectExists[T <: BaseTable[M], M <: BaseModel[M]](implicit tq: TableQuery[T]) =
    filterNot[UUID](ValidationError("object not found")){id =>
      import daos._
      val dao = new Dao[T, M]
      val c = dao.findById(id)
      Await.result(c, Duration.Inf).fold[Boolean](true)(o => false)
    }

  def format4ChannelSubscriber = Format(reads4ChannelSubscriber, write4ChannelSubscriber)

  def format4Subscriber: Format[SubscriberModel] = (
    (JsPath \ "id").formatNullable[UUID] and
      (JsPath \ "title").format[String](minLength[String](1))
    )(SubscriberModel.apply, unlift(SubscriberModel.unapply))
}
