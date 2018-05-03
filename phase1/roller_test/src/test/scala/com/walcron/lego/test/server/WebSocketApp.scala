package com.walcron.lego.test.server

import org.glassfish.grizzly.websockets.WebSocket
import org.glassfish.grizzly.websockets.WebSocketApplication

class WebSocketApp extends WebSocketApplication {

	override def onConnect(socket:WebSocket) {
		super.onConnect(socket);
	}
	
	override def onMessage(socket:WebSocket, data:String) {
		socket.broadcast(getWebSockets(), data);
	}
  
}