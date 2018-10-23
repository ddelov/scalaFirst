import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import com.typesafe.config.ConfigFactory
import org.apache.log4j.Logger

class Producer {
  def writeToKafka(topic: String) = {
    val logger = Logger.getLogger(this.getClass)
    val config = ConfigFactory.load()
    val props = new Properties()

    logger.info(s"Request has come to write to kafka topic $topic ")
    props.put("bootstrap.servers", config.getString("BOOTSTRAP_SERVER"))
    props.put("key.serializer", config.getString("SERIALIZER"))
    props.put("value.serializer", config.getString("VALUE_SERIALIZER"))
    val producer = new KafkaProducer[String, Person](props)
    for (i <- 1 to 30) {
      val record = new ProducerRecord[String, Person](topic, Person("name" + i, i))
      producer.send(record)
    }
  }
}

case class Person(name: String, id: Int)


object ProducerMain extends App {
  val topic = "my-kafka-topic"
  (new Producer).writeToKafka(topic)
}