package metrics
import scala.collection.Iterable

object AccuracyScore {

  def apply(expected: Iterable[AnyVal], predicted: Iterable[AnyVal]): Double = {
    if (expected.size != predicted.size) {
      return -1
    }

    var hits = expected.zip(predicted).count(x => (x._1 == x._2))
    var total = expected.size.toDouble
    hits / total
  }
}
