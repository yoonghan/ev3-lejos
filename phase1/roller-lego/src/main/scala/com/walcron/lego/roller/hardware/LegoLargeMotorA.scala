package com.walcron.lego.roller.hardware

import lejos.hardware.motor.EV3LargeRegulatedMotor
import lejos.hardware.port.MotorPort
import lejos.robotics.RegulatedMotor
import com.walcron.lego.roller.impl.MotorImpl
import com.walcron.lego.roller.impl.LegoMotorImpl

class LegoLargeMotorA extends MotorImpl with LegoMotor {
  val motor = new EV3LargeRegulatedMotor(MotorPort.A)
  motor.setSpeed(800)
}