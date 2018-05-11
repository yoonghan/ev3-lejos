package com.walcron.lego.roller.impl

import lejos.robotics.RegulatedMotor

trait LegoMotorImpl {
  def legoRotate(angle: Int)
	def legoSynchronizeWith(motors: Array[RegulatedMotor])
	def legoStartSynchronize()
	def legoEndSynchronize()
	def legoWaitCompletion()
	def legoClose()
  def legoGetMotor():RegulatedMotor
}