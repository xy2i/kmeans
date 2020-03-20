package vis

import kmeans.DataSet
import kmeans.Stats._

import scala.swing._

object Stats {
  def apply(ds: DataSet): Component = {
    val data: Seq[Seq[Double]] = ds.toVectorSeq
    var out = "" // Mutable, accumulates text.
    // Use the dataArray, since it carries variables already.
    val len = data(0).length
    for (i <- 0 until len) {
      val X = data.foldLeft(Array[Double]()) { case (acc, x) => acc :+ x(i) }

      out += s"Variable ${i + 1} (moyenne): ${average(X)}\n"
      out += s"Variable ${i + 1} (variance): ${variance(X)}\n"
      out += s"Variable ${i + 1} (ecartType): ${ecartType(X)}\n"

      if (i != 3) {
        val Y = data.foldLeft(Array[Double]()) { case (acc, x) => acc :+ x(i + 1) }
        out += s"Variable ${i + 1} et ${i + 2}: ${correlation(X, Y)}\n"
      }
    }
    println(s"out, $out")

    new FlowPanel {
      new TextArea(out)
    }
  }
}
