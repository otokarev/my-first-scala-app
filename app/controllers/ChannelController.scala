package controllers

import javax.inject._

import daos.BaseDao
import models.ChannelModel
import play.api.libs.json._
import tables.Tables.ChannelTable

import scala.concurrent.ExecutionContext

@Singleton
class ChannelController @Inject() (s: BaseDao[ChannelTable, ChannelModel], f: Format[ChannelModel])(implicit exec: ExecutionContext)  extends AbstractController(s, f)(exec)
