package classifier


trait Classifier {
    def fit(): Classifier
    def predict(): Any
}

