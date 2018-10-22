import com.estafet.eet.data.source.CSVReader

object KafkaProducer extends App {
  import java.util.Properties

  import org.apache.kafka.clients.producer._

  val  props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  val TOPIC="muncho"

  lazy val fileSource = io.Source.fromFile("/home/ddelov/Downloads/mca_csv_data/test_data.csv", "UTF-8")
  val buf = new CSVReader << fileSource
  println(s"Found ${buf.size} records")
  for (row <- buf){
    producer.send(new ProducerRecord(TOPIC, s"${row.k}", row.v))
//    println(s"${row.k} -> ${row.v}")
  }

//  for(i<- 1 to 50){
//    val record = new ProducerRecord(TOPIC, "key", s"hello $i")
//    producer.send(record)
//  }
//
//  val record = new ProducerRecord(TOPIC, "key", "the end "+new java.util.Date)
//  producer.send(record)

  producer.close()
}
