package com.walcron.lego.test.server

import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.grizzly.websockets.WebSocketAddOn
import org.glassfish.grizzly.http.server.NetworkListener
import java.util.Collection
import scala.collection.JavaConversions._
import org.glassfish.grizzly.websockets.WebSocketEngine
import com.walcron.lego.roller.util.Const
import org.glassfish.grizzly.websockets.WebSocket
import org.glassfish.grizzly.websockets.WebSocketApplication
import com.walcron.lego.roller.connector.WebsocketClient
import com.neovisionaries.ws.client.WebSocketAdapter

class GrizzlyWebsocketServer {
  private val server:HttpServer = HttpServer.createSimpleServer(null)
  
  def init() {
    val addon = new WebSocketAddOn()
    server.getListeners.foreach(_.registerAddOn(addon))
    WebSocketEngine.getEngine.register("", Const.API_PATH, new WebSocketApplication {
      override def onConnect(socket:WebSocket) {
    	  super.onConnect(socket)
    	}
    	
    	override def onMessage(socket:WebSocket, data:String) {
        val client = new WebsocketClient(Option(new WebSocketAdapter()), Const.CONNECTION_URI_RECEIVE);
        client.sendMessage(data)
        client.disconnect()
    	}
      
    })
    WebSocketEngine.getEngine.register("", Const.REGISTER_API_PATH, new WebSocketApplication {

    	override def onConnect(socket:WebSocket) {
    		super.onConnect(socket)
    	}
    	
    	override def onMessage(socket:WebSocket, data:String) {
    		socket.broadcast(getWebSockets(), data);
    	}
    })
  }
	
  def start() {
    server.start()
  }
  
  def isStarted() = {
    server.isStarted()
  }
  
  def shutdown() {
    server.shutdown()
  }
	
	//Starter
	init()
}