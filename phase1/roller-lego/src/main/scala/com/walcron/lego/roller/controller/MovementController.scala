package com.walcron.lego.roller.controller

import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocket
import com.walcron.lego.roller.util.Const.Directions
import com.walcron.lego.roller.util.Const
import akka.actor.ActorRef
import com.walcron.lego.roller.controller.ThreadedMotorController._
import com.google.gson.Gson
import com.neovisionaries.ws.client.WebSocketFrame
import com.neovisionaries.ws.client.WebSocketException

case class RollerTopicDirection(message:String, direction:Int)

class MovementController(threadedMotorControllerActor: ActorRef) extends WebSocketAdapter {
  val gson = new Gson
  
  override def onTextMessage(ws: WebSocket, json:String) {
    val response = gson.fromJson(json, classOf[RollerTopicDirection])
  	val symbol = response.message.charAt(0);
  	val assumedDirection = charToDirection(symbol);
  	threadedMotorControllerActor ! MoveAction(assumedDirection)
  }
  
  override def onError(websocket:WebSocket, error:WebSocketException) {
    websocket.disconnect()
    threadedMotorControllerActor ! Reconnect
    super.onError(websocket, error)
  }
  
  def charToDirection(symbol:Char):Directions = {
    symbol match {
      case 'A' => Const.LEFT
      case 'D' => Const.RIGHT
      case 'S' => Const.BACKWARD
      case 'W' => Const.FORWARD
      case '@' => Const.NOTHING
      case _ => Const.STOP
    }
  }
}