package Cassandra
import com.datastax.driver.core.{Cluster, ResultSet, Session}
import com.typesafe.config.ConfigFactory
import scala.util.{Failure, Success, Try}
object Connector extends Connector

trait Connector {

  /**
    * This method creates the cluster by configuring port number and host
    * @return Cassandra Cluster
    */
  def getCasssandraBuilder: Cluster = {
    val config = ConfigFactory.load()
    Cluster.builder()
      .addContactPoint(config.getString("CASSANDRA_HOST"))
      .withPort(config.getInt("CASSANDRA_PORT"))
      .withCredentials("cassandra", "cassandra")
      .build()
  }

  /**
    * This method connects to cassandra keyspace
    *
    * @param keySpaceName
    * @param cluster
    * @return session
    */
  def getSession(keySpaceName: String, cluster: Cluster): Session = {
    Try{
      cluster.connect(keySpaceName)
    } match {
      case Success(session) => session
      case Failure(exception) => throw new Exception("Unable to connect to keyspace" + exception)
    }
  }

  def executeQuery(session: Session, query: String): ResultSet = {
    session.execute(query)
  }

}
