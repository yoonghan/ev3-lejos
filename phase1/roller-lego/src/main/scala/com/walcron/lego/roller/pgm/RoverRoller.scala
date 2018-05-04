package com.walcron.lego.roller.pgm

import akka.actor.ActorRef
import com.walcron.lego.roller.connector.WebsocketClient
import com.walcron.lego.roller.controller.MovementController
import com.walcron.lego.roller.hardware.TouchSensor1
import com.walcron.lego.roller.controller.ThreadedMotorController.EndMove
import akka.actor.actorRef2Scala
import com.walcron.lego.roller.impl.TouchSensorImpl
import com.walcron.lego.roller.controller.ThreadedReactSensor

class RoverRoller(activateBtn:Boolean, btnProp:ActorRef, sensorProp:ActorRef) {
  private def initClient(controller: ActorRef):WebsocketClient = {
    new WebsocketClient(Option(new MovementController(btnProp)))
  }
  
  private def touchSensorListener(controller: ActorRef) {
    sensorProp ! ThreadedReactSensor.Listen(stopMovement(controller))
  }
  
  def stopMovement(controller: ActorRef)() {
    import ThreadedRoverRoller._
    client.disconnect()
    controller ! EndMove
  }
  
	val client = initClient(btnProp)
	touchSensorListener(btnProp)
}