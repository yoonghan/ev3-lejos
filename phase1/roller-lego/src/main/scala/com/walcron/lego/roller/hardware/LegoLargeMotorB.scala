package com.walcron.lego.roller.hardware

import lejos.hardware.port.MotorPort
import lejos.hardware.motor.EV3LargeRegulatedMotor
import com.walcron.lego.roller.impl.MotorImpl
import lejos.robotics.RegulatedMotor

class LegoLargeMotorB extends MotorImpl {
  val motor = new EV3LargeRegulatedMotor(MotorPort.B);
	
	def rotate(angle: Int) {
		motor.rotate(angle)
	}
	
	def getMotor():Option[RegulatedMotor] = {
	  Option(motor)
	}
	
	def synchronizeWith(motors: Array[RegulatedMotor]) {
	  motor.synchronizeWith(motors)
	}
	
	def startSynchronize() {
	  motor.startSynchronization()
	}
	
	def endSynchronize() {
	  motor.endSynchronization()
	}
	
	def waitCompletion() {
	  motor.waitComplete()
	}
	
	def close() {
	  motor.close()
	}
}