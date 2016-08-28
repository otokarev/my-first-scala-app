package controllers

import javax.inject._

import models.{BaseDao, ChannelSubscriberModel}
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.mvc._
import tables.Tables.ChannelSubscriberTable

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

@Singleton
class ChannelSubscriberController @Inject()(service: BaseDao[ChannelSubscriberTable, ChannelSubscriberModel])(implicit exec: ExecutionContext) extends Controller {

  implicit val itemFormat: Format[ChannelSubscriberModel] = (
    (__ \ "id").formatNullable[Long] and
      (__ \ "title").format[String](minLength[String](1)) and
      (__ \ "subscriberId").format[Long] and
      (__ \ "channelId").format[Long] and
      (__ \ "cfg").format[String]
    )(ChannelSubscriberModel.apply, unlift(ChannelSubscriberModel.unapply))

  def post = Action.async(BodyParsers.parse.json) { request =>
    request.body.validate[ChannelSubscriberModel].fold(
      errors => {
        Future {
          BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors))).as("text/json")
        }
      },
      r => {
        service.insert(r) map { r =>
          Ok(Json.obj("status" -> "OK", "message" -> "object created", "object" -> r)).as("text/json")
        } recover {
          case e => InternalServerError(Json.obj("status" -> "KO", "message" -> e.getMessage)).as("text/json")
        }
      })
  }
  def put(id:Long) = Action.async(BodyParsers.parse.json) { request => {
    request.body.validate[ChannelSubscriberModel]
      .filter(JsError(s"id is specified in the body and not equal to $id"))(s => {s.id.isEmpty || s.id.get == id})
      .fold(
        errors => {
          Future {
            BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors))).as("text/json")
          }
        },
        r => {
          val cr = r.copy(id=Option(id))
          service.update(cr) map { c =>
            Ok(Json.obj("status" -> "OK", "message" -> "object replaced")).as("text/json")
          } recover {
            case e => InternalServerError(Json.obj("status" -> "KO", "message" -> e.getMessage)).as("text/json")
          }
        })
  }}
  def delete(id: Long) = Action.async(BodyParsers.parse.json) { request =>
    service.deleteById(id) map { r =>
      Ok(Json.obj("status" -> "OK", "message" -> "object deleted")).as("text/json")
    } recover {
      case e => InternalServerError(Json.obj("status" -> "KO", "message" -> e.getMessage)).as("text/json")
    }
  }
  def get(id: Long) = Action.async { request =>
    service.findById(id) map { r =>
      Ok(Json.toJson(r)).as("text/json")
    } recover {
      case e => InternalServerError(Json.obj("status" -> "KO", "message" -> e.getMessage)).as("text/json")
    }
  }
  def list() = Action { request =>
    Try {
      val f = service.findByFilter(_.id != 0)
      val rs = Await.result(f, Duration.Inf)
      Ok(Json.obj("status" -> "OK", "items" -> rs)).as("text/json")
    } match {
      case Success(r) => r
      case Failure(e) => InternalServerError(Json.obj("status" -> "KO", "message" -> e.getMessage)).as("text/json")
    }
  }
}

