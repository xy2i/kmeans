// The simplest possible sbt build file is just one line:

scalaVersion := "2.13.1"

name := "kmeans"
organization := "dev.xy2"
version := "1.0"
// https://mvnrepository.com/artifact/com.github.haifengl/smile-core
libraryDependencies += "com.github.haifengl" % "smile-core" % "2.2.1"
// https://mvnrepository.com/artifact/com.github.haifengl/smile-plot
libraryDependencies += "com.github.haifengl" % "smile-plot" % "2.2.1"
// https://mvnrepository.com/artifact/com.github.haifengl/smile-math
libraryDependencies += "com.github.haifengl" % "smile-math" % "2.2.1"
// https://mvnrepository.com/artifact/com.github.haifengl/smile-scala
libraryDependencies += "com.github.haifengl" %% "smile-scala" % "2.2.1"

// Couldn't load class org.slf4j.impl.StaticLoggerBinder
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

// Easier swing bindings
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1"

// Tests
libraryDependencies += "org.scalatest" % "scalatest_2.13" % "3.1.1" % "test"

// Fix assembly
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
