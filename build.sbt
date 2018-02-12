name := "drone"

version := "1.0"

scalaVersion := "2.12.4"


libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % Test
libraryDependencies += "org.scalamock" %% "scalamock" % "4.0.0" % Test

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")