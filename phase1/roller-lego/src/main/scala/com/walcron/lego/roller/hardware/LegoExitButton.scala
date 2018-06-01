package com.walcron.lego.roller.hardware

import lejos.hardware.Button
import lejos.hardware.KeyListener
import lejos.hardware.Key

class LegoExitButton(stopFunc:()=>Unit) extends Thread {
  val button = Button.ENTER
  
  private def init() {
	  button.addKeyListener(new KeyListener {
			override def keyReleased(k:Key) {
			  stopFunc()
			  System.exit(0)
			}
			
			override def keyPressed(k:Key) {
			}
	  });
    this.start()
  }
  
  override def run() {
    button.waitForPress()
    println("PRESS")
  }
  
  //Starter
  if(SingleEntry.isFirst()) {
    init()
    SingleEntry.update()
  }
}

private object SingleEntry {
  var first = true
  def update() { first = false }
  def isFirst() = { first }
}
