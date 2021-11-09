Assignment Datapipeline Kafka and Spark

Run Steps:

1) Docker-compose.yaml up (to start spark,kafka,zookepper docker)
2) Run the KafkaProducer (ProducerTest object) to injest the data into kafka
3) Run the Spark-Kafka-Consumer (TestSpark object) to read the streaming data for 30 sec window
4) To run the spark code on the docker spark master, change the setmaster setting
