package com.walcron.lego.test.mock

import com.walcron.lego.roller.impl.MotorImpl
import lejos.robotics.RegulatedMotor

class MockLargeMotorA extends MotorImpl {
  override def rotate(angle:Int) {
		MockResult.motorResultA = angle
	}
  
  def getMotor():Option[RegulatedMotor] = {Option.empty}
	
	def synchronizeWith(motors: Array[RegulatedMotor]) {}
	
	def startSynchronize() {
	}
	
	def endSynchronize() {
	}
	
	def waitCompletion() {
	}
	
  def close() {
    
  }
}

class MockLargeMotorB extends MotorImpl {
  override def rotate(angle:Int) {
		MockResult.motorResultB = angle
	}
  
  def getMotor():Option[RegulatedMotor] = {Option.empty}
	
	def synchronizeWith(motors: Array[RegulatedMotor]) {}
	
	def startSynchronize() {
	}
	
	def endSynchronize() {
	}
	
	def waitCompletion() {
	}
  
  def close() {
    
  }
}

object MockResult {
  var motorResultA = 0
  var motorResultB = 0
}