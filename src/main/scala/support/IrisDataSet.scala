package support
import datastructure.DataFrame
import scala.io.Source

object IrisDataSet {

  def loadDataFrame(): (DataFrame, List[AnyVal]) = {
    var source = Source.fromFile("iris.data").getLines.toList
    source = source.slice(0, source.size - 1)
    var allData = source.map(proccessLine).toList

    (new DataFrame(allData.map(extractFeatures)), allData.map(extractLabel))
  }

  private def proccessLine(line: String): List[AnyVal] = {
    var sampleData = new Array[AnyVal](5)
    val lineFields = line.split(",")

    sampleData(0) = lineFields(0).toDouble
    sampleData(1) = lineFields(1).toDouble
    sampleData(2) = lineFields(2).toDouble
    sampleData(3) = lineFields(3).toDouble
    sampleData(4) = encodeLabel(lineFields(4))

    List(
      sampleData(0),
      sampleData(1),
      sampleData(2),
      sampleData(3),
      sampleData(4)
    )
  }

  private def encodeLabel(label: String): Char = {
    label match {
      case "Iris-setosa"     => 's'
      case "Iris-versicolor" => 'v'
      case "Iris-virginica"  => 'i'
      case _                 => 'k'
    }
  }

  private def extractFeatures(sample: List[AnyVal]): List[AnyVal] = {
    List(sample(0), sample(1), sample(2), sample(3))
  }

  private def extractLabel(sample: List[AnyVal]): AnyVal = {
    sample(4)
  }
}
