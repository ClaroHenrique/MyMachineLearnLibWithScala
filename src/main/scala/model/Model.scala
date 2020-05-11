package model
import datastructure.DataFrame

trait Model {

    def predict(dataFrame : DataFrame): List[AnyVal]
}
