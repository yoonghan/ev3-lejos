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
  override def legoRotate(angle: Int) {
    MockResult.motorResultA = angle
  }
  
  override def legoSetSpeed(speed: Int) {
    MockResult.motorSpeedA = speed
  }
}

class MockLargeMotorB extends MotorImpl with MockLegoMotor {
  override def legoRotate(angle: Int) {
    MockResult.motorResultB = angle
  }
  
  override def legoSetSpeed(speed: Int) {
    MockResult.motorSpeedB = speed
  }
}

class MockMediumMotorC extends MotorImpl with MockLegoMotor {
  override def legoRotate(angle: Int) {
    MockResult.motorResultC = angle
  }
  
  override def legoSetSpeed(speed: Int) {
    MockResult.motorSpeedC = speed
  }
}

object MockResult {
  var motorResultA = 0
  var motorResultB = 0
  var motorResultC = 0
  var motorSpeedA = 0
  var motorSpeedB = 0
  var motorSpeedC = 0
}