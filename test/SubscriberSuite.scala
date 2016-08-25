//import models.{Subscriber, SubscriberModel}
//import org.scalatest._
//import org.scalatest.concurrent.ScalaFutures
//import org.scalatest.time.{Seconds, Span}
//import slick.driver.MySQLDriver.api._
//import slick.jdbc.meta._
//
//import scala.util.Try
//
//class SubscriberSuite extends FunSuite with BeforeAndAfter with ScalaFutures {
//  implicit override val patienceConfig = PatienceConfig(timeout = Span(5, Seconds))
//
//  val subscribers = TableQuery[Subscriber]
//
//  var db: Database = _
//
//  def createSchema() =
//    db.run(subscribers.schema.create).futureValue
//
//  before {
//    db = Database.forConfig("mysqlMfa")
//    Try {db.run(subscribers.schema.drop).futureValue}
//  }
//
//  test("Creating the Schema works") {
//    createSchema()
//
//    val tables = db.run(MTable.getTables).futureValue
//
//    assert(tables.size == 1)
//    assert(tables.count(_.name.name.equalsIgnoreCase("subscriber")) == 1)
//  }
//
//  test("Inserting a Subscriber works") {
//    createSchema()
//
//    val s = Subscribers.add("Test Subscriber").futureValue
//    assert(s.title == "Test Subscriber")
//    assert(s.id.isDefined)
//
//    val s1 = Subscribers.get(s.id.get).futureValue.get
//    assert(s1.id.get == s.id.get)
//
//    val s2 = Subscribers.delete(s.id.get).futureValue
//    assert(s2 == 1)
//  }
//
//  after {
//    Try {db.run(subscribers.schema.drop).futureValue}
//    db.close
//  }
//}