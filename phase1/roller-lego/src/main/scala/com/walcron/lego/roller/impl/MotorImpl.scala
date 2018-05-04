package com.walcron.lego.roller.impl

import lejos.robotics.RegulatedMotor

trait MotorImpl {
  def rotate(angle:Int)
  def startSynchronize()
	def endSynchronize()
	def waitCompletion()
  def getMotor():Option[RegulatedMotor]
	def synchronizeWith(motors: Array[RegulatedMotor])
  def close() 
}