package com.walcron.lagom.lego.api

import java.time.Instant
import java.util.UUID
import com.fasterxml.jackson.annotation.JsonIgnore

import play.api.libs.json.Json._
import play.api.libs.json.{Format, Json, JsValue, JsResult}

case class RollerMovementChanged @JsonIgnore()(id:String, message: String, timestamp: Instant, uuid: String) {
  def this(id:String, message: String) =
    this(id, message, RollerMovementChanged.defaultTimestamp, RollerMovementChanged.defaultUUID)
}

object RollerMovementChanged {
  implicit object RollerMovementChangedOrder extends Ordering[RollerMovementChanged] {
    override def compare(x: RollerMovementChanged, y: RollerMovementChanged): Int = x.timestamp.compareTo(y.timestamp)
  }

  def apply(id:String, message: String, timestamp: Option[Instant], uuid: Option[String]): RollerMovementChanged =
    new RollerMovementChanged(id:String, message, timestamp.getOrElse(defaultTimestamp), uuid.getOrElse(defaultUUID))

  private def defaultTimestamp = Instant.now()
  private def defaultUUID = UUID.randomUUID().toString()
  
  implicit val format: Format[RollerMovementChanged] = Json.format[RollerMovementChanged]
}

case class RollerMoveMessage(move: Char)

object RollerMoveMessage {
  def characterModifier(str: String): Char = if(str != null && !str.isEmpty()) str.charAt(0) else ' '
  
  implicit val character = new Format[Char] {
    def writes(c: Char): JsValue = toJson(c.toString())
    def reads(json: JsValue): JsResult[Char] = fromJson[String](json).map(characterModifier)
  }
  implicit val format: Format[RollerMoveMessage] = Json.format[RollerMoveMessage]
}