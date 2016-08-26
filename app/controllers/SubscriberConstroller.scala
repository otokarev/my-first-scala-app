package controllers

import javax.inject._

import play.api.mvc._
import models.Subscriber
import models.SubscriberModel
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class SubscriberController @Inject() (Subscriber: Subscriber) extends Controller {

  implicit val subscriberFormat: Format[SubscriberModel] = (
    (JsPath \ "id").formatNullable[Long] and
      (JsPath \ "title").format[String](minLength[String](1))
    )(SubscriberModel.apply, unlift(SubscriberModel.unapply))

  def post = Action.async(BodyParsers.parse.json) { request =>
    request.body.validate[SubscriberModel].fold(
      errors => {
        Future {
          BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors))).as("text/json")
        }
      },
      subscriber => {
        Subscriber.add(subscriber) map { subscriber =>
          Ok(Json.obj("status" -> "OK", "message" -> "object created", "object" -> subscriber)).as("text/json")
        } recover {
          case e => InternalServerError(Json.obj("status" -> "KO", "message" -> e.getMessage)).as("text/json")
        }
      })
  }
  def put(id:Long) = Action.async(BodyParsers.parse.json) { request => {
    request.body.validate[SubscriberModel]
      .filter(JsError(s"id is specified in the body and not equal to $id"))(s => {s.id.isEmpty || s.id.get == id})
      .fold(
        errors => {
          Future {
            BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors))).as("text/json")
          }
        },
        subscriber => {
          val subscriber1 = subscriber.copy(id=Option(id))
          Subscriber.replace(subscriber1) map { c =>
            Ok(Json.obj("status" -> "OK", "message" -> "object replaced")).as("text/json")
          } recover {
            case e => InternalServerError(Json.obj("status" -> "KO", "message" -> e.getMessage)).as("text/json")
          }
        })
  }}
  def delete(id: Long) = Action.async(BodyParsers.parse.json) { request =>
    Subscriber.delete(id) map { subscriber =>
      Ok(Json.obj("status" -> "OK", "message" -> "object deleted")).as("text/json")
    } recover {
      case e => InternalServerError(Json.obj("status" -> "KO", "message" -> e.getMessage)).as("text/json")
    }
  }
  def get(id: Long) = Action.async { request =>
    Subscriber.get(id) map { subscriber =>
      Ok(Json.obj("status" -> "OK", "message" -> "object retrieved", "object" -> subscriber)).as("text/json")
    } recover {
      case e => InternalServerError(Json.obj("status" -> "KO", "message" -> e.getMessage)).as("text/json")
    }
  }
}

