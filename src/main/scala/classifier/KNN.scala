package classifier
import model.Model
import datastructure.DataFrame
import Math.abs
import scala.collection.mutable.ArrayBuffer
import datastructure.DataTypes.DataRow
//import org.apache.spark.sql.DataFrame

class KNN(var k: Int = 3, var metric: String = "euclidean") {

  def fit(dataFrame: DataFrame): KNNModel = {
    var distanceFunction = selectDistanceFunction
    new KNNModel(dataFrame, k, distanceFunction)
  }

  private def selectDistanceFunction: (DataRow, DataRow) => Double = {
    metric match {
      case "euclidean" => euclideanDistance
      case "manhattan" => manhattanDistance
    }
  }

  private def euclideanDistance(row1: DataRow, row2: DataRow): Double = {
    var sum: Double = 0
    for ((e1, e2) <- (row1 zip row2)) {
      sum += Math.pow((e1 - e2), 2)
    }
    Math.pow(sum, 0.5)
  }

  private def manhattanDistance(row1: DataRow, row2: DataRow): Double = {
    var sum: Double = 0
    for ((e1, e2) <- (row1 zip row2)) {
      sum += abs(e1 - e2)
    }
    sum
  }

  override def toString: String = {
    s"{KNN predictor: (k,$k),(metric,$metric)}"
  }
}

class KNNModel(
    data: DataFrame,
    k: Int,
    distance: (DataRow, DataRow) => Double
) extends Model {

  def predict(dataFrame: DataFrame): List[AnyVal] = {
    dataFrame.getData.map(predictSample)
  }

  private case class Point(distance: Double, label: AnyVal)

  private def predictSample(sample: DataRow): AnyVal = {
    var points = ArrayBuffer[Point]()
    for ((x, y) <- data.zip(data.getLabel())) {
      points += new Point(distance(x, sample), y)
    }
    points = points.sortWith((a: Point, b: Point) => a.distance < b.distance)
    points = points.slice(0, k)
    points.maxBy(x => points.count(_.label == x.label)).label
  }

  override def toString: String = {
    s"{KNN model: (k,$k)}"
  }
}
