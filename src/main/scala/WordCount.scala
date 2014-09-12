/*
 * Word count workload for BigDataBench
 */
package cn.ac.ict.bigdatabench

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object WordCount {

  def main(args: Array[String]): Unit = {
    if (args.length < 2) {
      System.err.println("Usage: WordCount <data_file> <save_file>" +
        " [<slices>]")
      System.exit(1)
    }

    var splits = 2
    val conf = new SparkConf().setAppName("BigDataBench WordCount")
    val spark = new SparkContext(conf)
    val filename = args(0)
    val save_file = args(1)
    if (args.length > 2) splits = args(2).toInt

    val lines = spark.textFile(filename, splits)
    val words = lines.flatMap(line => line.split(" "))
    val words_map = words.map(word => (word, 1))
    val result = words_map.reduceByKey(_ + _)
    //println(result.collect.mkString("\n"))
    result.saveAsTextFile(save_file)

    println("Result has been saved to: " + save_file)
  }

}