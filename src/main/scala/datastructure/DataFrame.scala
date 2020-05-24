package datastructure
import datastructure.DataTypes.DataRow

class DataFrame(rawData: List[List[AnyVal]]) {
  val data = DataFrameSupport.processRawData(rawData)

  def apply(row: Int, column: Int): Double = data(row)(column)
  def apply(row: Int): DataRow = data(row)
  def getData = { data }
  def length: Int = data.length
  def zip(other: List[AnyVal]): List[(DataRow, AnyVal)] = {
    data.zip(other)
  }
}

object DataFrameSupport {

  def processRawData(rawData: List[List[AnyVal]]): List[DataRow] = {
    rawData.map(row => convertRowToDouble(row))
  }

  def convertRowToDouble(row: List[AnyVal]): DataRow = {
    row.map(x => valueToDouble(x))
  }

  def valueToDouble(value: AnyVal): Double = {
    value match {
      case i: Int    => i
      case f: Float  => f
      case d: Double => d
    }
  }
}
