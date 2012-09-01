package com.ayosec.sprayencs

import cc.spray._
import cc.spray.typeconversion._
import akka.actor.{ActorSystem, Actor, Props}

import scalax.io._

trait PagesService extends Directives {

  implicit val actorSystem: ActorSystem

  def resource = Resource.fromFile("/tmp/spray.test")

  val routes = {

    implicit val codec = Codec.UTF8

    get {
      _.complete(resource.string)
    } ~
    post {
      formFields('content) { (content) =>

        resource.write("With UTF-8 = ")
        resource.write(content)
        resource.write("\n")

        resource.write("With ISO-8859 = ")
        resource.write(content)(Codec.ISO8859)
        resource.write("\n")

        _.complete("Ok")
      }
    }
  }

}
