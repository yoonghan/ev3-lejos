package com.walcron.lagom.lego.impl

import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.lightbend.lagom.scaladsl.testkit.PersistentEntityTestDriver
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}
import com.walcron.lagom.lego.impl._

class RollerEntitySpec extends WordSpec with Matchers with BeforeAndAfterAll {
  private val system = ActorSystem("RollerEntitySpec",
    JsonSerializerRegistry.actorSystemSetupFor(RollerSerializerRegistry))

  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }
  
  private def withTestDriver(block: PersistentEntityTestDriver[RollerCommand[_], RollerTimelineEvent, RollerState] => Unit): Unit = {
    val driver = new PersistentEntityTestDriver(system, new RollerEntity, "roller")
    block(driver)
    driver.getAllIssues should have size 0
  }

  
  "roller entity" should {
    "say W by default" in withTestDriver { driver =>
      val outcome = driver.run(Roller("W"))
      outcome.replies should contain only "W"
    }
  }
}