import java.util.{Properties, Collections}
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import collection.JavaConverters._
import com.typesafe.config.ConfigFactory
import org.apache.log4j.Logger

class Consumer {

  def consumeFromKafka(topic: String) = {
    val config = ConfigFactory.load()
    val logger = Logger.getLogger(this.getClass)
    val props = new Properties()

    logger.info(s"Request has come to comnsume data kafka topic $topic ")
    props.put("bootstrap.servers", config.getString("BOOTSTRAP_SERVER"))
    props.put("key.deserializer", config.getString("DESERIALIZER"))
    props.put("value.deserializer", config.getString("VALUE_DESERIALIZER"))
    props.put("group.id", config.getString("GROUP_ID"))
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.getString("OFFSET"))

    val consumer: KafkaConsumer[String, Person] = new KafkaConsumer[String, Person](props)
    consumer.subscribe(Collections.singletonList(topic))
    val record = consumer.poll(5000).asScala.toList.map(_.value())

    record
  }
}

object ConsumerMain extends App {
  val topic = "my-kafka-topic"
  (new Consumer).consumeFromKafka(topic)
}
