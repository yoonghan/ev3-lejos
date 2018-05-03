package com.walcron.lego.roller.util

object Const {
  val API_PATH = "/rollcall"
  val CONNECTION_URI = s"ws://${Property.getServer()}${API_PATH}"
  
  sealed abstract class Directions()
  case object FORWARD extends Directions()
  case object BACKWARD extends Directions()
  case object RIGHT extends Directions()
  case object LEFT extends Directions()
  case object STOP extends Directions()
}
