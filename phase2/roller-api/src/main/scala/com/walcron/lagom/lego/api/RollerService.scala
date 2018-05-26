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
  
  def streamIn(id:String): ServiceCall[Source[String, NotUsed], Source[String, NotUsed]]
  def streamOut(id:String): ServiceCall[NotUsed, Source[RollerTopicDirection, NotUsed]]
  def moveCommand(id:String): ServiceCall[String, String]
  def rollerMoveTopic() : Topic [RollerMovementChanged]
  def showOk(): ServiceCall[NotUsed, String]
  
  override final def descriptor = {
    import Service._
    named("roller-lagom")
      .withCalls(
        namedCall("/", showOk),
        pathCall("/api/roller/move/:id", moveCommand _),
        pathCall("/stream/:id/in", streamIn _),
        pathCall("/stream/:id/register", streamOut _)
      )
      .withTopics(
        topic(RollerService.topicName, rollerMoveTopic)
      )
      .withAutoAcl(true)
  }
}