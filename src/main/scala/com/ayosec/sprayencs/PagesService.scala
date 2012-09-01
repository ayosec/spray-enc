package com.ayosec.sprayencs

import java.io.File
import org.parboiled.common.FileUtils
import akka.util.Duration
import akka.util.duration._
import akka.actor.{ActorLogging, Props, Actor}
import akka.pattern.ask
import cc.spray.routing.{HttpService, RequestContext}
import cc.spray.routing.directives.CachingDirectives
import cc.spray.can.server.HttpServer
import cc.spray.httpx.encoding.Gzip
import cc.spray.http._
import MediaTypes._
import CachingDirectives._
import cc.spray._
import routing.ApplyConverter._

import scalax.io._

class PagesServiceActor extends Actor with PagesService {

  def actorRefFactory = context

  def receive = runRoute(pagesRoute)
}

trait PagesService extends HttpService {

  def resource = Resource.fromFile("/tmp/spray.test")

  implicit val codec = Codec.UTF8

  val pagesRoute = {

    path("") {
      get {
        complete(resource.string)
      } ~
      post {
        formFields('content) { (content) =>

          resource.write("With UTF-8 = ")
          resource.write(content)
          resource.write("\n")

          resource.write("With ISO-8859 = ")
          resource.write(content)(Codec.ISO8859)
          resource.write("\n")

          complete("Ok")
        }
      }
    }
  }

}
