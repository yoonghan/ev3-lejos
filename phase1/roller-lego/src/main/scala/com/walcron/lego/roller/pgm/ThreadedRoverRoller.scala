package com.walcron.lego.roller.pgm

import akka.actor.Actor
import akka.actor.Props
import com.walcron.lego.roller.controller.MotorMovementController
import com.walcron.lego.roller.controller.ThreadedMotorController
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._
import com.walcron.lego.roller.impl.TouchSensorImpl
import com.walcron.lego.roller.controller.ThreadedReactSensor
import akka.actor.Terminated
import com.walcron.lego.roller.connector.WebsocketClient
import com.walcron.lego.roller.util.Const

/**
 * Expected button is already bind, the listener behind just waits for the signal.
 */
class ThreadedRoverRoller(activateBtn:Boolean, motorController:MotorMovementController, touchSensor:TouchSensorImpl) extends Actor {
  
  import ThreadedRoverRoller._
  import context._

  val sensorButtonActor = context.actorOf(ThreadedReactSensor.props(touchSensor), "touchSensorActor")
  
  def receive = {
    case Go => {
      val threadedMotorControllerActor = context.actorOf(ThreadedMotorController.props(motorController), "motorActor")
      context.watch(threadedMotorControllerActor)
      new RoverRoller(activateBtn, threadedMotorControllerActor, sensorButtonActor)
    }
    case Terminated(who) => {
      self ! Go
    }
  }
  
  override def postStop():Unit = {
    println("END")
  }
}

object ThreadedRoverRoller {
  def props(activateBtn:Boolean, motorController:MotorMovementController, touchSensor:TouchSensorImpl):Props = Props(new ThreadedRoverRoller(activateBtn, motorController, touchSensor))
  case object Go
  class ThreadedRoverRollerRestartException extends Exception
}
