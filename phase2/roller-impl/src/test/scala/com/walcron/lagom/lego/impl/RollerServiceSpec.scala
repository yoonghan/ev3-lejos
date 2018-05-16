package com.walcron.lagom.lego.impl

import com.lightbend.lagom.scaladsl.server.LocalServiceLocator
import com.lightbend.lagom.scaladsl.testkit.ServiceTest
import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers}
import com.walcron.lagom.lego.api.RollerService

class RollerServiceSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll {

  private val server = ServiceTest.startServer(
    ServiceTest.defaultSetup
      .withCassandra()
  ) { ctx =>
    new RollerApplication(ctx) with LocalServiceLocator
  }

  val client = server.serviceClient.implement[RollerService]

  override protected def afterAll() = server.stop()

  "roller service" should {

    "say W" in {
      client.moveCommand("1").invoke("W").map { answer =>
        answer should ===("W")
      }
    }
  }
}