import com.typesafe.config.ConfigFactory
import org.scalatest.BeforeAndAfterAll
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test._
import play.api.test.Helpers._
import org.scalatestplus.play._
import play.api.db.DBApi
import play.api.db.evolutions.Evolutions
import play.api.{Application, Configuration, Play}
import play.api.libs.json.Json
import play.api.libs.json.Json._

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
  Evolutions.applyEvolutions(db);

  "Subscriber" should {

    "create new instance with title=First" in {
      val r1 = route(app, FakeRequest(POST, "/subscriber").withJsonBody(Json.obj(
        "title" -> "First"
      ))).get

      println(contentAsString(r1))
      status(r1) mustBe OK
      contentType(r1) mustBe Some("text/json")
      (Json.parse(contentAsString(r1)) \ "object" \ "title").as[String] mustEqual "First"
    }

  }

//  "Routes" should {
//
//    "send 404 on a bad request" in {
//      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
//    }
//
//  }
//
//  "HomeController" should {
//
//    "render the index page" in {
//      val home = route(app, FakeRequest(GET, "/")).get
//
//      status(home) mustBe OK
//      contentType(home) mustBe Some("text/html")
//      contentAsString(home) must include("Your new application is ready.")
//    }
//
//  }
//
//  "CountController" should {
//
//    "return an increasing count" in {
//      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "0"
//      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "1"
//      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "2"
//    }
//
//  }

}

