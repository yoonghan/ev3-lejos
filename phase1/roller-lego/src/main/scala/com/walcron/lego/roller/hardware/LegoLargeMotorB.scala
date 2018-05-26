package com.walcron.lego.roller.hardware

import lejos.hardware.motor.EV3LargeRegulatedMotor
import lejos.hardware.port.MotorPort
import lejos.robotics.RegulatedMotor
import com.walcron.lego.roller.impl.MotorImpl

class LegoLargeMotorB extends MotorImpl with LegoMotor {
  val motor = new EV3LargeRegulatedMotor(MotorPort.D)
  motor.setSpeed(800)
}