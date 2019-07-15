package com.knoldus.kafkaproducer

import java.util.Properties
import akka.actor.Actor
import com.knoldus.kafkaproducer.EmployeeProducer.EmployeeDetailProduce
import com.knoldus.utils.Configuration.KAFKA_SERVER
import com.knoldus.utils.LoggerUtil.logger
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}


object EmployeeProducer {

  case class EmployeeDetailProduce(topic: String, employeeDetail: String)

}

class EmployeeProducer extends Actor {

  val properties = new Properties()
  properties.put("bootstrap.servers", KAFKA_SERVER)
  properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  val employeeDetailProducer = new KafkaProducer[String, String](properties)

  override def receive: Receive = {
    case EmployeeDetailProduce(topic, employeeDetail) =>
      val record = new ProducerRecord[String, String](topic, employeeDetail)
      logger.info(s"Produce Data $employeeDetail on the topic $topic")
       employeeDetailProducer.send(record)

    case _ => ???
  }
}
