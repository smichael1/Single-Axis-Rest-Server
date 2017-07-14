import Dependencies._

name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.11"



libraryDependencies += filters
libraryDependencies += pkg
libraryDependencies += ccs
libraryDependencies += loc
libraryDependencies += seqSupport
libraryDependencies += javaWs
libraryDependencies += cache
libraryDependencies += singleaxis
libraryDependencies += javacsw
