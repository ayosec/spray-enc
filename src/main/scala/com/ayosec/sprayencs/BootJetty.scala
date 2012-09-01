package com.ayosec.sprayencs

import akka.actor.{Props, ActorSystem}
import cc.spray.servlet.WebBoot

class BootJetty extends WebBoot {

  // we need an ActorSystem to host our application in
  val system = ActorSystem()

  // the service actor replies to incoming HttpRequests
  val serviceActor = system.actorOf(Props[PagesServiceActor])

  system.registerOnTermination {
    // put additional cleanup code here
    system.log.info("Application shut down")
  }
}
