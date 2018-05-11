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

class RollerEntity(pubSubRegistry: PubSubRegistry) extends PersistentEntity {
  
  private val rollerTopic = {
    if(Option(pubSubRegistry).isDefined) 
      Option(pubSubRegistry.refFor(TopicId[String])) 
    else 
      Option.empty
  }
  
  val gadotCounter = Kamon.counter("gadot.counter")
  val gadotRollerMoveSpan = Kamon.buildSpan("gadot-move-roller").withMetricTag("component", "netty.server")
  
  override type Command = RollerCommand[_]
  override type Event = RollerTimelineEvent
  override type State = RollerState

  override def initialState: RollerState = RollerState("Roller", LocalDateTime.now.toString)
  
  override def behavior: Behavior = {
    case RollerState(_, _) => Actions()
    .onCommand[Roller, String] {
      case (Roller(input), ctx, state) =>
        gadotRollerMoveSpan.start()
        gadotCounter.increment()
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
        val gadotSpan = Kamon.currentSpan()
        gadotSpan.finish()
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
  override def aggregateTag: AggregateEventShards[RollerTimelineEvent] = RollerTimelineEvent.Tag
}

object RollerTimelineEvent {
  val NumShards = 3
  val Tag = AggregateEventTag.sharded[RollerTimelineEvent](NumShards)
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
