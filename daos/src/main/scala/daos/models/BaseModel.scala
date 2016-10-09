package daos.models
import java.util.UUID

trait BaseModel [T <: BaseModel[T]] {
  val id: Option[UUID]
  def isValid = true
  def copy(id: Option[UUID]) : T
}


