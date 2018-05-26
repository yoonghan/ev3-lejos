package com.walcron.lego.roller.connector

import com.neovisionaries.ws.client.WebSocketAdapter
import com.walcron.lego.roller.util.Const
import com.neovisionaries.ws.client.WebSocketFactory
import com.walcron.lego.roller.util.Property

class WebsocketClient(val adapterReceiver:Option[WebSocketAdapter], val uri:String) {
 
  private def init() = {
    val webSocket = new WebSocketFactory().createSocket(uri)
    if(adapterReceiver.isDefined) {
      webSocket.addListener(adapterReceiver.get)
    }
    webSocket.connect()
    webSocket.setPingInterval(Property.getPingInterval)
    webSocket
  }
	
	def sendMessage(message:String) {
    _webSocket.sendText(message);
	}
	
	def disconnect() {
		_webSocket.disconnect();
	}
	
	//Starter
  val _webSocket = init()
}