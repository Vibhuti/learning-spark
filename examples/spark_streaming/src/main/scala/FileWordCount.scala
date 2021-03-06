import org.apache.spark._
import org.apache.spark.streaming._

object FileWordCount extends App {

  if (args.length < 1) {
    System.err.println("Usage: FileWordCount <directory>")
    System.exit(1)
  }

  val updateFunc = (values: Seq[Int], state: Option[Int]) => {
    val currentCount = values.foldLeft(0)(_ + _)

    val previousCount = state.getOrElse(0)

    Some(currentCount + previousCount)
  }

  val conf = new SparkConf().setAppName("myStream").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val ssc = new StreamingContext(sc, Seconds(10))

  ssc.checkpoint("./checkpoints/")
  val lines = ssc.textFileStream(args(0))
  val words = lines.flatMap(_.split(" "))
  val pairs = words.map(word => (word, 1))
  val windowedWordCounts = pairs.window(Minutes(1), Minutes(1)).updateStateByKey(updateFunc)
//  val timestamp: Long = System.currentTimeMillis / 1000
  windowedWordCounts.saveAsTextFiles("result/result")
//  val tagCounts = hashTags.window(Minutes(1), Seconds(5)).countByValue()
  windowedWordCounts.print()

  ssc.start()
  ssc.awaitTermination()
}


//every one minute dump the result
// abiltiy to handle signal
//capture the logs, weather that file added or not. Status of file we have queuded and wich are in the result and which are not.
//Safley removed the file that we already queued

// File with data aggregation
//import org.apache.spark._
//import org.apache.spark.streaming._
//
//object FileWordCount extends App {
//
//  if (args.length < 1) {
//    System.err.println("Usage: FileWordCount <directory>")
//    System.exit(1)
//  }
//
//  val updateFunc = (values: Seq[Int], state: Option[Int]) => {
//    val currentCount = values.foldLeft(0)(_ + _)
//
//    val previousCount = state.getOrElse(0)
//
//    Some(currentCount + previousCount)
//  }
//
//  val conf = new SparkConf().setAppName("myStream").setMaster("local[*]")
//  val sc = new SparkContext(conf)
//  val ssc = new StreamingContext(sc, Seconds(10))
//
//  ssc.checkpoint("./checkpoints/")
//  val lines = ssc.textFileStream(args(0))
//  val words = lines.flatMap(_.split(" "))
//  val pairs = words.map(word => (word, 1))
//  val windowedWordCounts = pairs.updateStateByKey(updateFunc)
//  //  val timestamp: Long = System.currentTimeMillis / 1000
//  windowedWordCounts.saveAsTextFiles("result/result")
//
//  windowedWordCounts.print()
//
//  ssc.start()
//  ssc.awaitTermination()
//}

/**
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Counts words in new text files created in the given directory
 * Usage: HdfsWordCount <directory>
 *   <directory> is the directory that Spark Streaming will use to find and read new text files.
 *
 * To run this on your local machine on directory `localdir`, run this example
 *    $ bin/run-example \
 *       org.apache.spark.examples.streaming.HdfsWordCount localdir
 *
 * Then create a text file in `localdir` and the words in the file will get counted.
 */
object HdfsWordCount {
  def main(args: Array[String]) {
    if (args.length < 1) {
      System.err.println("Usage: HdfsWordCount <directory>")
      System.exit(1)
    }

    StreamingExamples.setStreamingLogLevels()
    val sparkConf = new SparkConf().setAppName("HdfsWordCount")
    // Create the context
    val ssc = new StreamingContext(sparkConf, Seconds(2))

    // Create the FileInputDStream on the directory and use the
    // stream to count words in new files created
    val lines = ssc.textFileStream(args(0))
    val words = lines.flatMap(_.split(" "))

    print("*******************\n")
    print(args(0) + "\n")
    print(lines)
    print(words)
    print("*******************\n")
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    wordCounts.print()
    ssc.start()
    ssc.awaitTermination()
  }
}

**/
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// scalastyle:off println



/**
 * Consumes messages from one or more topics in Kafka and does wordcount.
 * Usage: DirectKafkaWordCount <brokers> <topics>
 *   <brokers> is a list of one or more Kafka brokers
 *   <topics> is a list of one or more kafka topics to consume from
 *
 * Example:
 *    $ bin/run-example streaming.DirectKafkaWordCount broker1-host:port,broker2-host:port \
 *    topic1,topic2
 */

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// scalastyle:off println



