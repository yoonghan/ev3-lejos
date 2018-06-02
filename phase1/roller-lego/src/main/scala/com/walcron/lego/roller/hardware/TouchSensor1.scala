package com.walcron.lego.roller.hardware

import lejos.hardware.sensor.EV3TouchSensor
import lejos.hardware.port.SensorPort
import com.walcron.lego.roller.impl.TouchSensorImpl

class TouchSensor1 extends TouchSensorImpl {
  
  val ev3TouchSensor = new EV3TouchSensor(SensorPort.S1)
  val touch = ev3TouchSensor.getTouchMode
  val sample = Array(touch.sampleSize().toFloat)
  
  override def waitForTouch() {
    var loop = true;
      while(loop) {
        touch.fetchSample(sample, 0)
        if(sample(0) == 1) {
          loop = false
        }
        Thread.sleep(500)
      }
  }
  
  override def close() {
    ev3TouchSensor.close()
  }
}
