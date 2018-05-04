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

class RollerEntity extends PersistentEntity {
  
  override type Command = RollerCommand[_]
  override type Event = RollerTimelineEvent
  override type State = RollerState

  override def initialState: RollerState = RollerState("Roller", LocalDateTime.now.toString)

  override def behavior: Behavior = {
    case RollerState(_, _) => Actions()
    .onCommand[Roller, String] {
      case (Roller(input), ctx, state) =>
        val event = RollerMovementAdded(input)
        ctx.thenPersist(event) { _ =>
          ctx.reply(input)
        }
    }
    .onEvent {
      case (RollerMovementAdded(movement), state) =>
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

object RollerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(
    JsonSerializer[Roller],
    JsonSerializer[RollerMovementAdded],
    JsonSerializer[RollerMovementChanged]
  )
}
