package daos

import java.util.UUID

import daos.models.BaseModel
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import slick.lifted.{CanBeQueryCondition, TableQuery}
import Tables._

import scala.concurrent.{ExecutionContext, Future}


trait BaseDao[T,A] {
  def insert(row : A): Future[Any]
  def update(row : A): Future[Int]
  def update(rows : Seq[A]): Future[Unit]
  def findById(id : UUID): Future[Option[A]]
  def findByFilter[C : CanBeQueryCondition](f: (T) => C): Future[Seq[A]]
  def deleteById(id : UUID): Future[Int]
  def deleteById(ids : Seq[UUID]): Future[Int]
  def deleteByFilter[C : CanBeQueryCondition](f:  (T) => C): Future[Int]
}


class Dao[T <: BaseTable[A], A <: BaseModel[A]]()(
  implicit val tableQ: TableQuery[T],
  dbConfig: DatabaseConfig[JdbcProfile],
  executionContext: ExecutionContext
) extends BaseDao[T,A] {
  import dbConfig.driver.api._
  val db = dbConfig.db

  def insert(row : A) = {
    db.run(tableQ ++= Seq(row).filter(_.isValid))
  }

  def update(row : A): Future[Int] = {
    if (row.isValid)
      db.run(tableQ.filter(_.id === row.id).update(row))
    else
      Future{0}
  }

  def update(rows : Seq[A]): Future[Unit] = {
    db.run(DBIO.seq(rows.filter(_.isValid).map(r => tableQ.filter(_.id === UUID.fromString(r.id.get.toString.replace("-", ""))).update(r)): _*))
  }

  def findById(id : UUID): Future[Option[A]] = {
    db.run(tableQ.filter(_.id === id).result.headOption)
  }

  def findByFilter[C : CanBeQueryCondition](f: (T) => C): Future[Seq[A]] = {
    db.run(tableQ.withFilter(f).result)
  }

  def deleteById(id : UUID): Future[Int] = {
    deleteById(Seq(id))
  }

  def deleteById(ids : Seq[UUID]): Future[Int] = {
    db.run(tableQ.filter(_.id.inSet(ids)).delete)
  }

  def deleteByFilter[C : CanBeQueryCondition](f:  (T) => C): Future[Int] = {
    db.run(tableQ.withFilter(f).delete)
  }

}

