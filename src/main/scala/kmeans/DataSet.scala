package kmeans

/**
 * A DataSet is a wrapper around Data with some names.
 * @param data The list of Data to pass.
 */
case class DataSet (data: Seq[Data]) {
  def this(ds: DataSet) = this(ds.data)

  def realClass : Seq[Option[String]] = data.map(_.realClass)
  /**
   * Returns a column ( a random variable. )
   * @param i The variable's number.
   * @return A random variable
   */
  def column(i: Int): DataSet = DataSet( data.map(v => v.column(i)) )
  /**
   * Returns a name given a column's number.
   * @param i The variable's number.
   * @return A name.
   */
  def namedColumn(i: Int): String = DataSet.names(i)

  /**
   * Returns a slice of columns.
   * @param slice The columns to keep.
   * @return A Dataset formed with the kept columns.
   */
  def columnSlice(slice: Seq[Int]): DataSet =
    DataSet( data.map(_.columnSlice(slice)) )

  /**
   * Returns a slice of names, given column ids.
   * @param slice The columns to keep.
   * @return A list of names.
   */
  def namedSlice(slice: Seq[Int]) : Seq[String] = slice.map(namedColumn)

  def realClassAsInt : Seq[Int] = data.map(_.realClassAsInt)

  def toVectorSeq : Seq[Seq[Double]] = data.map(_.toSeq)

  /**
   * Convenience method for Java.
   * @return A Java array.
   */
  def toArray: Array[Array[Double]] = toVectorSeq.map(_.toArray).toArray

  def length : Int = data.length
}
object DataSet {
  /**
   * Names for each field.
   */
  private val names = Seq("Sepal length", "Sepal width", "Petal length", "Petal width", "Real class name")
}
