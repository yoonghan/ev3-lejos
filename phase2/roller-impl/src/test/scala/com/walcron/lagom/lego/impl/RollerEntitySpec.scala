package com.walcron.lagom.lego.impl

import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.lightbend.lagom.scaladsl.testkit.PersistentEntityTestDriver
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}
import com.walcron.lagom.lego.impl._
import com.lightbend.lagom.scaladsl.pubsub.PubSubRegistry
import com.lightbend.lagom.scaladsl.pubsub.TopicId
import com.lightbend.lagom.scaladsl.pubsub.PubSubRef

class RollerEntitySpec extends WordSpec with Matchers  with BeforeAndAfterAll {
  private val system = ActorSystem("RollerEntitySpec",
    JsonSerializerRegistry.actorSystemSetupFor(RollerSerializerRegistry))

  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }
  
  private def withTestDriver(block: PersistentEntityTestDriver[RollerCommand[_], RollerTimelineEvent, RollerState] => Unit): Unit = {
    val driver = new PersistentEntityTestDriver(system, new RollerEntity(null), "roller")
    block(driver)
    driver.getAllIssues should have size 0
  }

  
  "roller entity" should {
    "say W by default" in withTestDriver { driver =>
      val outcome = driver.run(Roller("1","W"))
      outcome.replies should contain only "W"
    }
  }
}