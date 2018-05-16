package com.walcron.lagom.lego.impl

import play.api.libs.json.{Format, Json}
import java.time.LocalDateTime
import com.walcron.lagom.lego.api.RollerMoveMessage
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, PersistentEntity}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import play.api.libs.json.{Format, Json, JsValue, JsResult}
import play.api.libs.json.Json._
import scala.collection.immutable.Seq
import com.lightbend.lagom.scaladsl.persistence.AggregateEventShards
import com.walcron.lagom.lego.api.RollerMovementChanged
import kamon.Kamon
import com.lightbend.lagom.scaladsl.pubsub.TopicId
import com.lightbend.lagom.scaladsl.pubsub.PubSubRegistry
import org.slf4j.LoggerFactory
import org.slf4j.MarkerFactory

class RollerEntity(pubSubRegistry: PubSubRegistry) extends PersistentEntity {
  
  val logger = LoggerFactory.getLogger(classOf[RollerEntity])
  val marker = MarkerFactory.getMarker("Roller Implementation")
  
  private val rollerTopic = {
    if(Option(pubSubRegistry).isDefined) 
      Option(pubSubRegistry.refFor(TopicId[String])) 
    else 
      Option.empty
  }
  
  val gadotCounter = Kamon.counter("gadot.counter")
  
  override type Command = RollerCommand[_]
  override type Event = RollerTimelineEvent
  override type State = RollerState

  override def initialState: RollerState = RollerState("Roller", LocalDateTime.now.toString)
  
  override def behavior: Behavior = {
    case RollerState(_, _) => Actions()
    .onCommand[Roller, String] {
      case (Roller(input), ctx, state) =>
        gadotCounter.increment()
        logger.info(marker, "command:" + input)
        val event = RollerMovementAdded(input)
        ctx.thenPersist(event) { _ =>
          if(rollerTopic.isDefined) {
            rollerTopic.get.publish(input)
          }
          ctx.reply(input)
        }
    }
    .onEvent {
      case (RollerMovementAdded(movement), state) =>
        logger.info(marker, "event:" + movement)
        RollerState(movement, LocalDateTime.now().toString)
    }
  }
}

sealed trait RollerCommand[R] extends ReplyType[R]

case class Roller(move: String) extends RollerCommand[String]

object Roller {
  implicit val format: Format[Roller] = Json.format
}

case class RollerState(message: String, timestamp: String)

object RollerState {
  implicit val format: Format[RollerState] = Json.format
}

sealed trait RollerTimelineEvent extends AggregateEvent[RollerTimelineEvent] {
  override def aggregateTag = RollerTimelineEvent.Tag
}

object RollerTimelineEvent {
  val Tag = AggregateEventTag[RollerTimelineEvent]
}

case class RollerMovementAdded(message: String) extends RollerTimelineEvent

object RollerMovementAdded {
  implicit val format: Format[RollerMovementAdded] = Json.format
}

object RollerSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(
    JsonSerializer[Roller],
    JsonSerializer[RollerState],
    JsonSerializer[RollerMovementAdded],
    JsonSerializer[RollerMovementChanged]
  )
}
