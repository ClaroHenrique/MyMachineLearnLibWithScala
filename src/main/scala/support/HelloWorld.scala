package support
import classifier.KNN
import metrics.AccuracyScore

object HelloWorld extends App {

  var df = IrisDataSet.loadDataFrame()

  var classifier: KNN = new KNN(k = 4)
  var model = classifier.fit(df)
  var predicted = model.predict(df)

  val acc = AccuracyScore(df.getLabel(), predicted)

  println("hello world!")
  println(model)
  println("accuracy:", acc)
}
