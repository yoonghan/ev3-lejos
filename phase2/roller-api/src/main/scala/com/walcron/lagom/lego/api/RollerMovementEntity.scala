package com.walcron.lagom.lego.api

import java.time.Instant
import java.util.UUID
import com.fasterxml.jackson.annotation.JsonIgnore

import play.api.libs.json.Json._
import play.api.libs.json.{Format, Json, JsValue, JsResult}

case class RollerMovementChanged @JsonIgnore()(id:String, message: String, direction:Int, timestamp: Instant, uuid: String) {
  def this(id:String, message: String, direction: Int) =
    this(id, message, direction, RollerMovementChanged.defaultTimestamp, RollerMovementChanged.defaultUUID)
}

object RollerMovementChanged {
  implicit object RollerMovementChangedOrder extends Ordering[RollerMovementChanged] {
    override def compare(x: RollerMovementChanged, y: RollerMovementChanged): Int = x.timestamp.compareTo(y.timestamp)
  }

  def apply(id:String, message: String, direction: Int, timestamp: Option[Instant], uuid: Option[String]): RollerMovementChanged =
    new RollerMovementChanged(id, message, direction, timestamp.getOrElse(defaultTimestamp), uuid.getOrElse(defaultUUID))

  private def defaultTimestamp = Instant.now()
  private def defaultUUID = UUID.randomUUID().toString()
  
  implicit val format: Format[RollerMovementChanged] = Json.format[RollerMovementChanged]
}

case class RollerTopicDirection(message:String, direction:Int)

object RollerTopicDirection {
  implicit val format: Format[RollerTopicDirection] = Json.format
}
