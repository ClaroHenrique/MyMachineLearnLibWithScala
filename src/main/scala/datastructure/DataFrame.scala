package datastructure

class DataFrame(data : List[List[AnyVal]]){

    def apply(row : Int, column : Int) : AnyVal = data(row)(column)
    def apply(row : Int) : List[AnyVal] = data(row)
    def getData = data
    def length : Int = data.length
    def zip (other : List[AnyVal]): List[(List[AnyVal],AnyVal)] ={
        data.zip(other)
    }
}