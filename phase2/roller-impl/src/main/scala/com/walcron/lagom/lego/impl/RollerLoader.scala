package com.walcron.lagom.lego.impl

import com.lightbend.lagom.scaladsl.server.LagomApplicationLoader
import com.lightbend.lagom.scaladsl.server.LagomApplicationContext
import com.lightbend.lagom.scaladsl.server.LagomApplication
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import com.walcron.lagom.lego.api.RollerService
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import kamon.Kamon
import kamon.zipkin._
import kamon.prometheus._
import scala.util.Random
import com.lightbend.lagom.scaladsl.pubsub.PubSubComponents

class RollerLoader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext): LagomApplication =
    new RollerApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new RollerApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[RollerService])
  
  Kamon.addReporter(new PrometheusReporter())
}

abstract class RollerApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with LagomKafkaComponents
    with PubSubComponents
    with AhcWSComponents {
  
  override lazy val lagomServer = serverFor[RollerService](wire[RollerImpl])
  
  override lazy val jsonSerializerRegistry = RollerSerializerRegistry

  persistentEntityRegistry.register(wire[RollerEntity])
}
