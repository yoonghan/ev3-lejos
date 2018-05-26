package com.walcron.lagom.lego.impl

import play.api.libs.json.{Format, Json}
import java.time.LocalDateTime
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
import com.walcron.lagom.lego.api._

class RollerEntity(pubSubRegistry: PubSubRegistry) extends PersistentEntity {
  
  val logger = LoggerFactory.getLogger(classOf[RollerEntity])
  val marker = MarkerFactory.getMarker("Roller Implementation")
  
  private def rollerTopic(id:String) = {
    if(Option(pubSubRegistry).isDefined) 
      Option(pubSubRegistry.refFor(TopicId[RollerTopicDirection](id))) 
    else 
      Option.empty
  }
  
  private def faceDirection(movement:String, currentDirection:Int):Int = {
    movement match {
      case "D" => if(currentDirection > 6) 0 else currentDirection + 1
      case "A" => if(currentDirection < 1) 7 else currentDirection - 1
      case _ => currentDirection
    }
  }
  
  val gadotCounter = Kamon.counter("gadot.counter")
  
  override type Command = RollerCommand[_]
  override type Event = RollerTimelineEvent
  override type State = RollerState

  override def initialState: RollerState = RollerState("F", 0, LocalDateTime.now.toString)
  
  override def behavior: Behavior = {
    case RollerState(sMovement, sDirection, sTime) => Actions()
    .onCommand[Roller, String] {
      case (Roller(id, input), ctx, state) =>
        gadotCounter.increment()
        logger.info(marker, s"""command: $input, $sDirection""")
        val event = RollerMovementAdded(input, faceDirection(input, sDirection))
        ctx.thenPersist(event) { _ =>
          if(rollerTopic(id).isDefined) {
            logger.info(marker, s"""publish: ${event.message}, ${event.direction}""")
            rollerTopic(id).get.publish(RollerTopicDirection(event.message, event.direction))
          }
          ctx.reply(input)
        }
    }
    .onEvent {
      case (RollerMovementAdded(movement, direction), state) =>
        logger.info(marker, s"""event: $movement, $direction""")
        RollerState(movement, direction, LocalDateTime.now().toString)
    }
  }
}

sealed trait RollerCommand[R] extends ReplyType[R]

case class Roller(id:String, move: String) extends RollerCommand[String]

object Roller {
  implicit val format: Format[Roller] = Json.format
}

case class RollerState(message: String, direction:Int, timestamp: String)

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

case class RollerMovementAdded(message: String, direction: Int) extends RollerTimelineEvent

object RollerMovementAdded {
  implicit val format: Format[RollerMovementAdded] = Json.format
}

object RollerSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(
    JsonSerializer[Roller],
    JsonSerializer[RollerState],
    JsonSerializer[RollerMovementAdded],
    JsonSerializer[RollerMovementChanged],
    JsonSerializer[RollerTopicDirection]
  )
}
