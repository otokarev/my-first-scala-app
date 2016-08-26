package services

import javax.inject.Inject

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import models.Subscriber

import scala.concurrent.Future

class SubscriberService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val subscribers = TableQuery[Schema]

  class Schema(tag: Tag) extends Table[Subscriber](tag, "subscriber") {

    def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
    def title = column[String]("title")

    def * = (id.?, title) <> (Subscriber.tupled, Subscriber.unapply)
  }

  def add(subscriber: Subscriber): Future[Subscriber] = {
    db.run((subscribers returning subscribers.map(_.id) into ((subscribers, id) => subscribers.copy(id = Some(id)))) += subscriber)
  }

  def replace(subscriber: Subscriber): Future[Int] = {
    db.run(subscribers.filter(_.id === subscriber.id).update(subscriber))
  }

  def delete(id: Long): Future[Int] = {
    db.run(subscribers.filter(_.id === id).delete)
  }

  def get(id: Long): Future[Option[Subscriber]] = {
    db.run(subscribers.filter(_.id === id).result.headOption)
  }

}
