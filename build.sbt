name := "twitter-assignment"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % "test"
libraryDependencies += "org.apache.commons" % "commons-io" % "1.3.2"
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.11"

libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "4.0.6"
//libraryDependencies += "com.danielasfregola" %% "twitter4s" % "6.2"

libraryDependencies += "com.typesafe" % "config" % "1.4.0"