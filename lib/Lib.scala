package com.github.un_jon.ahoy


object Ahoy {

  def apply() : Unit = {
    println("Ahoy")
  }
}

object MyApp extends App {
  
  Ahoy()
}