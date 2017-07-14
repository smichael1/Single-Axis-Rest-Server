import sbt._

// Dependencies

object Dependencies {

  val ScalaVersion = "2.11.8"
  
  val Version = "0.3-SNAPSHOT"


  // Common Services Dependencies
  val pkg = "org.tmt" % "pkg_2.11" % "0.3-SNAPSHOT" 
  val ccs = "org.tmt" % "ccs_2.11" % "0.3-SNAPSHOT" 
  val loc = "org.tmt" % "loc_2.11" % "0.3-SNAPSHOT" 
  val seqSupport = "org.tmt" % "seqsupport_2.11" % "0.3-SNAPSHOT" 
  val javacsw = "org.tmt" % "javacsw_2.11" % "0.3-SNAPSHOT" 
  
  val singleaxis = "org.tmt" % "singleaxis_2.11" % "0.3-SNAPSHOT"

  
  val akkaVersion = "2.4.14"

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion // all akka is ApacheV2
  val akkaKernel = "com.typesafe.akka" %% "akka-kernel" % akkaVersion
  val akkaRemote = "com.typesafe.akka" %% "akka-remote" % akkaVersion
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion

}

