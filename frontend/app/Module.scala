import java.time.Clock

import com.google.inject.{AbstractModule, Provides}
import play.api.libs.json.Format

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.

 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module extends AbstractModule {
  import formats.Formats._
  import daos.models._
  import play.api.libs.concurrent.Execution.Implicits._

  override def configure() = {
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
  }

  @Provides def subscriberDao = daos.subscriberDao
  @Provides def channelDao = daos.channelDao
  @Provides def channelSubscriberDao = daos.channelSubscriberDao

  @Provides def subscriberFormat : Format[SubscriberModel] = format4Subscriber
  @Provides def channelFormat : Format[ChannelModel] = format4Channel
  @Provides def channelSubscriberFormat :  Format[ChannelSubscriberModel] = format4ChannelSubscriber
}
