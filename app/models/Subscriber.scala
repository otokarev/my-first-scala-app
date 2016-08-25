package models

import javax.inject.Inject

import slick.driver.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import scala.concurrent.Future


case class SubscriberModel(id: Option[Long], title: String)

class Subscriber @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val subscribers = TableQuery[Schema]

  class Schema(tag: Tag) extends Table[SubscriberModel](tag, "subscriber") {

    def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
    def title = column[String]("title")

    def * = (id.?, title) <> (SubscriberModel.tupled, SubscriberModel.unapply)
  }

  def add(subscriber: SubscriberModel): Future[SubscriberModel] = {
    db.run((subscribers returning subscribers.map(_.id) into ((subscribers, id) => subscribers.copy(id = Some(id)))) += subscriber)
  }

  def delete(id: Long): Future[Int] = {
    db.run(subscribers.filter(_.id === id).delete)
  }

  def get(id: Long): Future[Option[SubscriberModel]] = {
    db.run(subscribers.filter(_.id === id).result.headOption)
  }

  def listAll: Future[Seq[SubscriberModel]] = {
    db.run(subscribers.result)
  }

}
