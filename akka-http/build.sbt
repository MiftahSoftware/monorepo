name := "akka-http-service"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.1",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.1",
  "com.typesafe.akka" %% "akka-stream" % "2.5.12",
  "ch.megard" %% "akka-http-cors" % "0.2.2",
  "com.typesafe" % "config" % "1.2.1",
  "org.elasticsearch" % "elasticsearch" % "6.2.4",
  "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % "6.1.2",
  "org.apache.kafka" %% "kafka" % "2.1.0",
  "com.typesafe.play" %% "play" % "2.6.11",
  "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime
)

test in assembly := {}

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case "reference.conf" =>  MergeStrategy.concat
  case "application.conf" => MergeStrategy.concat
  case x => MergeStrategy.first
}
