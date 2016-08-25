My First Scala App
==================

It's the place where i'm training my scala skills

Description
-----------

Web-service with RESTful API.

Right now one can add `Subscriber` by sending

```
{"title": "<title>"}
```

to `POST /subscriber/`

Out of scope for now
--------------------
1. Beautiful RESTful API
2. GUI

TODO
----
1. Authentication/Authorization
2. Sessions
3. Checkout Play's deployment procedures
4. Implement simple event-cracker app
5. Unit tests for testing with Kafka(?)

Debatable topics
----------------
1. test/ApplicationSpec: probably it is not best way to run test against `test` database configs by doing that:
   ```
   class ApplicationSpec extends PlaySpec with OneAppPerSuite with BeforeAndAfterAll {
   
     override implicit lazy val app: Application = new GuiceApplicationBuilder()
         .configure(Configuration(ConfigFactory.load("application.test.conf"))).build()
   ```
2. Possibly there better way to populate test db with data than:
```
  lazy val databaseApi = app.injector.instanceOf[DBApi]
  val db = databaseApi.database("default")
  Evolutions.applyEvolutions(db);
```
3. models/Subscriber: why not to hide SubscriberModel in Subscriber.Model?
4. controllers/SubscriberControllers: is it the best place for:
```
  implicit val locationFormat: Format[SubscriberModel] = (
    (JsPath \ "id").formatNullable[Long] and
      (JsPath \ "title").format[String](minLength[String](1))
    )(SubscriberModel.apply, unlift(SubscriberModel.unapply))
```
or maybe move it to models/Subscriber?
