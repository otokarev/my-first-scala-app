package controllers

import javax.inject._

import play.api.mvc._
import models.Subscriber
import models.SubscriberModel
import play.api.libs.json.{Format, JsError, JsPath, Json}
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class SubscriberController @Inject() (Subscriber: Subscriber) extends Controller {

  implicit val locationFormat: Format[SubscriberModel] = (
    (JsPath \ "id").formatNullable[Long] and
      (JsPath \ "title").format[String](minLength[String](1))
    )(SubscriberModel.apply, unlift(SubscriberModel.unapply))

  def post = Action.async(BodyParsers.parse.json) { request =>
    val subscriberResult = request.body.validate[SubscriberModel]

    subscriberResult.fold(
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

}

