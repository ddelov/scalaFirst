package com.estafet.eep.actors

import akka.actor.Actor
import akka.event.Logging
import com.datastax.driver.core.{BoundStatement, Cluster, ResultSet, Row}
import com.estafet.eep.actors.ClaimRegistryActor.{CountAll, ReadFirst}

import scala.collection.mutable.ListBuffer


//#user-case-classes
case class Claim(ids: Int, name: String)//, age: Int, countryOfResidence: String)
case class Claims(records: List[Claim])
//#user-case-classes

object ClaimRegistryActor {
  case class ReadFirst(n : Int)
  case object CountAll
}

class ClaimRegistryActor(cluster: Cluster) extends Actor {
  val log = Logging(context.system, this)
  val session = cluster.connect("eepkeyspace")
  val countAll = new BoundStatement(session.prepare("select count(*) as c from tweet;"))
  val readAll = new BoundStatement(session.prepare("select * from tweet;"))

  override def receive: Receive = {
    case ReadFirst(n:Int) =>
      val futureResultSet = session.executeAsync(readAll)

//      while (!futureResultSet.isDone)
//        log.debug("Waiting for request to complete")
      val resultSet : ResultSet = futureResultSet.get
      var i = 0
      var row : Row = null
      var records : ListBuffer[Claim] = ListBuffer()

      row = resultSet.one()
      while (i < n && row != null) {
          i += 1
          val oneClaim = new Claim(row.getInt("sid"), row.getString("name"))
          records += oneClaim
          row = resultSet.one()
      }

      val claims = Claims(records.toList)
      sender() ! claims

    case CountAll =>
      val futureResultSet = session.executeAsync(countAll)
//      while (!futureResultSet.isDone)
//        log.debug("Waiting for request to complete")
      val resultSet = futureResultSet.get
      sender() ! "Total Count: " + resultSet.one.toString


  }
}
