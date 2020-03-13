package kmeans

/**
 * Describes our data.
 * Each instance of Data is a data point.
 * May optionally carry the actualclass: this is a "cheat" column
 * to see how well our k-means algorithm performs against
 * the actual results.
 *
 * @param sepalLength Sepal length.
 * @param sepalWidth Sepal width.
 * @param petalLength Petal length.
 * @param petalWidth Petal width.
 * @param actualClass The actual class, as specified in the document.
 */
case class Data(
                 sepalLength: Double = 0.0,
                 sepalWidth: Double = 0.0,
                 petalLength: Double = 0.0,
                 petalWidth: Double = 0.0,
                 // "Cheat" column: describes the data's actual class. Not required.
                 actualClass: Option[String] = None,
               ) {
  def toSeq = Seq(sepalLength, sepalWidth, petalLength, petalWidth)
}
object Data {
  /**
   * Transform a Seq to a data.
   * @param vector The Seq to transform.
   * @return A new Data.
   */
  def apply(vector: Seq[Double]): Data =
    vector match {
      case Seq(sl, sw, pl, pw) => Data(sl, sw, pl, pw, None)
    }
}