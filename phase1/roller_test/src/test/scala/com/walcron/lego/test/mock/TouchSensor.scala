package com.walcron.lego.test.mock

import com.walcron.lego.roller.impl.TouchSensorImpl
import akka.actor.Actor
import akka.actor.Props

class TouchSensor extends TouchSensorImpl {
  override def waitForTouch() {
    while(TouchSensor.keepAwake) {
      Thread.sleep(200)
    }
    TouchSensor.keepAwake = true
  }
  
  override def close() {
  }
}

object TouchSensor {
  var keepAwake = true
}