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

For data integration:








For kafka integration:
Source	Artifact 
Kafka 	spark-streaming-kafka_2.10 


By adding  sbt, and after building image using sbt package. 
we can build our own straming Input object. And ran the same command.



