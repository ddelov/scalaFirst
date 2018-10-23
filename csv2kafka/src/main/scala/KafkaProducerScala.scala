import java.util.Properties

import com.estafet.eet.data.source.CSVReader
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

object KafkaProducerScala extends App {
  private val start: Long = System.nanoTime()
  //step 1 : read csv file
  //  lazy val fileSource = io.Source.fromFile("/home/ddelov/IdeaProjects/scalaFirst/csv2kafka/src/main/resources/data-1529499889365.csv", "UTF-8")
  lazy val fileSource = io.Source.fromFile("csv2kafka/src/main/resources/data-1529499889365.csv", "UTF-8")
  val buf = new CSVReader << fileSource
  println(s"Found ${buf.size} records")

  // step 2: send records to kafka
  val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  val TOPIC = "muncho"
  try {
    for (row <- buf) {
      producer.send(new ProducerRecord(TOPIC, s"${row.k}", row.v))
    }
  }
  finally {
    producer.close()
  }
}
