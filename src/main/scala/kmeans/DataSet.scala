package kmeans

/**
 * A DataSet is a list of Data, with methods for convenient manipulation.
 * {{{
 * val ds = Reader.fromFile("iris.data")
 * val firstColumn = ds.column(0).toSeq // Returns a list of all the first values for each Data.
 * val reduced = ds.columnSlice(1 to 3) // A dataset with reduced dimension.
 * }}}
 * @param data The list of Data to pass.
 */
case class DataSet (data: Seq[Data]) {
  /**
   * @constructor Creates a DataSet from another one.
   * @param ds A DataSet.
   */
  def this(ds: DataSet) = this(ds.data)

  /**
   * Returns each of the data's real classes.
   * @return An array of each's data classes.
   */
  def realClass : Seq[Option[String]] = data.map(_.realClass)

  /**
   * Obtain the i-th column of each data.
   * {{{
   * val ds: DataSet = DataSet( Seq( Data(Seq(1,2,3,4)),
   *                                 Data(Seq(5,6,7,8)) ) )
   * val reducedDs = ds.column(1)
   * reducedDs.toSeq // [2,6]
   * }}}
   * @param i The column to take.
   * @return A reduced DataSet
   */
  def column(i: Int): DataSet = DataSet( data.map(v => v.column(i)) )

  /**
   * Returns a name given a column's number.
   * @param i The variable's number.
   * @return A name.
   */
  def namedColumn(i: Int): String = DataSet.names(i)

  /**
   * Obtain n columns of this data, as defined by a slice.
   * {{{
   * val ds: DataSet = DataSet( Seq( Data(Seq(1,2,3,4)),
   *                                 Data(Seq(5,6,7,8)) ) )
   * val reducedDs = ds.columnSlice(1 to 3)
   * reducedDs.toVectorSeq // [[2,3,4], [6,7,8]]
   * }}}
   * @param slice A slice of columns to take.
   * @return A reduced DataSet
   */
  def columnSlice(slice: Seq[Int]): DataSet =
    DataSet( data.map(_.columnSlice(slice)) )

  /**
   * Returns a slice of names, given column ids.
   * @param slice The columns to keep.
   * @return A list of names.
   */
  def namedSlice(slice: Seq[Int]) : Seq[String] = slice.map(namedColumn)

  /**
   * Returns each Data's real class as an int.
   * To do so, uses this mapping:
   * {{{
   * private val classToInt = Map(
   *    "Iris-setosa" -> 0,
   *    "Iris-versicolor" -> 1,
   *    "Iris-virginica" -> 2,
   * )
   * }}}
   * @return A list of real classes, as Ints.
   */
  def realClassAsInt : Seq[Int] = data.map(_.realClassAsInt)

  /**
   * Transform a Dataset to an Array of vectors.
   * {{{
   * val ds: DataSet = DataSet( Seq( Data(Seq(1,2,3,4)),
   *                                 Data(Seq(5,6,7,8)) ) )
   * val reducedDs = ds.columnSlice(1 to 3)
   * reducedDs.toVectorSeq // [[2,3,4], [6,7,8]]
   * }}}
   * @return A seq of vectors.
   */
  def toVectorSeq : Seq[Seq[Double]] = data.map(_.toSeq)

  /**
   * Returns the current DataSet as a seq. Needs to be one-dimensional.
   * {{{
   * val ds: DataSet = DataSet( Seq( Data(Seq(1,2,3,4)),
   *                                 Data(Seq(5,6,7,8)) ) )
   * val reducedDs = ds.column(1)
   * reducedDs.dim // 1
   * reducedDs.toSeq // [2,6]
   * }}}
   * @return A reduced DataSet
   */
  def toSeq : Seq[Double] = {
    if (this.dim != 1) println("Warning: illegal toSeq")
    data.map(_.toDouble)
  }

  /**
   * Convenience method for Java.
   * @return A Java array.
   */
  def toArray: Array[Array[Double]] = toVectorSeq.map(_.toArray).toArray

  /**
   * Returns the total number of Data in the dataset.
   * {{{
   * val ds: DataSet = DataSet( Seq( Data(Seq(1,2,3,4)),
   *                                 Data(Seq(5,6,7,8)) ) )
   * val reducedDs = ds.column(1)
   * ds.dim // 4
   * ds.length // 2
   * reducedDs.dim // 1
   * reducedDs.length // 2
   * }}}
   * @return The number of Datas.
   */
  def length : Int = data.length

  /**
   * Returns the dimension of each Data in the dataset.
   * {{{
   * val ds: DataSet = DataSet( Seq( Data(Seq(1,2,3,4)),
   *                                 Data(Seq(5,6,7,8)) ) )
   * val reducedDs = ds.column(1)
   * ds.dim // 4
   * ds.length // 2
   * reducedDs.dim // 1
   * reducedDs.length // 2
   * }}}
   * @return The number of Datas.
   */
  def dim = data(0).length
}
object DataSet {
  /**
   * Names for each field.
   */
  private val names = Seq("Sepal length", "Sepal width", "Petal length", "Petal width", "Real class name")
}
