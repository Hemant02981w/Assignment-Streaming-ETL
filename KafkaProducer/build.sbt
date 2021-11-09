name := "KafkaProducer"

version := "0.1"

scalaVersion := "2.13.7"

libraryDependencies += "org.apache.kafka" %% "kafka" % "2.4.1"
libraryDependencies += "com.lihaoyi"%% "upickle" % "0.9.5"
libraryDependencies += "net.liftweb" % "lift-json" % "2.0"
libraryDependencies += "io.spray" %% "spray-json" % "1.3.6"
val AkkaVersion = "2.5.23"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % AkkaVersion
libraryDependencies +="org.apache.logging.log4j" % "log4j-api" % "2.14.1"
libraryDependencies +="org.slf4j" % "slf4j-log4j12" % "1.7.10"