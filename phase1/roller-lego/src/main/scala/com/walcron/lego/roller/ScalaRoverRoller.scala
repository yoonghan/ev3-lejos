package com.walcron.lego.roller

import com.walcron.lego.roller.impl.MotorImpl
import com.walcron.lego.roller.connector.WebsocketClient
import com.walcron.lego.roller.controller.MotorMovementController
import com.walcron.lego.roller.controller.MovementController
import lejos.hardware.Button
import lejos.hardware.KeyListener
import lejos.hardware.Key
import akka.actor.ActorSystem
import akka.actor.ActorRef
import com.walcron.lego.roller.hardware.LegoExitButton
import akka.actor.PoisonPill
import com.walcron.lego.roller.hardware.TouchSensor1
import akka.actor.Props
import com.walcron.lego.roller.impl.TouchSensorImpl

class ScalaRoverRoller(leftMotor:MotorImpl, rightMotor:MotorImpl, rotorMotor:MotorImpl, touchSensor: TouchSensorImpl, activateBtn:Boolean) {  
  import com.walcron.lego.roller.pgm.ThreadedRoverRoller
  
  def shutdown(){
		leftMotor.close()
		rightMotor.close()
		rotorMotor.close()
		touchSensor.close()
		system.shutdown()
  }
  
  private def closeListener() {
    if(activateBtn) {
      new LegoExitButton(shutdown)
    }
  }
  
	//Starter
  val motorController = new MotorMovementController(leftMotor, rightMotor, rotorMotor)
  val system: ActorSystem = ActorSystem("legoRoller")
  val props = ThreadedRoverRoller.props(activateBtn, motorController, touchSensor)
  val controller: ActorRef = system.actorOf(props, "threadeRoverRoller")
  controller ! ThreadedRoverRoller.Go
  closeListener()
}
