package com.walcron.lego.roller.controller

import com.walcron.lego.roller.impl.TouchSensorImpl
import com.walcron.lego.roller.impl.TouchSensorImpl
import akka.actor.Actor
import akka.actor.Props
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._
import com.walcron.lego.roller.pgm.ThreadedRoverRoller._
import akka.actor.PoisonPill

class ThreadedReactSensor(touchSensor:TouchSensorImpl) extends Actor {
  import ThreadedReactSensor._
  
  def receive = {
    case Listen(execFunc) => {
      touchSensor.waitForTouch()
      execFunc()
      context.stop(self)
    }
  }
}

object ThreadedReactSensor {
  def props(touchSensor:TouchSensorImpl):Props = Props(new ThreadedReactSensor(touchSensor))
  case class Listen(execFunc:() => Unit)
}