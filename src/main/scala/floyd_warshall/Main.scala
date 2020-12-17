package floyd_warshall

import com.typesafe.config.ConfigFactory

object Main {

  private final val SYSTEM_THREADS_NUMBER = "scala.concurrent.context.numThreads"

  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()

    val maxThreads = config.getInt(MAX_THREADS_PARAM)
    System.setProperty(SYSTEM_THREADS_NUMBER, maxThreads.toString)

    val inputFile = config.getString(INPUT_DATA_PARAM)

    val outputFile = if (config.hasPath(OUTPUT_DATA_PARAM)) {
      Some(config.getString(OUTPUT_DATA_PARAM))
    } else {
      None
    }

    val printComputationTime = if (config.hasPath(PRINT_COMPUTATION_TIME_PARAM)) {
      config.getBoolean(PRINT_COMPUTATION_TIME_PARAM)
    } else {
      false
    }

    new Algorithm(printComputationTime).computeAndSave(inputFile, outputFile)
  }
}
