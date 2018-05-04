package com.walcron.lagom.lego.api

import com.lightbend.lagom.scaladsl.api.Service
import com.lightbend.lagom.scaladsl.api.ServiceCall
import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.broker.Topic

import akka.stream.scaladsl.Source

object RollerService  {
  val topicName = "rollerTopic"
}

trait RollerService extends Service {
  
  def stream(): ServiceCall[Source[String, NotUsed], Source[String, NotUsed]]
  def moveCommand(): ServiceCall[String, String]
  def rollerMoveTopic() : Topic [RollerMovementChanged]
  
  override final def descriptor = {
    import Service._
    named("roller-lagom")
      .withCalls(
        pathCall("/api/roller/move", moveCommand _),
        namedCall("stream", stream)
      )
      .withTopics(
        topic(RollerService.topicName, rollerMoveTopic)
      )
      .withAutoAcl(true)
  }
}