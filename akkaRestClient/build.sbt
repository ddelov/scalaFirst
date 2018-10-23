val akkaVersion = "2.5.12"
val akkaHttpVersion = "10.1.5"
val sparkCassandraConnector = "2.3.2"
libraryDependencies ++= Seq(
  //"com.datastax.spark" %% "spark-cassandra-connector" % sparkCassandraConnector,
  "com.lightbend.akka" %% "akka-stream-alpakka-cassandra" % "1.0-M1",
  "org.xerial.snappy" % "snappy-java" % "1.1.7.2",

  "com.typesafe.akka" %% "akka-http"   % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-remote" % akkaVersion,
  "com.typesafe.akka" %% "akka-agent"  % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j"  % akkaVersion,
  "com.typesafe.akka" %% "akka-actor"  % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,

  // If testkit used, explicitly declare dependency on akka-streams-testkit in same version as akka-actor
  "com.typesafe.akka" %% "akka-http-testkit"   % akkaHttpVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion     % Test,
  "com.typesafe.akka" %% "akka-testkit"        % akkaVersion     % Test
)