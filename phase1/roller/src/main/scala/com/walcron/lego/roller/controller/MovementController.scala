package com.walcron.lego.roller.controller

import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocket
import com.walcron.lego.roller.util.Const.Directions
import com.walcron.lego.roller.util.Const
import akka.actor.ActorRef
import com.walcron.lego.roller.controller.ThreadedMotorController._

class MovementController(actor: ActorRef) extends WebSocketAdapter {
  override def onTextMessage(ws: WebSocket, message:String) {
    	val symbol = message.charAt(0);
    	val assumedDirection = charToDirection(symbol);
    	actor ! MoveAction(assumedDirection)
  }
  
  def charToDirection(symbol:Char):Directions = {
    symbol match {
      case 'A' => Const.LEFT
      case 'D' => Const.RIGHT
      case 'S' => Const.BACKWARD
      case 'W' => Const.FORWARD
      case _ => Const.STOP
    }
  }
}