/**
 * Consumes messages from one or more topics in Kafka and does wordcount.
 * Usage: DirectKafkaWordCount <brokers> <topics>
 *   <brokers> is a list of one or more Kafka brokers
 *   <topics> is a list of one or more kafka topics to consume from
 *
 * Example:
 *    $ bin/run-example streaming.DirectKafkaWordCount broker1-host:port,broker2-host:port \
 *    topic1,topic2
 */

// scalastyle:on println
/**
import kafka.serializer.StringDecoder
import org.apache.spark._
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka._

object NetworkWordCount {

  val updateFunc = (values: Seq[Int], state: Option[Int]) => {
    val currentCount = values.foldLeft(0)(_ + _)

    val previousCount = state.getOrElse(0)

    Some(currentCount + previousCount)
  }

  def main(args: Array[String]) {
    if (args.length < 2) {
      System.err.println(s"""
                            |Usage: DirectKafkaWordCount <brokers> <topics>
                            |  <brokers> is a list of one or more Kafka brokers
                            |  <topics> is a list of one or more kafka topics to consume from
                            |
        """.stripMargin)
      System.exit(1)
    }

    StreamingExamples.setStreamingLogLevels()

    // Create context with 2 second batch interval
    val sparkConf = new SparkConf().setAppName("myStream").setMaster("local[*]").set("spark.driver.allowMultipleContexts", "true")

    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sparkConf, Seconds(2))

    ssc.checkpoint("./checkpoints/")

    // Create direct kafka stream with brokers and topics
    val Array(brokers, topics) = args
    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topicsSet)

    // Get the lines, split them into words, count the words and print
    val lines = messages.map(_._2)
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(word => (word, 1))
    val windowedWordCounts = pairs.updateStateByKey(updateFunc)
    windowedWordCounts.saveAsTextFiles("result/result")
    windowedWordCounts.print()

    // Start the computation
    ssc.start()
    ssc.awaitTermination()
  }
}
  **/

/** STATEFUL TRANSFORMATION & CHECK-POINTING
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._

object NetworkWordCount extends App {
  val updateFunc = (values: Seq[Int], state: Option[Int]) => {
    val currentCount = values.foldLeft(0)(_ + _)

    val previousCount = state.getOrElse(0)

    Some(currentCount + previousCount)
  }

  val conf = new SparkConf().setAppName("myStream").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val ssc = new StreamingContext(sc, Seconds(10))

  ssc.checkpoint("./checkpoints/")
  val lines = ssc.socketTextStream("localhost", 9999)
  val words = lines.flatMap(_.split(" "))
  val pairs = words.map(word => (word, 1))
  val windowedWordCounts = pairs.updateStateByKey(updateFunc)
  windowedWordCounts.saveAsTextFiles("result/result")
  windowedWordCounts.print()

  ssc.start()
  ssc.awaitTermination()
}

**/

/**  Without check-pointing and stateful transformations
// scalastyle:off println
// package org.apache.spark.examples.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.StreamingContext._ // not necessary since Spark 1.3
/**
 * Counts words in UTF8 encoded, '\n' delimited text received from the network every second.
 *
 * Usage: NetworkWordCount <hostname> <port>
 * <hostname> and <port> describe the TCP server that Spark Streaming would connect to receive data.
 *
 * To run this on your local machine, you need to first run a Netcat server
 *    `$ nc -lk 9999`
 * and then run the example
 *    `$ bin/run-example org.apache.spark.examples.streaming.NetworkWordCount localhost 9999`
 */
object NetworkWordCount {
  def main(args: Array[String]) {
    if (args.length < 2) {
      System.err.println("Usage: NetworkWordCount <hostname> <port>")
      System.exit(1)
    }

    StreamingExamples.setStreamingLogLevels()

    // Create the context with a 1 second batch size
    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    val ssc = new StreamingContext(conf, Seconds(1))

    // Create a socket stream on target ip:port and count the
    // words in input stream of \n delimited text (eg. generated by 'nc')
    // Note that no duplication in storage level only for running locally.
    // Replication necessary in distributed scenario for fault tolerance.
    val lines = ssc.socketTextStream(args(0), args(1).toInt)
    val words = lines.flatMap(_.split(" "))

    // Count each word in each batch
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)

    // Print the first ten elements of each RDD generated in this DStream to the console
    wordCounts.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
// scalastyle:on println

**/