package com.walcron.lego.roller.hardware

import lejos.hardware.port.MotorPort
import lejos.robotics.RegulatedMotor
import com.walcron.lego.roller.impl.MotorImpl
import lejos.hardware.motor.EV3MediumRegulatedMotor

class LegoMediumMotorC extends MotorImpl with LegoMotor {
  val motor = new EV3MediumRegulatedMotor(MotorPort.C)
}