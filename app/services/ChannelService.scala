package services

import javax.inject.Inject

import models.Channel
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

class ChannelService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val channels = TableQuery[Schema]

  class Schema(tag: Tag) extends Table[Channel](tag, "channel") {

    def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
    def title = column[String]("title")

    def * = (id.?, title) <> (Channel.tupled, Channel.unapply)
  }

  def get() = {
    db.run(channels.result)
  }
}
