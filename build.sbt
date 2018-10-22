name := "scalaFirst"

version := "0.1"

scalaVersion := "2.12.7"

// https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.0.0"
// https://mvnrepository.com/artifact/org.cassandraunit/cassandra-unit
libraryDependencies += "org.cassandraunit" % "cassandra-unit" % "1.0.1.4" % Test
// https://mvnrepository.com/artifact/com.datastax.cassandra/cassandra-driver-core
libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-core" % "1.0.0-beta1"
