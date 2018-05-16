package com.walcron.lego.test

import com.walcron.lego.test.server.GrizzlyWebsocketServer
import com.neovisionaries.ws.client.WebSocketAdapter
import com.walcron.lego.roller.connector.WebsocketClient
import org.scalacheck._
import com.walcron.lego.roller.util.Const

/**
 * This creates a real server, it is used for temporary testing.
 */
object TemporaryServer {
  def sendRandomSeeds(client:WebsocketClient) {
    val randomMovement = Gen.oneOf('W', 'A', 'S', 'D').sample.get
    println(s"->${randomMovement}")
    client.sendMessage(randomMovement.toString())	
	}
  
  def initServer() {
    val server:GrizzlyWebsocketServer = new GrizzlyWebsocketServer
		server.start()
  }
	
	def main(args:Array[String]) {
		println("TO STOP SERVER: Use Ctrl-C");
		println("Path responses are W=Forward, A=Left, S=Backwards, D=Right");
		initServer()
		val client:WebsocketClient = new WebsocketClient(Option(new WebSocketAdapter), Const.CONNECTION_URI_SEND)
		
		while(true) {
			TemporaryServer.sendRandomSeeds(client);
			Thread.sleep(5000);
		}
	}
}