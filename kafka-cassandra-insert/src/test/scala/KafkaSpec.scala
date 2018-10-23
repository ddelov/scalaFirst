import java.util.UUID

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
    val uuid = UUID.randomUUID();
    val list = List(Person("Tervel", uuid))
    list.map(publishToKafka(topicName, _))
    val response = consumer.consumeFromKafka(topicName)
    assert(response.size > 0)
  }

  override def afterAll(): Unit = {
    EmbeddedKafka.stop()
  }
}
