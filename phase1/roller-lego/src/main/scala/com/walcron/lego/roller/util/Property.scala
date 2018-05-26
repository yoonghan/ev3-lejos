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
  
  def getId():String = {
    _property.getProperty("id")
  }
  
  def getPingInterval:Int = {
    val default = 60000
    val interval = _property.getProperty("socket.ping.interval", default.toString())
    if(interval == null || interval.matches("^[0-9]+$")) {
      default
    }
    else {
      Integer.parseInt(interval, 10)
    }
  }
  
  //Starter
  load()
}