name := "scala-trace-debug"

organization := "scala-trace-debug"

// version := "0.1.0-SNAPSHOT"

version := "0.1.1"

scalaVersion := "2.11.7"

// crossScalaVersions := Seq("2.10.4", "2.11.2")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.11.5" % "test"
)

// to debug macros, use -Ymacro-debug-lite

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xlint", "-Xfatal-warnings", "-Yinline-warnings", "-Ywarn-inaccessible", "-Ywarn-infer-any", "-Ywarn-nullary-override", "-Ywarn-nullary-unit")

// initialCommands := "import example._"

// This is needed for macros:

libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _)
