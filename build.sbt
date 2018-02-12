name := "drone"

version := "1.0"

scalaVersion := "2.12.4"


libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % Test
libraryDependencies += "org.scalamock" %% "scalamock" % "4.0.0" % Test

coverageMinimum := 99

coverageFailOnMinimum := false

coverageHighlighting := true

parallelExecution in Test := false