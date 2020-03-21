package kmeans

import scala.io.Source

/* Serialises our iris csv to Data.
   Call toData to convert.
 */
object Reader {
  /**
   * Given a filename in resources, create classes and return a DataSet.
   * @param filename The filename.
   * @return A fully formed dataset.
   */
  def toData(filename: String): DataSet =
    new DataSet(
      Source.fromResource(filename)
        .getLines().toSeq
        .filter(! _.isEmpty()) // Keep non-empty lines
        .map(_.split(","))
        .map{ case Array(sl, sw, pl, pw, ac) =>
          Data(Seq[Double](sl.toDouble, sw.toDouble, pl.toDouble, pw.toDouble), Option(ac))
        }
    )
}
