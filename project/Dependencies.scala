import sbt._

object Dependencies {
  val resolutionRepos = Seq(
    ScalaToolsSnapshots,
    "Typesafe repo" at "http://repo.typesafe.com/typesafe/releases/",
    "spray repo" at "http://repo.spray.cc/",
    "Local Ivy Repository" at "file://"+Path.userHome.absolutePath+"/.ivy2/local"
  )

  def compile   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
  def provided  (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
  def test      (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")
  def runtime   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
  def container (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

  object V {
    val akka     = "2.0.3"
    val spray    = "1.0-M3-SNAPSHOT"
  }

  // Logging
  val slf4j       = "org.slf4j"         %  "slf4j-api"       % "1.6.4"
  val logback     = "ch.qos.logback"    %  "logback-classic" % "1.0.0"

  // Akka
  val akkaActor   = "com.typesafe.akka" %  "akka-actor"      % V.akka
  val akkaRemote  = "com.typesafe.akka" %  "akka-remote"     % V.akka
  val akkaSlf4j   = "com.typesafe.akka" %  "akka-slf4j"      % V.akka
  val akkaTestKit = "com.typesafe.akka" %  "akka-testkit"    % V.akka

  // Scala IO
  val ioFile = "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.1-seq"
  val ioCore = "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.1-seq"

  // Dependency sets
  val akka = Seq(akkaActor, akkaRemote, akkaSlf4j)

  // Right now, this packages are available in the local repository
  val spray = Seq(
    "cc.spray" % "spray-caching" % V.spray,
    "cc.spray" % "spray-client" % V.spray,
    "cc.spray" % "spray-httpx" % V.spray,
    "cc.spray" % "spray-routing" % V.spray,
    "cc.spray" % "spray-servlet" % V.spray,
    "cc.spray" % "spray-util" % V.spray,
    "cc.spray" % "spray-can" % V.spray,
    "cc.spray" % "spray-http" % V.spray,
    "cc.spray" % "spray-io" % V.spray //,
    // "cc.spray" % "spray-routing-tests" % V.spray,
    // "cc.spray" % "spray-testkit" % V.spray
  )

}
