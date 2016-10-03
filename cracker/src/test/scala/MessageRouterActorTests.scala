import akka.actor.ActorSystem
import akka.testkit.{EventFilter, ImplicitSender, TestKit, TestProbe}
import org.scalatest._
import com.typesafe.config.ConfigFactory

import cracker.actors.MessageRouterActor

class MessageRouterActorTests(_system: ActorSystem) extends TestKit(_system: ActorSystem)
  with ImplicitSender
  with WordSpecLike
  with BeforeAndAfterAll
  with BeforeAndAfterEach
{
  def this() = this(
    ActorSystem(
      "MessageRouterActorSpec",
      ConfigFactory.parseString("""
  akka.loggers = ["akka.testkit.TestEventListener"]
                                """)))

  override def afterAll() = TestKit.shutdownActorSystem(system)

  "Message router actor" must {
    "accept events with correct channel and subscriber ids" in {
      val parentProbe = TestProbe()
      val sourceRef = system.actorOf(MessageRouterActor.props(parent=parentProbe.ref))

      val sentMsg = FakeSource.get()
      EventFilter.info(message = s"Event processed: uuid: ${sentMsg.uuid}; ev: ${sentMsg.event}", occurrences = 1) intercept {
        sourceRef ! sentMsg
      }
    }

  }

}
