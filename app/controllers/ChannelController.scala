package controllers

import javax.inject._

import models.Channel
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.mvc._
import services.{ChannelService, SubscriberService}

import scala.concurrent.duration.Duration
import scala.concurrent.Await
import scala.util.{Failure, Success, Try}

@Singleton
class ChannelController @Inject()(ChannelService: ChannelService) extends Controller {

  implicit val channelFormat: Format[Channel] = (
    (JsPath \ "id").formatNullable[Long] and
      (JsPath \ "title").format[String](minLength[String](1))
    )(Channel.apply, unlift(Channel.unapply))

  def list() = Action { request => {
      Try {
        val f = ChannelService.get()
        val results = Await.result(f, Duration.Inf)
        Ok(Json.obj("status" -> "OK", "items" -> results)).as("text/json")
      } match {
        case Success(r) => r
        case Failure(e) => InternalServerError(Json.obj("status" -> "KO", "message" -> e.getMessage)).as("text/json")
      }
    }
  }
}
