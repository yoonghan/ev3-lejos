package com.walcron.lego.roller.util

object Const {
  val API_PATH = "/stream"
  val REGISTER_API_PATH = "/stream/register"
  val CONNECTION_URI_SEND = s"ws://${Property.getServer()}${API_PATH}"
  val CONNECTION_URI_RECEIVE = s"ws://${Property.getServer()}${REGISTER_API_PATH}"
  
  sealed abstract class Directions()
  case object FORWARD extends Directions()
  case object BACKWARD extends Directions()
  case object RIGHT extends Directions()
  case object LEFT extends Directions()
  case object STOP extends Directions()
}
