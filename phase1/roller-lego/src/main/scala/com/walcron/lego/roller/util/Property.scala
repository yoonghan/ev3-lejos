package com.walcron.lego.roller.util

import java.util.Properties

object Property {
  val _property = new Properties()
  val _configFile = "config.properties"
  
  def load() {
    val loader =Thread.currentThread().getContextClassLoader()
    val stream = loader.getResourceAsStream(_configFile);
    _property.load(stream);
  }
  
  def getServer():String = {
    _property.getProperty("connection.url")
  }
  
  //Starter
  load()
}