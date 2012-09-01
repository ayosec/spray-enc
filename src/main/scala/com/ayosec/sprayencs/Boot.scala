package com.ayosec.sprayencs

import akka.actor.{Props, ActorSystem}
import cc.spray.can.server.HttpServer
import cc.spray.io.IOBridge
import cc.spray.io.pipelining.MessageHandlerDispatch

object Boot extends App {
  // we need an ActorSystem to host our application in

  val system = ActorSystem("demo")

  val ioBridge = new IOBridge(system).start()

  val service = system.actorOf(Props[PagesServiceActor], "demo-service")

  val sprayCanServer = system.actorOf(
    Props(new HttpServer(ioBridge, MessageHandlerDispatch.SingletonHandler(service))),
    name = "http-server"
  )

  sprayCanServer ! HttpServer.Bind("localhost", 8080)

  system.registerOnTermination {
    ioBridge.stop()
  }
}
