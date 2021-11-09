name := "untitled"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.4.6",
  "org.apache.spark" %% "spark-sql" % "2.4.6"

)
libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.4.8"
libraryDependencies += "com.lihaoyi"%% "upickle" % "1.4.2"
libraryDependencies += "net.liftweb" % "lift-json" % "2.0"
libraryDependencies += "io.spray" %% "spray-json" % "1.3.6"