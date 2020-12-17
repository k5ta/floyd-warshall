package object floyd_warshall {

  final val MAX_THREADS_PARAM = "max-threads"
  final val INPUT_DATA_PARAM = "input-data"
  final val OUTPUT_DATA_PARAM = "output-data"
  final val PRINT_COMPUTATION_TIME_PARAM = "print-time"

  implicit class RangeFromZero(size: Int) {
    def getRangeFromZero: Range = 0.until(size)
  }
}
