import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext

package object daos {
  import daos.Dao
  import daos.models.{ChannelModel, ChannelSubscriberModel, SubscriberModel}
  import Tables.{ChannelSubscriberTable, ChannelTable, SubscriberTable}

  implicit val dbConfig: DatabaseConfig[JdbcProfile] = Utils.getDatabaseConfig

  def subscriberDao(implicit ec: ExecutionContext): BaseDao[SubscriberTable, SubscriberModel]  = new Dao[SubscriberTable,SubscriberModel]
  def channelDao(implicit ec: ExecutionContext): BaseDao[ChannelTable, ChannelModel] = new Dao[ChannelTable,ChannelModel]
  def channelSubscriberDao(implicit ec: ExecutionContext): BaseDao[ChannelSubscriberTable, ChannelSubscriberModel] = new Dao[ChannelSubscriberTable,ChannelSubscriberModel]
}
