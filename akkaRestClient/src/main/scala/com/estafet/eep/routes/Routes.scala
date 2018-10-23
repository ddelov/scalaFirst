package com.estafet.eep.routes

import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import akka.pattern.ask
import akka.http.scaladsl.server.Directives._

import scala.concurrent.duration._
import com.estafet.eep.actors.ClaimRegistryActor.{CountAll, ReadFirst}
import com.estafet.eep.actors.Claims
import com.estafet.eep.traits.util.JsonSupport

import scala.concurrent.{Await, Future}

trait Routes extends JsonSupport {

  // we leave these abstract, since they will be provided by the App
  implicit def system: ActorSystem
  lazy val log = Logging(system, classOf[Routes])

  // other dependencies that UserRoutes use
  def dbManagerActorRef: ActorRef
  // Required by the `ask` (?) method below
  implicit lazy val timeout = Timeout(5 seconds) // usually we'd obtain the timeout from the system's configuration

  lazy val restRoutes : Route = {readFirstRecordsRoute ~ readCountRecordsRoute}

  val readFirstRecordsRoute = path("readFirst100") {
    get {
      val claims : Future[Claims] = (dbManagerActorRef ? ReadFirst(100)).mapTo[Claims]

      val response = Await.result(claims, 3 seconds)
      complete(StatusCodes.OK, response)
    }
  }

  val readCountRecordsRoute = path("count") {
    get {
      val futureResponse : Future[String] = (dbManagerActorRef ? CountAll).mapTo[String]
      val response = Await.result(futureResponse, 3 seconds)
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, response))
    }
  }
}
