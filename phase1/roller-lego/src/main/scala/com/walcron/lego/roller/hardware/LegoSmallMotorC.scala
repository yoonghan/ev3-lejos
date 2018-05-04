package com.walcron.lego.roller.hardware

import lejos.hardware.port.MotorPort
import com.walcron.lego.roller.impl.MotorImpl
import lejos.hardware.motor.EV3MediumRegulatedMotor
import lejos.robotics.RegulatedMotor

class LegoSmallMotor1 extends MotorImpl {
  val motor = new EV3MediumRegulatedMotor(MotorPort.C);
	
	def rotate(angle: Int) {
		motor.rotate(angle)
	}
	
	def getMotor():Option[RegulatedMotor] = {
	  Option(motor)
	}
	
	def synchronizeWith(motors: Array[RegulatedMotor]) {
	}
	
	def startSynchronize() {
	}
	
	def endSynchronize() {
	}
	
	def waitCompletion() {
	}
	
	def close() {
	  motor.close()
	}
}