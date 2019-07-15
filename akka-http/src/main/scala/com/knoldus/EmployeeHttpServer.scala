package com.knoldus

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.knoldus.route.EmployeeHttpService
import com.knoldus.utils.Configuration.{AKKA_HOST, AKKA_PORT}
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import com.knoldus.utils.LoggerUtil.logger

object EmployeeHttpServer extends App {

  implicit val system: ActorSystem = ActorSystem("EmployeeHttpServer")

  implicit val materializer: ActorMaterializer = ActorMaterializer()


  val employeeHttpService = new EmployeeHttpService
  val routes: Route = employeeHttpService.route

  Http().bindAndHandle(routes, AKKA_HOST, AKKA_PORT)

  logger.info(s"Server online at http://$AKKA_HOST:$AKKA_PORT/")

  Await.result(system.whenTerminated, Duration.Inf)

}
