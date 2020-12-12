package floyd_warshall

import com.typesafe.config.{Config, ConfigFactory}

object Main {

  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()

    val maxThreads = config.getInt(MAX_THREADS_PARAM)
    val inputFile = config.getString(INPUT_DATA_PARAM)

    val outputFile = if (config.hasPath(OUTPUT_DATA_PARAM)) {
      Some(config.getString(OUTPUT_DATA_PARAM))
    } else {
      None
    }

    new Algorithm(maxThreads).computeAndSave(inputFile, outputFile)
  }
}
