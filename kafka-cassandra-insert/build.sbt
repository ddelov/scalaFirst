name := "kafka-cassandra-insert"

version := "0.1"

scalaVersion := "2.12.7"

val scalatestEmbeddedKafka = "net.manub" %% "scalatest-embedded-kafka" % "2.0.0" % "test"

libraryDependencies ++= Seq(
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.6.0",
  "org.apache.kafka" % "kafka-clients" % "2.0.0",
  "com.typesafe" % "config" % "1.2.1",
  "log4j" % "log4j" % "1.2.17",
  "org.slf4j" % "slf4j-simple" % "1.7.25",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  scalatestEmbeddedKafka
)