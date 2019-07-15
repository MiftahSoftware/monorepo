package com.knoldus.utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.knoldus.model.{EmployeeAddress, EmployeeDetail}

trait JsonSupport extends SprayJsonSupport {

  import spray.json.DefaultJsonProtocol._

  implicit val userJsonFormat = jsonFormat6(EmployeeDetail)
  implicit val addressJsonFormat = jsonFormat6(EmployeeAddress)
}