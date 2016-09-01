import com.typesafe.config.ConfigFactory
import org.scalatest.BeforeAndAfterAll
import org.scalatestplus.play._
import play.api.db.DBApi
import play.api.db.evolutions.Evolutions
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.libs.json.Json._
import play.api.test.Helpers._
import play.api.test._
import play.api.{Application, Configuration}

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
class ApplicationSpec extends PlaySpec with OneAppPerSuite with BeforeAndAfterAll {

  override implicit lazy val app: Application = new GuiceApplicationBuilder()
      .configure(Configuration(ConfigFactory.load("application.test.conf"))).build()

  lazy val databaseApi = app.injector.instanceOf[DBApi]
  val db = databaseApi.database("default")
  Evolutions.applyEvolutions(db)

  "Subscriber" should {

    "create new instance with title=First" in {
      val r1 = route(app, FakeRequest(POST, "/subscriber/").withJsonBody(Json.obj(
        "title" -> "First"
      ))).get

      println(contentAsString(r1))
      status(r1) mustBe OK
      contentType(r1) mustBe Some("text/json")
      (Json.parse(contentAsString(r1)) \ "object" \ "title").as[String] mustEqual "First"

      (Json.parse(contentAsString(r1)) \ "object" \ "id").as[Long] mustEqual 1
    }

    "Update just created instance with title=Second" in {
      val r2 = route(app, FakeRequest(PUT, "/subscriber/1").withJsonBody(Json.obj(
        "title" -> "Second"
      ))).get
      println(contentAsString(r2))

      status(r2) mustBe OK
      val r3 = route(app, FakeRequest(GET, "/subscriber/1")).get
      println(contentAsString(r3))
      status(r3) mustBe OK
      (Json.parse(contentAsString(r3)) \ "title").as[String] mustEqual "Second"
      (Json.parse(contentAsString(r3)) \ "id").as[Long] mustEqual 1
    }

    "Check that under /subscriber/ our new record is also available" in {
      val r4 = route(app, FakeRequest(GET, "/subscriber/")).get
      println(contentAsString(r4))

      status(r4) mustBe OK
      ((Json.parse(contentAsString(r4)) \ "items")(0) \ "title").as[String] mustEqual "Second"
      ((Json.parse(contentAsString(r4)) \ "items")(0) \ "id").as[Long] mustEqual 1
    }

    "Check that under /channel/ our `Default` channel is available" in {
      val r = route(app, FakeRequest(GET, "/channel/")).get
      println(contentAsString(r))

      status(r) mustBe OK
      ((Json.parse(contentAsString(r)) \ "items")(0) \ "title").as[String] mustEqual "Default"
      ((Json.parse(contentAsString(r)) \ "items")(0) \ "id").as[Long] mustEqual 1
    }

    "Check POST /channel-subscriber with wrong channelId" in {
      val r = route(app, FakeRequest(POST, "/channel-subscriber/").withJsonBody(Json.obj(
        "title" -> "Second", "channelId" -> 3, "subscriberId" -> 1, "cfg" -> "config1"
      ))).get
      println(contentAsString(r))
      ((Json.parse(contentAsString(r)) \ "errors" \ "obj.channelId" )(0) \ "msg")(0).as[String] mustEqual "object not found"
    }

    "Check POST /channel-subscriber with wrong subscriberId" in {
      val r = route(app, FakeRequest(POST, "/channel-subscriber/").withJsonBody(Json.obj(
        "title" -> "Second", "channelId" -> 1, "subscriberId" -> 2, "cfg" -> "config1"
      ))).get
      println(contentAsString(r))
      ((Json.parse(contentAsString(r)) \ "errors" \ "obj.subscriberId" )(0) \ "msg")(0).as[String] mustEqual "object not found"
    }
  }

}

