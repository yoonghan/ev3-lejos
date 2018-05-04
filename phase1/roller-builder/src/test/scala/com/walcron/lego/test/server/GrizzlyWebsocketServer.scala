package com.walcron.lego.test.server

import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.grizzly.websockets.WebSocketAddOn
import org.glassfish.grizzly.http.server.NetworkListener
import java.util.Collection
import scala.collection.JavaConversions._
import org.glassfish.grizzly.websockets.WebSocketEngine
import com.walcron.lego.roller.util.Const

class GrizzlyWebsocketServer {
  private val server:HttpServer = HttpServer.createSimpleServer(null)
  
  def init() {
    val addon = new WebSocketAddOn()
    server.getListeners.foreach(_.registerAddOn(addon))
    WebSocketEngine.getEngine.register("", Const.API_PATH, new WebSocketApp)
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