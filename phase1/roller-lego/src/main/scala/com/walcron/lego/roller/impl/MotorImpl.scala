package com.walcron.lego.roller.impl

import lejos.robotics.RegulatedMotor
import lejos.hardware.port.MotorPort
import lejos.hardware.motor.EV3LargeRegulatedMotor

trait MotorImpl {this:LegoMotorImpl =>

  def rotate(angle: Int) {
		legoRotate(angle)
	}
	
	def getMotor():Option[RegulatedMotor] = {
	  Option(legoGetMotor())
	}
	
	def synchronizeWith(motors: Array[RegulatedMotor]) {
	  legoSynchronizeWith(motors)
	}
	
	def startSynchronize() {
	  legoStartSynchronize()
	}
	
	def endSynchronize() {
	  legoEndSynchronize()
	}
	
	def waitCompletion() {
	  legoWaitCompletion()
	}
	
	def setSpeed(speed:Int) {
	  legoSetSpeed(speed)
	}
	
	def close() {
	  legoClose()
	}
}