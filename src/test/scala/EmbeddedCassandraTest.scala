import com.datastax.driver.core.ResultSet

/**
  * Created by Delcho Delov on 22.10.18.
  *
  */
object EmbeddedCassandraTest {
  import java.net.InetAddress

  import com.datastax.driver.core.Cluster
  import org.cassandraunit.utils.EmbeddedCassandraServerHelper

  EmbeddedCassandraServerHelper.startEmbeddedCassandra(/*60.seconds.toMillis*/)
  val cluster = new Cluster
  .Builder()
    .addContactPoints(InetAddress.getByName("127.0.0.1"))
    .withPort(9142)
    .build()
  implicit val session = cluster.connect()
  private val set: ResultSet = session.execute("\"100\".toInt")
  private val i: Int = set.one().getInt(0)
  println(s"Result is ${i}")
  cluster.shutdown()
}
