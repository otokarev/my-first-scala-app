package daos

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

object Utils {
  lazy val databaseConfig = DatabaseConfig.forConfig[JdbcProfile]("slick.dbs.default")
  def getDatabaseConfig = databaseConfig
}
