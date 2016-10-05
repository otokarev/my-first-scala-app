package controllers

import javax.inject._

import daos.BaseDao
import models.SubscriberModel
import play.api.libs.json._
import tables.Tables.SubscriberTable

import scala.concurrent.ExecutionContext

@Singleton
class SubscriberController @Inject() (s: BaseDao[SubscriberTable, SubscriberModel], f: Format[SubscriberModel])(implicit exec: ExecutionContext)  extends AbstractController(s, f)(exec)
