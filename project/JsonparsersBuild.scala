import sbt._
import sbt.Keys._

object JsonparsersBuild extends Build {

  lazy val jsonparsers = Project(
    id = "jsonparsers",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "jsonParsers",
      organization := "org.example",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.1"
      // add other settings here
    )
  )
}
