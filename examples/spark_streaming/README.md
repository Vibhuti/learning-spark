Guide: http://spark.apache.org/docs/latest/streaming-programming-guide.html

## Simplest way to run streaming example using network  data...
╭─pkhadloya@pratikk2  /usr/local/Cellar/apache-spark/1.4.1/bin  ‹master*› 
╰─$ run-example streaming.NetworkWordCount localhost 9999



╭─pkhadloya@pratikk2  ~  
╰─$ nc -lk 9999



NetworkWordCount:

https://github.com/apache/spark/blob/master/examples/src/main/scala/org/apache/spark/examples/streaming/NetworkWordCount.scala


We can run it using spark-submit also:
╭─pkhadloya@pratikk2  ~/Projects/Juniper/hadoop/learning-spark/examples/spark_streaming  ‹master*› 
╰─$ spark-submit --class NetworkWordCount --master "local[*]" target/scala-2.10/spark_streaming_2.10-0.1-SNAPSHOT.jar localhost 9999


╭─pkhadloya@pratikk2  ~  
╰─$ nc -lk 9999


Output:


-------------------------------------------
Time: 1438034187000 ms
-------------------------------------------
(ffffff,1)
(fff,5)

-------------------------------------------
Time: 1438034188000 ms
-------------------------------------------
(ffffff,2)
(fff,4)

-------------------------------------------
Time: 1438034189000 ms
-------------------------------------------

Apache job can be view online:

http://localhost:4040

For data Aggregation:

added checkpoint


Kafka Integration

* Need to add artifact for kafka under sbt.
* config folder was not came under kafka installation through brew so need to copy it saparately.


START ZOOKEEPER SERVER

╭─pkhadloya@pratikk2  /usr/local/Cellar/kafka/0.8.2.1/bin  ‹master*› 
╰─$ zkServer start
     
START KAFKA SERVER

╭─pkhadloya@pratikk2  /usr/local/Cellar/kafka/0.8.2.1/bin  ‹master*› 
╰─$ ./kafka-server-start.sh ../config/server.properties

START PRODUCER
╭─pkhadloya@pratikk2  /usr/local/Cellar/kafka/0.8.2.1/bin  ‹master*› 
╰─$ kafka-console-producer.sh --broker-list localhost:9092 --topic test

LIST TOPICS UNDER KAFKA
╭─pkhadloya@pratikk2  /usr/local/Cellar/kafka/0.8.2.1/bin  ‹master*› 
╰─$ kafka-topics.sh --list --zookeeper localhost:2181
test

CONSUMER
╭─pkhadloya@pratikk2  /usr/local/Cellar/kafka/0.8.2.1/bin  ‹master*› 
╰─$ run-example streaming.DirectKafkaWordCount localhost:9092 test

OR
─pkhadloya@pratikk2  ~/Projects/Juniper/hadoop/learning-spark/examples/spark_streaming  ‹master*› 
$ spark-submit --class NetworkWordCount --master "local[*]" --jars ../../jars/spark-streaming-kafka-assembly_2.10-1.4.1.jar  target/scala-2.10/spark_streaming_2.10-0.1-SNAPSHOT.jar localhost:9092 test

OR

╭─pkhadloya@pratikk2  /usr/local/Cellar/kafka/0.8.2.1/bin  ‹master*› 
╰─$ kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning






