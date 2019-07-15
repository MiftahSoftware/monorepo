package com.knoldus.route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.knoldus.elastic.ElasticSearchOperations
import com.knoldus.kafkaproducer.Publisher
import com.knoldus.model.{EmployeeAddress, EmployeeDetail}
import com.knoldus.utils.JsonSupport
import com.knoldus.utils.LoggerUtil.logger
import play.api.libs.json.{Format, Json}
import scala.util.{Failure, Success, Try}

class EmployeeHttpService extends JsonSupport with Publisher {

  lazy val route: Route =
    cors() {
      getHealth ~ addEmployeeAddress ~ addEmployeeDetail ~ getEmployeeAddress

    }

  def getHealth: Route =
    path("health") {
      get {
        complete("Your Health is good")
      }
    }

  def addEmployeeAddress: Route =
    path("addEmployeeAddress") {
      post {
        entity(as[EmployeeAddress]) { employeeAddressData =>
          Try {
            ElasticSearchOperations.insertEmployeeAddress(employeeAddressData)
          } match {
            case Success(_) => complete("EmployeeAddress successfully saved in elastic search")
            case Failure(ex) => logger.info(s"Exception while saving EmployeeAddress reason : ${ex.getMessage}")
              complete("Something went wrong please try again after some time")
          }
        }
      }
    }


  def addEmployeeDetail: Route =
    path("addEmployeeDetail") {
      post {
        entity(as[EmployeeDetail]) { employeeDetailData =>
          implicit val format: Format[EmployeeDetail] = Json.format[EmployeeDetail]
          Try {
            kafkaProducer(Json.stringify(Json.toJson(employeeDetailData)))
          } match {
            case Success(_) => complete("employee detail send to kafka topic successfully.")
            case Failure(ex) => logger.info(s"Exception while send data to kafka topic reason : ${ex.getMessage}")
              complete("Something went wrong please try again after some time")
          }
        }
      }
    }


  def getEmployeeAddress: Route =
    path("getEmployeeAddress") {
      get {
        parameters('key, 'value) { (key, value) => {
          Try {
            ElasticSearchOperations.searchEmployeeAddress(key, value).getHits.getHits
          } match {
            case Success(employeeAddress) => complete {
              if (employeeAddress.nonEmpty) employeeAddress.map(_.getSourceAsString).mkString("\n")
              else s"No Employee Address found for key: $key and value: $value"
            }
            case Failure(ex) => logger.info(s"Exception while fetching EmployeeAddress reason : ${ex.getMessage}")
              complete("Something went wrong please try again after some time")

          }
        }
        }

      }
    }
}
