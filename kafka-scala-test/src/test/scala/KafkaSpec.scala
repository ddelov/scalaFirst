import net.manub.embeddedkafka._
import org.scalatest._


class KafkaSpec extends FlatSpec with EmbeddedKafka with BeforeAndAfterAll {
  val topicName = "my-kafka-topic"
  val producer = new Producer
  val consumer = new Consumer

  implicit val config = EmbeddedKafkaConfig(kafkaPort = 9093, zooKeeperPort = 2183)

  override def beforeAll(): Unit = {
    EmbeddedKafka.start()
  }

  it should "publish synchronously data to Kafka" in {
    producer.writeToKafka(topicName)
    val response = consumeFirstStringMessageFrom(topicName)
    assert(Some(response).isDefined)
  }

  it should "consume message from published topic" in {
    implicit val serializer = new UserSerializer
    val list = List(Person("Tervel", 1), Person("Vasil", 2), Person("Kubrat", 3),
      Person("Krum", 4), Person("Asparuh", 5), Person("Irnik", 6))
    list.map(publishToKafka(topicName, _))
    val response = consumer.consumeFromKafka(topicName)
    assert(response.size > 6)
  }

  override def afterAll(): Unit = {
    EmbeddedKafka.stop()
  }
}
