package daos.models

trait BaseModel [T <: BaseModel[T]] {
  val id: Option[Long]
  def isValid = true
  def copy(id: Option[Long]) : T
}


