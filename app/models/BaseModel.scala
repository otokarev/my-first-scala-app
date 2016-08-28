package models

trait BaseModel {
  val id: Option[Long]
  def isValid = true
}


