package com.ayosec.sprayencs

import cc.spray.io.pipelines.MessageHandlerDispatch
import cc.spray.io.IoWorker
import cc.spray.can.server.HttpServer
import cc.spray._
import akka.actor._

object Boot extends App {
  // we need an ActorSystem to host our application in
  val system = ActorSystem()

  val service = system.actorOf(
    props = Props(new HttpService(new PagesService {
      val actorSystem = system
    }.routes)),
    name = "service"
  )

  val rootService = system.actorOf(
    props = Props(new SprayCanRootService(service)),
    name = "spray-root-service"
  )

  val ioWorker = new IoWorker(system).start()
  val server = system.actorOf(
    props = Props(new HttpServer(ioWorker, MessageHandlerDispatch.SingletonHandler(rootService))),
    name = "http-server"
  )

  server ! HttpServer.Bind("localhost", 8080)

  system.registerOnTermination {
    ioWorker.stop()
  }
}
