
//name := "scalaFirst"

//version := "0.1.buba.lazi"
val scalatestEmbeddedKafka = "net.manub" %% "scalatest-embedded-kafka" % "2.0.0" % "test"

libraryDependencies ++= Seq(
  //"org.apache.kafka" %% "kafka" % "0.10.2.2",
  "org.apache.kafka" % "kafka-clients" % "2.0.0",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "com.typesafe" % "config" % "1.2.1",
  "log4j" % "log4j" % "1.2.17",
  "org.slf4j" % "slf4j-simple" % "1.7.25",
  scalatestEmbeddedKafka
)