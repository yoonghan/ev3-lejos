package com.walcron.lego.test.mock

import com.walcron.lego.roller.impl.MotorImpl
import lejos.robotics.RegulatedMotor
import com.walcron.lego.roller.impl.LegoMotorImpl

trait MockLegoMotor extends LegoMotorImpl {
  
	def legoSynchronizeWith(motors: Array[RegulatedMotor]) {
	  
	}
	
	def legoStartSynchronize() {
	  
	}
	
	def legoEndSynchronize() {
	  
	}
	
	def legoWaitCompletion() {
	  
	}
	
	def legoClose() {
	  
	}
	
  def legoGetMotor():RegulatedMotor = {
    null
  }
}

class MockLargeMotorA extends MotorImpl with MockLegoMotor {
  def legoRotate(angle: Int) {
    MockResult.motorResultA = angle
  }
}

class MockLargeMotorB extends MotorImpl with MockLegoMotor {
  def legoRotate(angle: Int) {
    MockResult.motorResultB = angle
  }
}

object MockResult {
  var motorResultA = 0
  var motorResultB = 0
}