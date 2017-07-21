import Dependencies._

name := """play-java"""

version := "0.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.1"



libraryDependencies += filters
libraryDependencies += pkg
libraryDependencies += ccs
libraryDependencies += loc
libraryDependencies += seqSupport
libraryDependencies += javaWs
libraryDependencies += singleaxis
libraryDependencies += javacsw
libraryDependencies += guice