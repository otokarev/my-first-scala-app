package controllers

import javax.inject._

import daos._
import daos.models._
import daos.Tables._
import play.api.libs.json._

import scala.concurrent.ExecutionContext

@Singleton
class ChannelSubscriberController @Inject() (s: BaseDao[ChannelSubscriberTable, ChannelSubscriberModel], f: Format[ChannelSubscriberModel])(implicit exec: ExecutionContext)  extends AbstractController(s, f)(exec)
