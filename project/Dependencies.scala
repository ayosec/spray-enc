import sbt._

object Dependencies {
  val resolutionRepos = Seq(
    ScalaToolsSnapshots,
    "Typesafe repo" at "http://repo.typesafe.com/typesafe/releases/",
    "spray repo" at "http://repo.spray.cc/"
  )

  def compile   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
  def provided  (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
  def test      (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")
  def runtime   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
  def container (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

  object V {
    val akka     = "2.0.3"
    val spray    = "1.0-M2"
  }

  // Logging
  val slf4j       = "org.slf4j"         %  "slf4j-api"       % "1.6.4"
  val logback     = "ch.qos.logback"    %  "logback-classic" % "1.0.0"

  // Akka
  val akkaActor   = "com.typesafe.akka" %  "akka-actor"      % V.akka
  val akkaRemote  = "com.typesafe.akka" %  "akka-remote"     % V.akka
  val akkaSlf4j   = "com.typesafe.akka" %  "akka-slf4j"      % V.akka
  val akkaTestKit = "com.typesafe.akka" %  "akka-testkit"    % V.akka

  // Spray
  val sprayServer = "cc.spray"          %  "spray-server"    % V.spray
  val sprayCan    = "cc.spray"          %  "spray-can"       % V.spray
  val sprayJson   = "cc.spray"          %% "spray-json"      % "1.1.1"

  // Tests
  val scalatest   = "org.scalatest"     %% "scalatest"       % "2.0.M3"

  // General
  val subset      = "com.osinka.subset" %  "subset_2.9.1"    % "1.0.0"
  val pegdown     = "org.pegdown"       %  "pegdown"         % "1.1.0"
  val jodaTime    = "joda-time"         %  "joda-time"       % "2.1"
  val jodaConvert = "org.joda"          %  "joda-convert"    % "1.2"

  // Scala IO
  val ioFile = "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.1-seq"
  val ioCore = "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.1-seq"

  // Dependency sets
  val akka = Seq(akkaActor, akkaRemote, akkaSlf4j)
  val spray = Seq(sprayServer, sprayCan /*, sprayJson */)
}
