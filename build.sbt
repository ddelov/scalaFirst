resolvers ++= Seq(
  "MavenCentral" at "http://central.maven.org/maven2"
)

ThisBuild / organization := "com.estafet.eet"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.7"
lazy val scalaFirst = (project in file("."))
  .aggregate(csv2kafka, kafkaScalaTest, kafkaCassandraInsert/*, util, core*/)

//lazy val util = (project in file("util"))

//lazy val core = (project in file("core"))
lazy val csv2kafka = (project in file("csv2kafka"))
lazy val akkaRestClient = (project in file("akkaRestClient"))
lazy val kafkaScalaTest = (project in file("kafka-scala-test"))
lazy val kafkaCassandraInsert = (project in file("kafka-cassandra-insert"))

//name := "scalaFirst"

//version := "0.1"

//scalaVersion := "2.12.7"

// https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
ThisBuild / libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.0.0"
ThisBuild / libraryDependencies += "com.typesafe" % "config" % "1.2.1"
ThisBuild / libraryDependencies += "log4j" % "log4j" % "1.2.17"
