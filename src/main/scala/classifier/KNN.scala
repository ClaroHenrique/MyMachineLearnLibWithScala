package classifier
import model.Model
import datastructure.DataFrame
import Math.abs
import scala.collection.mutable.ArrayBuffer
//import org.apache.spark.sql.DataFrame

class KNN(var k: Int = 3, var metric: String = "euclidean"){

    def fit(dataFrame: DataFrame, label: List[AnyVal]): KNNModel = {
        var distanceFunction = selectDistanceFunction
        new KNNModel(dataFrame, label, k, distanceFunction)
    }

    private
    def selectDistanceFunction : (List[AnyVal],List[AnyVal]) => Double = {
        metric match {
            case "euclidean" => euclideanDistance
            case "manhattan" => manhattanDistance
        }
    }

    private
    def euclideanDistance(row1: List[AnyVal], row2: List[AnyVal]): Double = {
        var sum : Double = 0
        for ((e1,e2) <- (row1.map(convertToDouble) zip row2.map(convertToDouble))){
            sum += Math.pow((e1-e2),2)
        }
        Math.pow(sum,0.5)
    }

    private
    def manhattanDistance(row1: List[AnyVal], row2: List[AnyVal]): Double = {
        var sum : Double = 0
        for ((e1,e2) <- (row1.map(convertToDouble) zip row2.map(convertToDouble))){
            sum += abs(e1-e2)
        }
        Math.pow(sum,0.5)
    }

    private
    def convertToDouble(value: AnyVal) : Double = {
        value match { case i: Int => i case f: Float => f case d: Double => d }
    }

    override
    def toString : String ={
        s"{KNN model: (k,$k),(metric,$metric)}"
    }
}

class KNNModel(data : DataFrame, label : List[AnyVal], k: Int,  distanceFunction: (List[AnyVal],List[AnyVal]) => Double) extends Model{

    
    def predict(dataFrame : DataFrame) : List[AnyVal] = {
        dataFrame.getData.map(predictSample)
    }

    private
    case class Point(distance: Double, label: AnyVal)

    private
    def predictSample(sample: List[AnyVal]) : AnyVal = {
        var points = ArrayBuffer[Point]()
        for ((x,y) <- data.zip(label)){
            points += new Point(distanceFunction(x,sample),y)
        }
        points = points.sortWith((a: Point, b: Point) => a.distance < b.distance)
        points = points.slice(0,k)
        points.maxBy(x => points.count(_.label == x.label)).label
    }
}


