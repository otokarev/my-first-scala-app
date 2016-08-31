package controllers

import javax.inject._

import daos.BaseDao
import models.ChannelSubscriberModel
import play.api.libs.json._
import tables.Tables.ChannelSubscriberTable

import scala.concurrent.ExecutionContext

@Singleton
class ChannelSubscriberController @Inject() (s: BaseDao[ChannelSubscriberTable, ChannelSubscriberModel], f: Format[ChannelSubscriberModel])(implicit exec: ExecutionContext)  extends AbstractController(s, f)(exec)
