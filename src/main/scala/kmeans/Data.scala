package kmeans

/**
 * Describes our data.
 * Each instance of Data is a data point, of dimension n.a  a a&ézqes
 * May optionally carry the actualclass: this is a "cheat" column
 * to see how well our k-means algorithm performs against
 * the actual results.
 *
 * @param vector The actual data points.
 * @param realClass The actual class, as specified in the dataset. May not exist
 */
case class Data(
                 // The actual data.
                 private val vector: Seq[Double],
                 // "Cheat" column: describes the data's actual class. Not required.
                 realClass: Option[String] = None,
                 /* A Data can be reduced (keeping only certain columns, for example.)
                   So the question arises when we use it later: which columns have been kept? */
                 columns: Seq[Int] = Seq(0, 1, 2, 3)
               ){

  def apply(i: Int): Double = vector(i)
  def column(i: Int): Data =
    Data( Seq(vector(i)), realClass, Seq(i) )
  def columnSlice(slice: Seq[Int]): Data =
    Data( slice.map(i => vector(i)), realClass, slice )
  def toSeq = vector
  def toArray = toSeq.toArray
  def realClassAsInt: Int =
    this.realClass match {
      case Some(realClassName) => Data.classToInt(realClassName)
      case None => -1
    }
  def length = vector.length
}
object Data {
  private val classToInt = Map(
    "Iris-setosa" -> 0,
    "Iris-versicolor" -> 1,
    "Iris-virginica" -> 2,
  )
}