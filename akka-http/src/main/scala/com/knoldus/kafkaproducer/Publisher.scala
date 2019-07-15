package com.knoldus.kafkaproducer

import akka.actor.{ActorRef, ActorSystem, Props}
import com.knoldus.kafkaproducer.EmployeeProducer.EmployeeDetailProduce
import com.knoldus.utils.Configuration.EMP_DETAIL_TOPIC

trait Publisher {
  implicit val system: ActorSystem = ActorSystem("EmployeeProducerSystem")
  val producer: ActorRef = system.actorOf(Props[EmployeeProducer], "employeeDetailActor")

  def kafkaProducer(employeeDetail: String): Unit =
    producer ! EmployeeDetailProduce(EMP_DETAIL_TOPIC, employeeDetail)

}

object Publisher extends Publisher
