package com.walcron.lego.roller.controller

import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocket
import com.walcron.lego.roller.util.Const.Directions
import com.walcron.lego.roller.util.Const
import akka.actor.ActorRef
import com.walcron.lego.roller.controller.ThreadedMotorController._
import com.google.gson.Gson

case class RollerTopicDirection(message:String, direction:Int)

class MovementController(actor: ActorRef) extends WebSocketAdapter {
  val gson = new Gson
  
  override def onTextMessage(ws: WebSocket, json:String) {
    val response = gson.fromJson(json, classOf[RollerTopicDirection])
  	val symbol = response.message.charAt(0);
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