package com.walcron.lego.test

import org.scalatest.FlatSpec
import org.scalatest._
import com.walcron.lego.test.server.GrizzlyWebsocketServer
import com.walcron.lego.roller.connector.WebsocketClient
import com.walcron.lego.roller.ScalaRoverRoller
import com.walcron.lego.test.mock.MockLargeMotorA
import com.walcron.lego.test.mock.MockLargeMotorB
import com.walcron.lego.test.mock.MockResult
import com.neovisionaries.ws.client.WebSocketAdapter
import com.walcron.lego.test.mock.TouchSensor

class RoverRollerSpec extends FlatSpec with Matchers with BeforeAndAfterAll {
  
  override def afterAll(): Unit = {
    client.disconnect()
    server.shutdown()
  }
  
  def initServer:GrizzlyWebsocketServer = {
    val server = new GrizzlyWebsocketServer;
    server.start()
    server
  }
  
  def initClient:WebsocketClient = {
    new WebsocketClient(Option(new WebSocketAdapter()));
  }
  
  def initRoverRoller:ScalaRoverRoller = {
    val leftMotor = new MockLargeMotorA
    val rightMotor = new MockLargeMotorB
    val touchSensor = new TouchSensor
    new ScalaRoverRoller(leftMotor, rightMotor, touchSensor,false)
  }
  
  def delay() {
		Thread.sleep(500)
	}
  
  def delayVeryLong() {
		Thread.sleep(2000)
	}
  
  def rollerMovement(roverRoller:ScalaRoverRoller, client:WebsocketClient, server:GrizzlyWebsocketServer) {
    "For input of W it" should "move forward" in {
      client.sendMessage("W")
      delay
      MockResult.motorResultA should be (360)
      MockResult.motorResultB should be (360)
    }
    "For input of S it" should "move backward" in {
      client.sendMessage("S")
      delay
      MockResult.motorResultA should be (-360)
      MockResult.motorResultB should be (-360)
    }
    "For input of A it" should "move left" in {
      client.sendMessage("A")
      delay
      MockResult.motorResultA should be (360)
      MockResult.motorResultB should be (-360)
    }
    "For input of D it" should "move right" in {
      client.sendMessage("D")
      delay
      MockResult.motorResultA should be (-360)
      MockResult.motorResultB should be (360)
    }
    "For input of X it" should "do nothing" in {
      client.sendMessage("X")
      delay
      MockResult.motorResultA should be (0)
      MockResult.motorResultB should be (0)
    }
    
    "After a delay and a restart, W sent" should "still move forward" in {
      TouchSensor.keepAwake = false
      delayVeryLong
      client.sendMessage("W")
      delay
      MockResult.motorResultA should be (360)
      MockResult.motorResultB should be (360)
    }
  }
  
  //Starter
  val server = initServer
  val client = initClient
  val rover = initRoverRoller
  it should behave like rollerMovement(rover, client, server)
}