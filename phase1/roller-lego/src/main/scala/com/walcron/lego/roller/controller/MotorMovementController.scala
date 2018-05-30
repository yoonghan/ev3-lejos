package com.walcron.lego.roller.controller

import java.util.concurrent.atomic.AtomicBoolean
import com.walcron.lego.roller.impl.MotorImpl
import com.walcron.lego.roller.util.Const.Directions
import com.walcron.lego.roller.util.Const

class MotorMovementController(leftMotor: MotorImpl, rightMotor:MotorImpl, rotorMotor:MotorImpl) {
  val canUpdate = new AtomicBoolean(true);
  val fullRotation = 760
  val halfRotation = fullRotation / 2
  val quarterRotation = halfRotation / 2
  val fullSpeed = 400
  val halfSpeed = fullSpeed / 2
  
  def init() {
   if(leftMotor != null && rightMotor !=null && rotorMotor != null) {
     val _rightMotor = rightMotor.getMotor()
     val _rotorMotor = rotorMotor.getMotor()
     if(_rightMotor.isDefined && _rotorMotor.isDefined) {
       leftMotor.synchronizeWith(Array(_rightMotor.get, _rotorMotor.get))
     }
   }
  }
  
	/**
	 * Atomic method to stop new request going in.
	 */
	def move(direction:Directions) {
//		val status = canUpdate.getAndSet(false);
//		if(status) {
			moveInDirection(direction)
//			canUpdate.getAndSet(true)
//		}
	}
	
	def moveInDirection(direction: Directions) {
	  direction match {
	    case Const.BACKWARD => { moveBackward }
	    case Const.FORWARD => { moveForward }
	    case Const.LEFT => { moveLeft() }
	    case Const.RIGHT => { moveRight() }
	    case Const.STOP => { haltMovement() }
	    case _ => return
	  }
	}
	
	def haltMovement() {
	  leftMotor.rotate(0);
		rightMotor.rotate(0);
		leftMotor.waitCompletion();
		rightMotor.waitCompletion();
	}
	
	def moveBackward() {
	  leftMotor.startSynchronize()
	  leftMotor.setSpeed(fullSpeed)
		rightMotor.setSpeed(fullSpeed)
		leftMotor.rotate(-halfRotation)
		rightMotor.rotate(-halfRotation)
		leftMotor.endSynchronize()
	}
	
	def moveForward() {
	  leftMotor.startSynchronize()
		leftMotor.setSpeed(fullSpeed)
		rightMotor.setSpeed(fullSpeed)
		leftMotor.rotate(fullRotation)
		rightMotor.rotate(fullRotation)
		leftMotor.endSynchronize()
	}
	
	def moveLeft() {
	  leftMotor.startSynchronize()
	  rotorMotor.rotate(-45)
	  leftMotor.setSpeed(halfSpeed)
		rightMotor.setSpeed(fullSpeed)
		leftMotor.rotate(quarterRotation)
		rightMotor.rotate(halfRotation)
		leftMotor.endSynchronize()
		leftMotor.waitCompletion()
		rotorMotor.rotate(45)
	}
	
	def moveRight() {
	  leftMotor.startSynchronize()
	  rotorMotor.rotate(45)
	  leftMotor.setSpeed(fullSpeed)
		rightMotor.setSpeed(halfSpeed)
		leftMotor.rotate(halfRotation)
		rightMotor.rotate(quarterRotation)
		leftMotor.endSynchronize()
		leftMotor.waitCompletion()
		rotorMotor.rotate(-45)
	}
	
	//starter
	init()
}