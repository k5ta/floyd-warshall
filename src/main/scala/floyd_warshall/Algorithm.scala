package floyd_warshall

import scala.collection.parallel.CollectionConverters._

class Algorithm(printComputationTime: Boolean = false) {

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
    val result = matrix.data.map(_.zipWithIndex).zipWithIndex.par

    val algorithmStartTime = System.currentTimeMillis()

    matrix.rowsNumber.getRangeFromZero.foreach { k =>
      // kthRow and kthCol - immutable and threadsafe for optimizing calculations a bit
      val kthRow = result(k)._1.map(_._1)
      val kthCol = result.map(_._1(k)._1).toArray

      result.foreach { rowWithIndex =>
        val (row, i) = rowWithIndex

        val updatedRow = row.map {
          case (elem, j) if (i != k && j != k) =>
            val updatedElem = scala.math.min(elem, kthRow(j) + kthCol(i))
            (updatedElem, j)

          case other => other
        }

        result(i) = (updatedRow, i)
      }
    }

    if (printComputationTime) {
      val algorithmFinishTime = System.currentTimeMillis()
      println(s"Completed in ${algorithmFinishTime - algorithmStartTime} ms")
    }

    val resultData = result.map { case (row, _) => row.map(_._1) }
    Matrix.fromRawData(resultData.toArray)
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
