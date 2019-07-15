package com.knoldus.utils

import com.typesafe.config.ConfigFactory

object Configuration {
  val configFactory = ConfigFactory.load()
  val host = configFactory.getString("elasticsearch.host")
  val port = configFactory.getInt("elasticsearch.port")
  val empAddressIndex = configFactory.getString("elasticsearch.index")
  val empAddressType = configFactory.getString("elasticsearch.type")
  val EMP_DETAIL_TOPIC = configFactory.getString("kafka.topic")
  val KAFKA_SERVER = configFactory.getString("kafka.server")
  val AKKA_HOST = configFactory.getString("akkaHttp.host")
  val AKKA_PORT = configFactory.getInt("akkaHttp.port")

}
