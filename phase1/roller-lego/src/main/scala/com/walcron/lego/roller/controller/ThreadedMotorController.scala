package com.walcron.lego.roller.controller

import akka.actor.Actor
import lejos.hardware.Button
import akka.actor.Props
import akka.actor.ActorSystem
import akka.actor.ActorRef
import akka.actor.actorRef2Scala
import lejos.hardware.Key
import com.walcron.lego.roller.impl.MovementImpl
import com.walcron.lego.roller.util.Const.Directions
import akka.actor.PoisonPill
import com.walcron.lego.roller.util.Const
import com.walcron.lego.roller.connector.WebsocketClient

/**
 * Expected button is already bind, the listener behind just waits for the signal.
 */
class ThreadedMotorController(motorMovementController: MotorMovementController) extends Actor {
  
  import ThreadedMotorController._
  
  def receive = {
    case MoveAction(direction) => {
      motorMovementController.move(direction)
    }
    case EndMove => {
      motorMovementController.move(Const.BACKWARD)
      val conn = new WebsocketClient(Option.empty, Const.CONNECTION_URI_SEND)
      conn.sendMessage("C")
      conn.disconnect()
      self ! PoisonPill
    }
    case Reconnect => {
      self ! PoisonPill
    }
  }
}

object ThreadedMotorController {
  def props(motorMovementController: MotorMovementController):Props = Props(new ThreadedMotorController(motorMovementController))
  case class MoveAction(direction: Directions)
  case object Reconnect
  case object EndMove
}
