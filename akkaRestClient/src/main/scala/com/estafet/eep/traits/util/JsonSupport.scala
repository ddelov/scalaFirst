package com.estafet.eep.traits.util

//#json-support
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.estafet.eep.actors.{Claim, Claims}
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val claimJsonFormat = jsonFormat2(Claim)
  implicit val claimsJsonFormat = jsonFormat1(Claims)
}
//#json-support
