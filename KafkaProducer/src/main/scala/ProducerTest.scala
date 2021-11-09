import akka.actor.{ActorSystem, Cancellable, actorRef2Scala}
import akka.stream.ActorMaterializer

import java.util.{Date, Properties}
import org.apache.kafka.clients.producer._
import spray.json._
import org.apache.log4j.Logger

import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import net.liftweb.json._

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration._
import scala.util.Random
import akka.stream.scaladsl.Source
object ProducerTest {
 def main(args: Array[String]): Unit ={
   writeToKafka("stocks")
 }
  def writeToKafka(topic: String): Unit = {
    lazy val logger = Logger.getLogger(getClass)
    val timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss").format(LocalDateTime.now)
    logger.info("Reading json stream data from the file")
    val jsonString = scala.io.Source.fromFile("Stock.json")
    val lines = try jsonString.mkString finally jsonString.close()
    logger.info("Setting kafka properties")
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    /*props.put("linger.ms",5)
    props.put("batch.size",1000)
    props.put("queued.max.request",10)
    props.put("message.max.bytes",1000)*/
    logger.info("Starting Producer")
    val producer = new KafkaProducer[String, String](props)
    /*
    Trying to create streaming through scala AKKA
    implicit val system = ActorSystem("source")
    implicit val materializer = ActorMaterializer()
    val source:Source[String,Cancellable] = Source.tick(0.seconds,1.seconds,jsonString)

     */
    producer.send(new ProducerRecord[String,String](topic,timestamp,lines))
    println(lines)
    producer.close()

  }
}
