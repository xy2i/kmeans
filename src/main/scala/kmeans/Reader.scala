package kmeans

import scala.io.Source

import kmeans.DataSet

/* Serialises our iris csv to Data.
   Call toData to convert.
 */
object Reader {
  def toData(filename: String): DataSet =
    Source.fromResource(filename)
      .getLines().toSeq
      .filter(!_.isEmpty()) // Keep empty lines
      .map(_.split(","))
      .map({ case Array(sl, sw, pl, pw, ac) =>
        Data(sl.toDouble, sw.toDouble, pl.toDouble, pw.toDouble, Option(ac))
      })
}
