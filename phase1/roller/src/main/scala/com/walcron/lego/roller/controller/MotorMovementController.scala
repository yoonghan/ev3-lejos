package com.walcron.lego.roller.controller

import java.util.concurrent.atomic.AtomicBoolean
import com.walcron.lego.roller.impl.MotorImpl
import com.walcron.lego.roller.util.Const.Directions
import com.walcron.lego.roller.util.Const

class MotorMovementController(leftMotor: MotorImpl, rightMotor:MotorImpl) {
  val canUpdate = new AtomicBoolean(true);
  
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
		leftMotor.rotate(-360);
		rightMotor.rotate(-360);
		leftMotor.endSynchronize()
		leftMotor.waitCompletion()
		rightMotor.waitCompletion()
	}
	
	def moveForward() {
	  leftMotor.startSynchronize()
		leftMotor.rotate(360);
		rightMotor.rotate(360);
		leftMotor.endSynchronize()
		leftMotor.waitCompletion()
		rightMotor.waitCompletion()
	}
	
	def moveLeft() {
	  leftMotor.startSynchronize()
		leftMotor.rotate(360);
		rightMotor.rotate(-360);
		leftMotor.endSynchronize()
		leftMotor.waitCompletion()
		rightMotor.waitCompletion()
	}
	
	def moveRight() {
	  leftMotor.startSynchronize()
		leftMotor.rotate(-360);
		rightMotor.rotate(360);
		leftMotor.endSynchronize()
		leftMotor.waitCompletion()
		rightMotor.waitCompletion()
	}
	
	//starter
	init()
}