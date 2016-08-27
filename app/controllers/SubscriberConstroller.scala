package controllers

import javax.inject._

import play.api.mvc._
import models.Subscriber
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import services.SubscriberService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class SubscriberController @Inject() (SubscriberService: SubscriberService) extends Controller {

  implicit val subscriberFormat: Format[Subscriber] = (
    (JsPath \ "id").formatNullable[Long] and
      (JsPath \ "title").format[String](minLength[String](1))
    )(Subscriber.apply, unlift(Subscriber.unapply))
  trait ModelConverter[T] {

  }

  def post = Action.async(BodyParsers.parse.json) { request =>
    request.body.validate[Subscriber].fold(
      errors => {
        Future {
          BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors))).as("text/json")
        }
      },
      subscriber => {
        SubscriberService.add(subscriber) map { subscriber =>
          Ok(Json.obj("status" -> "OK", "message" -> "object created", "object" -> subscriber)).as("text/json")
        } recover {
          case e => InternalServerError(Json.obj("status" -> "KO", "message" -> e.getMessage)).as("text/json")
        }
      })
  }
  def put(id:Long) = Action.async(BodyParsers.parse.json) { request => {
    request.body.validate[Subscriber]
      .filter(JsError(s"id is specified in the body and not equal to $id"))(s => {s.id.isEmpty || s.id.get == id})
      .fold(
        errors => {
          Future {
            BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors))).as("text/json")
          }
        },
        subscriber => {
          val subscriber1 = subscriber.copy(id=Option(id))
          SubscriberService.replace(subscriber1) map { c =>
            Ok(Json.obj("status" -> "OK", "message" -> "object replaced")).as("text/json")
          } recover {
            case e => InternalServerError(Json.obj("status" -> "KO", "message" -> e.getMessage)).as("text/json")
          }
        })
  }}
  def delete(id: Long) = Action.async(BodyParsers.parse.json) { request =>
    SubscriberService.delete(id) map { subscriber =>
      Ok(Json.obj("status" -> "OK", "message" -> "object deleted")).as("text/json")
    } recover {
      case e => InternalServerError(Json.obj("status" -> "KO", "message" -> e.getMessage)).as("text/json")
    }
  }
  def get(id: Long) = Action.async { request =>
    SubscriberService.get(id) map { subscriber =>
      Ok(Json.toJson(subscriber)).as("text/json")
    } recover {
      case e => InternalServerError(Json.obj("status" -> "KO", "message" -> e.getMessage)).as("text/json")
    }
  }
  def list() = Action { request => {
      Try {
        val f = SubscriberService.get()
        val subscribers = Await.result(f, Duration.Inf)
        Ok(Json.obj("status" -> "OK", "items" -> subscribers)).as("text/json")
      } match {
        case Success(r) => r
        case Failure(e) => InternalServerError(Json.obj("status" -> "KO", "message" -> e.getMessage)).as("text/json")
      }
    }
  }
}
