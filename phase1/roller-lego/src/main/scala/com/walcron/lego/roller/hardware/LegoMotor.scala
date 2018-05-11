package com.walcron.lego.roller.hardware

import com.walcron.lego.roller.impl.LegoMotorImpl
import lejos.hardware.motor.EV3LargeRegulatedMotor
import lejos.robotics.RegulatedMotor

trait LegoMotor extends LegoMotorImpl {
  val motor:RegulatedMotor
  
  def legoRotate(angle: Int) {
    motor.rotate(angle)
  }
  
	def legoSynchronizeWith(motors: Array[RegulatedMotor]) {
	  motor.synchronizeWith(motors)
	}
	
	def legoStartSynchronize() {
	  motor.startSynchronization()
	}
	
	def legoEndSynchronize() {
	  motor.endSynchronization()
	}
	
	def legoWaitCompletion() {
	  motor.waitComplete()
	}
	
	def legoClose() {
	  motor.close()
	}
	
  def legoGetMotor():RegulatedMotor = {
    motor
  }
}