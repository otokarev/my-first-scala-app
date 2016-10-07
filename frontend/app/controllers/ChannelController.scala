package controllers

import javax.inject._

import daos._
import daos.models._
import daos.Tables._
import play.api.libs.json._

import scala.concurrent.ExecutionContext

@Singleton
class ChannelController @Inject() (s: BaseDao[ChannelTable, ChannelModel], f: Format[ChannelModel])(implicit exec: ExecutionContext)  extends AbstractController(s, f)(exec)
