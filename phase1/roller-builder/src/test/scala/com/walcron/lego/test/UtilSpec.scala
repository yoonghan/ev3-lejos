package com.walcron.lego.test

import org.scalatest.FlatSpec
import org.scalatest._
import com.walcron.lego.roller.util.Property

class UtilSpec extends FlatSpec with Matchers {

  "A config" should "have connection.uri" in {
    val property = Property
    property.getServer() shouldEqual ("localhost:8080")
  }
  
  "A config" should "have id" in {
    val property = Property
    property.getId() shouldEqual ("test")
  }
}