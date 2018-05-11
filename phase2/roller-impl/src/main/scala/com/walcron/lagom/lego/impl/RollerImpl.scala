package com.walcron.lagom.lego.impl

import com.walcron.lagom.lego.api.RollerService
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraSession
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.walcron.lagom.lego.api.RollerMovementChanged
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import akka.stream.scaladsl.Source
import akka.NotUsed
import scala.concurrent.Future
import kamon.Kamon
import com.lightbend.lagom.scaladsl.pubsub.PubSubRegistry
import com.lightbend.lagom.scaladsl.pubsub.TopicId
import akka.stream.Materializer

class RollerImpl(
    rollerService: RollerService,
    pubSub: PubSubRegistry,
    persistentEntityRegistry: PersistentEntityRegistry
    ) extends RollerService {
  
  val rollerTopic = pubSub.refFor(TopicId[String])
  
  def streamIn(): ServiceCall[Source[String, NotUsed], Source[String, NotUsed]] = { 
    source =>
      Future.successful(source.mapAsync(3)(rollerService.moveCommand().invoke(_)))
  }
  
  def streamOut(): ServiceCall[NotUsed, Source[String, NotUsed]] = {
    source =>
      Future.successful(rollerTopic.subscriber)
  }
  
  def moveCommand(): ServiceCall[String, String] = ServiceCall { move =>
    val ref = persistentEntityRegistry.refFor[RollerEntity](move)
    ref.ask(Roller(move))
  }
  
  private def convertEvent(rollerTimelineEvent: EventStreamElement[RollerTimelineEvent]): RollerMovementChanged = {
    rollerTimelineEvent.event match {
      case RollerMovementAdded(movement) => new RollerMovementChanged(rollerTimelineEvent.entityId, movement)
    }
  }
  
  def rollerMoveTopic() : Topic[RollerMovementChanged] = 
    TopicProducer.taggedStreamWithOffset(RollerTimelineEvent.Tag.allTags.toList) {
      (tag, offset) =>
        persistentEntityRegistry.eventStream(tag, offset)
          .map(ev => (convertEvent(ev), ev.offset))
    }
}