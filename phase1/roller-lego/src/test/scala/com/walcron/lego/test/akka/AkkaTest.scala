package com.walcron.lego.test.akka

import akka.actor._

class TestWithoutAkka extends Thread {
  override def run() {
    println("FINISH")
    Thread.sleep(10000)
  }
}

class TestWithAkka {
  init()
  
  def init() {
    val system = ActorSystem("system")
    
    println("FINISH")
    system.shutdown()
    Thread.sleep(10000)
  }
}