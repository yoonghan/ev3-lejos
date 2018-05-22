package com.walcron.lego.roller.controller

import java.util.concurrent.atomic.AtomicBoolean
import com.walcron.lego.roller.impl.MotorImpl
import com.walcron.lego.roller.util.Const.Directions
import com.walcron.lego.roller.util.Const

class MotorMovementController(leftMotor: MotorImpl, rightMotor:MotorImpl) {
  val canUpdate = new AtomicBoolean(true);
  val fullRotation = 720
  val halfRotation = 360
  
  def init() {
   if(leftMotor != null && rightMotor !=null) {
     val motor = rightMotor.getMotor()
     if(motor.isDefined) {
       leftMotor.synchronizeWith(Array(motor.get))
     }
   }
  }
  
	/**
	 * Atomic method to stop new request going in.
	 */
	def move(direction:Directions) {
		val status = canUpdate.getAndSet(false);
		if(status) {
			moveInDirection(direction)
			canUpdate.getAndSet(true)
		}
	}
	
	def moveInDirection(direction: Directions) {
	  direction match {
	    case Const.BACKWARD => {moveBackward}
	    case Const.FORWARD => {moveForward}
	    case Const.LEFT => {moveLeft()}
	    case Const.RIGHT => {moveRight()}
	    case _ => {haltMovement()}
	  }
	}
	
	def haltMovement() {
	  leftMotor.rotate(0);
		rightMotor.rotate(0);
	}
	
	def moveBackward() {
	  leftMotor.startSynchronize()
		leftMotor.rotate(-halfRotation);
		rightMotor.rotate(-halfRotation);
		leftMotor.endSynchronize()
	}
	
	def moveForward() {
	  leftMotor.startSynchronize()
		leftMotor.rotate(fullRotation);
		rightMotor.rotate(fullRotation);
		leftMotor.endSynchronize()
	}
	
	def moveLeft() {
	  leftMotor.startSynchronize()
		leftMotor.rotate(-fullRotation);
		rightMotor.rotate(fullRotation);
		leftMotor.endSynchronize()
	}
	
	def moveRight() {
	  leftMotor.startSynchronize()
		leftMotor.rotate(fullRotation);
		rightMotor.rotate(-fullRotation);
		leftMotor.endSynchronize()
	}
	
	//starter
	init()
}