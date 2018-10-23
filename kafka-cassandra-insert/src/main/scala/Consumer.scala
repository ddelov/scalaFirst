import java.util.{Properties, Collections}
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import collection.JavaConverters._
import com.typesafe.config.ConfigFactory
import org.apache.log4j.Logger
import scala.util.{Failure, Success, Try}
import Cassandra._
import scala.collection.JavaConversions
class Consumer {

  def consumeFromKafka(topic: String) = {
    val config = ConfigFactory.load()
    val logger = Logger.getLogger(this.getClass)
    val props = new Properties()

    logger.info(s"Comnsuming data kafka topic $topic ")
    props.put("bootstrap.servers", config.getString("BOOTSTRAP_SERVER"))
    props.put("key.deserializer", config.getString("DESERIALIZER"))
    props.put("value.deserializer", config.getString("VALUE_DESERIALIZER"))
    props.put("group.id", config.getString("GROUP_ID"))
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.getString("OFFSET"))

    val consumer: KafkaConsumer[String, Person] = new KafkaConsumer[String, Person](props)
    consumer.subscribe(Collections.singletonList(topic))
    val record = consumer.poll(5000).asScala.toList.map(_.value())
    record.foreach (processData(_))
    record
  }

  def processData(person: Person): Unit = {
    val config = ConfigFactory.load()
    println("processData: " + person.name)
    Try {
      Connector.getCasssandraBuilder
    } match {
      case Success(cluster) =>
        val session = Connector.getSession(config.getString("CASSANDRA_KEYSPACE"), cluster)
        session.execute(s"INSERT INTO person.person (id, name) VALUES (${person.UUID}, '${person.name}')")
        println(s"insert to DB ${person.name}")
        session.close()
        cluster.close()
      case Failure(exception) => println("Unable to Connect to Cassandra" + exception)
    }
  }


}

object ConsumerMain extends App {
  val topic = "cassandra-topic"
  (new Consumer).consumeFromKafka(topic)
}
