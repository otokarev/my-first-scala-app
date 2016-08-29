My First Scala App
==================

It's the place where i'm training my scala skills

Description
-----------

###Web-service with RESTful API.

####Endpoints

* `/subscriber/`
* `/channel/`
* `/channel_subscriber/`
####Objects
#####Subscriber
```json
{"id": <id>, "title": "<title>"}
```
#####Channel
```json
{"id": "<id>", "title": "<title>"}
```
#####Subscriber's channel
```json
{"id": "<id>", "title": "<title>", "subscriberId": "<subscriber id>", "channelId": "<channel id>", "cfg": "<configs>"}
```
####Commands
#####Create new object
`POST /<endpoint>/`

Body:
```
{json ecoded object without `id`}
```

Success:
```
{json ecoded object}
```
Failure:
```
{"status":"KO","message": "<error message>"}
```
#####Modify existing object
`PUT /<endpoint>/<id>`
Body:
```
{json ecoded object}
```

Success:
```
{json ecoded object}
```
Failure:
```
{"status":"KO","message": "<error message>"}
```
#####List all objects
`GET /<endpoint>/`

Success:
```
{"status":"OK","items":[object1, ...]}
```
Failure:
```
{"status":"KO","message": "<error message>"}
```
#####Get object by id
`GET /<endpoint>/`

Success:
```
{<json ecoded object>}
```
Failure:
```
{"status":"KO","message": "<error message>"}
```

Out of scope for now
--------------------
1. Beautiful RESTful API
2. GUI

TODO
----
1. Implement extra validation for `POST /channel_subscriber/` to check releations with others models
1. Move Dao to daos
1. Move `implicit itemFormat: Format[]` in controllers to `tables` (or `models`?)
1. Authentication/Authorization
1. Sessions
1. Checkout Play's deployment procedures
1. Implement simple event-cracker app
1. Unit tests for testing with Kafka(?)

Debatable topics
----------------
1. test/ApplicationSpec: probably it is not best way to run test against `test` database configs by doing that:
   
   ```scala
   class ApplicationSpec extends PlaySpec with OneAppPerSuite with BeforeAndAfterAll {
   
     override implicit lazy val app: Application = new GuiceApplicationBuilder()
         .configure(Configuration(ConfigFactory.load("application.test.conf"))).build()
   ```
2. Possibly there better way to populate test db with data than:
   
   ```scala
     lazy val databaseApi = app.injector.instanceOf[DBApi]
     val db = databaseApi.database("default")
     Evolutions.applyEvolutions(db);
   ```
3. controllers/SubscriberControllers: is it the best place for:
   
   ```scala
     implicit val locationFormat: Format[SubscriberModel] = (
       (JsPath \ "id").formatNullable[Long] and
         (JsPath \ "title").format[String](minLength[String](1))
       )(SubscriberModel.apply, unlift(SubscriberModel.unapply))
   ```
or maybe move it to models/Subscriber?
