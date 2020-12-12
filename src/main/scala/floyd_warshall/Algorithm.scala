package floyd_warshall

import java.util.concurrent.ForkJoinPool

import scala.collection.mutable
import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.ForkJoinTaskSupport

class Algorithm(maxThreads: Int) {

  def computeAndSave(inputFile: String, outputFile: Option[String]): Unit = {
    val result = computeForMatrixFile(inputFile)

    outputFile.fold {
      println("Result matrix:")
      println(result.prettyOutputFormat)
    } { file =>
      result.saveToFile(file)
      println(s"Saved result to file $file")
    }
  }

  def computeForMatrixFile(fileName: String): Matrix = {
    val matrix = Matrix.readFromFile(fileName)
    computeForMatrix(matrix)
  }

  def computeForMatrix(matrix: Matrix): Matrix = {
    val result = matrix.data.map(mutable.ArrayBuffer.from(_)).par
    result.tasksupport = createParallelTaskSupport(maxThreads)

    matrix.rowsNumber.getRangeFromZero.foreach { k =>
      // kthRow and kthCol - immutable and threadsafe for optimizing calculations a bit
      val kthRow = result(k)
      val kthCol = result.map(_(k)).toArray

      for (i <- 0.until(result.length)) {
        result(i) = result(i).zipWithIndex.map {
          case (elem, j) if (i != k && j != k) =>
            // using kthRow and kthCol instead of result(i)(k) + result(k)(j)
            scala.math.min(elem, kthRow(j) + kthCol(i))

          case other => other._1
        }
      }
    }

    Matrix.fromRawData(result.map(_.toArray).toArray)
  }

  private def createParallelTaskSupport(threadsNumber: Int): ForkJoinTaskSupport = {
    new ForkJoinTaskSupport(new ForkJoinPool(threadsNumber))
  }
}

object Algorithm {

  def getMatrixIndices(matrixSize: Int): Seq[(Int, Int)] = {
    matrixSize.getRangeFromZero.flatMap { i =>
      matrixSize.getRangeFromZero.map { j => (i, j) }
    }
  }

  def saveToFile(matrix: Matrix, fileName: String): Unit = {
    matrix.saveToFile(fileName)
  }

}
