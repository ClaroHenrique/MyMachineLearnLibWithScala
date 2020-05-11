package support
import classifier.KNN
import metrics.AccuracyScore

object HelloWorld extends App {
 
  var (df,label) = IrisDataSet.loadDataFrame
  
  var classifier : KNN = new KNN(k=4)
  var model = classifier.fit(df,label)
  var predicted = model.predict(df)

  println("hello world!")
  println(model)
  println("accuracy:", AccuracyScore(label,predicted))
}
