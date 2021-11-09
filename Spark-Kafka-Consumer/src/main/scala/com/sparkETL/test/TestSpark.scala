package com.sparkETL.test
import org.apache.spark.sql.types.{ArrayType, IntegerType, StringType, StructField, StructType, TimestampType}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.functions
import org.apache.log4j.Logger
import org.apache.log4j._
import org.apache.spark.sql.SparkSession

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.collection.mutable.ArrayBuffer
object TestSpark {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel((Level.ERROR))
    lazy val logger = Logger.getLogger(getClass)
    logger.info("Initializing Spark ")
    /* set master to spark://localhost:7077 to run on the running spark docker */
    val spark = SparkSession.builder().master("local").appName("rate source").getOrCreate()
    logger.info("Reading the stream from Kafka")
    val data = spark.readStream.format("kafka").option("kafka.bootstrap.servers","localhost:9092")
    .option("subscribe","stocks").load().select(col("value").cast(("string")),col("timestamp"))

    println(data.isStreaming)
    data.printSchema()

    val dataSchema = ArrayType( StructType(
     ArrayBuffer(
       StructField("name",StringType,true),
       StructField("price",IntegerType,true)
     )))

    val explodeDf = data.withColumn("jsonData",explode(from_json(col("value"),dataSchema)))
    val jsonDF = explodeDf.withWatermark("timestamp", "1 minutes")
    val aggDF = jsonDF.groupBy(functions.window(jsonDF.col("timestamp"), "30 seconds", "30 seconds"),jsonDF.col("jsonData.name"))
      .avg("jsonData.price").alias("AveragePrice")
      .writeStream.outputMode("complete").format("console").option("truncate",false).start().awaitTermination()

  }


}

