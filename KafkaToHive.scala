package hive

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.StreamingContext
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.spark.streaming.Seconds
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.SaveMode
import kafka.serializer.StringDecoder

object KafkaToHive {
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  val (brokerlist,  topics) = ("localhost:9092",  "netflix")
  val sparkConf = new SparkConf()
    .setAppName(MyAPp).setMaster("local[2]")

  val sc = new SparkContext(sparkConf)
  val ssc = new StreamingContext(sc, Seconds(5))
  val hc = new HiveContext(ssc.sparkContext)

   import hc.implicits._
   
  hc.setConf("hive.metastore.uris","thrift://localhost:9083")
  hc.sql("use default")
   
  case class vmstat(r: String, b: String, swpd: String,
                    free: String, buff: String, cache: String,
                    si: String, so: String, bi: String,
                    bo: String, ins: String, cs: String,
                    us: String, sy: String, id: String,
                    wa: String)

  

  def main(args: Array[String]): Unit = {

    
    val topicMap = topics.split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> "localhost:9092")

    val lines = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder](ssc, kafkaParams, topicMap).map(_._2)

    
    lines.foreachRDD { rdd =>

      //rdd.foreach(println)

     val mytable = rdd.filter(line => !line.contains("memory"))
      .filter(line => !line.contains("buff")).map(line => line.split("[\\s]+"))
      .map(c =>
       vmstat(c(1), c(2), c(3), c(4), c(5), c(6), c(7), c(8), c(9), c(10), c(11), c(12), c(13), c(14), c(15), c(16))).toDF

     mytable.show(5)//.foreach(println)
      
     mytable.write.format("orc").mode(SaveMode.Append).saveAsTable("vmstat_kafka")
    }
    ssc.start
    ssc.awaitTermination
  }
}