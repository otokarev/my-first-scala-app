package controllers

import javax.inject._

import daos._
import daos.models._
import daos.Tables._
import play.api.libs.json._

import scala.concurrent.ExecutionContext

@Singleton
class SubscriberController @Inject() (s: BaseDao[SubscriberTable, SubscriberModel], f: Format[SubscriberModel])(implicit exec: ExecutionContext)  extends AbstractController(s, f)(exec)
