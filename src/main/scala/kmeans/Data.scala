package kmeans

/**
 * Describes our data.
 * Each instance of Data is a data point, of dimension n.
 * May optionally carry the actualclass: this is a "cheat" column
 * to see how well our K-Means algorithm performs against
 * the actual results.
 *
 * {{{
 * val data : Data = Data(Seq(1,2,3,4), Option("Iris-setosa")
 * val reducedData = Data(Seq(2,3), Option("Iris-setsoa"), 1 to 2)
 * val withoutClass = Data(Seq(1,2,3,4))
 * }}}
 *
 * @param vector The actual data points.
 * @param realClass The actual class, as specified in the dataset. May not exist
 * @param columns If creating a reduced Data: which columns have been kept?
 */
case class Data(
                 private val vector: Seq[Double],
                 realClass: Option[String] = None,
                 /**
                  * A Data can be reduced (keeping only certain columns, for example.)
                  * So the question arises when we use it later: which columns have been kept?
                  * This field helps us keep track of that.
                  */
                 columns: Seq[Int] = Seq(0, 1, 2, 3)
               ){

  /**
   * Obtain the i-th column of this data.
   * {{{
   * val data: Data = Data(Seq(1,2,3,4), Option("Iris-setosa")
   * val reducedData = data.column(i)
   * val reducedData = data(i)
   * }}}
   * @param i The column to take.
   * @return A reduced Data.
   */
  def apply(i: Int): Double = vector(i)

  /**
   * Obtain the i-th column of this data.
   * {{{
   * val data: Data = Data(Seq(1,2,3,4), Option("Iris-setosa"))
   * val reducedData = data.column(i)
   * val reducedData = data(i) // alt.
   * }}}
   * @param i The column to take.
   * @return A reduced Data.
   */
  def column(i: Int): Data =
    Data( Seq(vector(i)), realClass, Seq(i) )

  /**
   * Obtain n columns of this data, as defined by a slice.
   * {{{
   * val data: Data = Data(Seq(1,2,3,4), Option("Iris-setosa"))
   * val reducedData = data.column(1 to 3)
   * val reducedData = data.column(Seq(0,2)) // alt.
   * }}}
   * @param slice The columns to take.
   * @return A reduced Data.
   */
  def columnSlice(slice: Seq[Int]): Data =
    Data( slice.map(i => vector(i)), realClass, slice )

  /**
   * Transforms a Data to a Seq.
   * {{{
   * val data: Data = Data(Seq(1,2,3,4), Option("Iris-setosa"))
   * val seq = data.toSeq
   * seq // [1,2,3,4]
   * }}}
   * @return
   */
  def toSeq = vector

  /**
   * Transforms a Data to an Array.
   * @return An Array.
   */
  def toArray = toSeq.toArray

  /**
   * Transforms a Data to a Double. Needs to be one-dimensional.
   * {{{
   * val data: Data = Data(Seq(1,2,3,4), Option("Iris-setosa"))
   * val reducedData = data.column(0)
   * reducedData.toDouble // 1
   * }}}
   * @note Make sure the Data is one-dimensional before transforming!
   * @return A number.
   */
  def toDouble: Double = {
    if (this.columns.length != 1) println("Warning: illegal toDouble")
    vector.head
  }

  /**
   * Returns this Data's real class as an int.
   * To do so, uses this mapping:
   * {{{
   * private val classToInt = Map(
   *    "Iris-setosa" -> 0,
   *    "Iris-versicolor" -> 1,
   *    "Iris-virginica" -> 2,
   * )
   * }}}
   * @return The real class, as an Int.
   */
  def realClassAsInt: Int =
    this.realClass match {
      case Some(realClassName) => Data.classToInt(realClassName)
      case None => -1
    }

  /**
   * The dimension of the Data.
   * {{{
   * val data: Data = Data(Seq(1,2,3,4), Option("Iris-setosa"))
   * data.length // 4, because the data is 4-dimensional
   * }}}
   * @return The dimension.
   */
  def length = vector.length
}
object Data {
  private val classToInt = Map(
    "Iris-setosa" -> 0,
    "Iris-versicolor" -> 1,
    "Iris-virginica" -> 2,
  )
}