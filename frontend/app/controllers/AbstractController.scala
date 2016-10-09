package controllers

import java.util.UUID
import javax.inject._

import daos.BaseDao
import daos.models._
import daos.Tables._
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

abstract class AbstractController[T <: BaseTable[M], M <: BaseModel[M]] @Inject()(service: BaseDao[T, M], format: Format[M])(implicit exec: ExecutionContext) extends Controller {

  implicit val itemFormat = format

  implicit val seqM2JsValueWrapper = new Writes[Seq[M]] {
    def writes(a: Seq[M]) = Json.toJson(a)
  }

  def post = Action.async(BodyParsers.parse.json) { request =>
    request.body.validate[M].fold(
      errors => {
        Future {
          BadRequest(Json.obj("status" -> "KO", "errors" -> JsError.toJson(errors))).as("text/json")
        }
      },
      r => {
        var rr = r

        if (r.id.isEmpty) {
          val id = UUID.randomUUID()
          rr = r.copy(id=Option(id))
        }

        service.insert(rr) map { id =>
          Ok(Json.obj("status" -> "OK", "message" -> "object created", "object" -> rr)).as("text/json")
        } recover {
          case e => InternalServerError(Json.obj("status" -> "KO", "errors" -> e.getMessage)).as("text/json")
        }
      })
  }
  def put(sid: String) = Action.async(BodyParsers.parse.json) { request => {
    val id = UUID.fromString(sid)

    request.body.validate[M]
      .filter(JsError(s"id is specified in the body and not equal to $id"))(s => {s.id.isEmpty || s.id.get == id})
      .fold(
        errors => {
          Future {
            BadRequest(Json.obj("status" -> "KO", "errors" -> JsError.toJson(errors))).as("text/json")
          }
        },
        (r: M) => {
          val cr = r.copy(id=Option(id))
          service.update(cr) map { c =>
            Ok(Json.obj("status" -> "OK", "message" -> "object replaced")).as("text/json")
          } recover {
            case e => InternalServerError(Json.obj("status" -> "KO", "errors" -> e.getMessage)).as("text/json")
          }
        })
  }}
  def delete(sid: String) = Action.async { request =>
    val id = UUID.fromString(sid)

    service.deleteById(id) map { r =>
      Ok(Json.obj("status" -> "OK", "message" -> "object deleted")).as("text/json")
    } recover {
      case e => InternalServerError(Json.obj("status" -> "KO", "errors" -> e.getMessage)).as("text/json")
    }
  }
  def get(sid: String) = Action.async { request =>
    val id = UUID.fromString(sid)

    service.findById(id) map { r =>
      Ok(Json.toJson(r)).as("text/json")
    } recover {
      case e => InternalServerError(Json.obj("status" -> "KO", "errors" -> e.getMessage)).as("text/json")
    }
  }
  def list() = Action { request =>
    Try {
      val f = service.findByFilter(_.id != 0)
      val rs = Await.result(f, Duration.Inf)
      Ok(Json.obj("status" -> "OK", "items" -> rs)).as("text/json")
    } match {
      case Success(r) => r
      case Failure(e) => InternalServerError(Json.obj("status" -> "KO", "errors" -> e.getMessage)).as("text/json")
    }
  }
}

