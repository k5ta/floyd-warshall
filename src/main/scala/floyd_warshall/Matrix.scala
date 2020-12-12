package floyd_warshall

import java.io.{BufferedWriter, File, FileWriter}

import scala.io.Source.fromResource

case class Matrix(
  rowsNumber: Int,
  data: Array[Array[Double]]
) {

  private final lazy val OUTPUT_SEPARATOR: String = List.fill(8)(" ").mkString

  def saveToFile(fileName: String): Unit = {
    val file = new File(fileName)
    val bufferedWriter = new BufferedWriter(new FileWriter(file))

    for (singleArray <- data) {
      val rowString = singleArray.mkString(OUTPUT_SEPARATOR)
      bufferedWriter.write(s"$rowString\n")
    }

    bufferedWriter.close()
  }

  def prettyOutputFormat: String = {
    data
      .map { row =>
        row.mkString("\t")
      }
      .mkString("\n")
  }
}

object Matrix {
  def fromRawData(inputMatrix: Array[Array[Double]]): Matrix = {
    val nodesNumber = inputMatrix.length

    inputMatrix.find(_.length != nodesNumber).foreach { badArray =>
      throw new IllegalArgumentException(s"Wrong input data: expect internal arrays of size $nodesNumber, got ${badArray.length}")
    }

    Matrix(nodesNumber, inputMatrix)
  }

  def readFromFile(fileName: String): Matrix = {
    val fileSource = fromResource(fileName)

    val matrixData = fileSource.getLines()
                               .map { line =>
                                 line.split("\\s+").map {
                                   case "inf" => Double.PositiveInfinity
                                   case num => num.toDouble
                                 }
                               }
                               .toArray

    fileSource.close()

    fromRawData(matrixData)
  }
}
