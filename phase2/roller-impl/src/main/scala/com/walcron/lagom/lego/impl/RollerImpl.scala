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

class RollerImpl(
    rollerService: RollerService,
    persistentEntityRegistry: PersistentEntityRegistry
    ) extends RollerService {
  
  def stream(): ServiceCall[Source[String, NotUsed], Source[String, NotUsed]] = { 
    source =>
    Future.successful(source.mapAsync(3)(rollerService.moveCommand().invoke(_)))
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