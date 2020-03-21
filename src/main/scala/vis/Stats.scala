package vis

import kmeans.DataSet
import kmeans.Stats._

import scala.swing._

/**
 * The stats visualisation.
 */
object Stats {
  def apply(ds: DataSet): Component = {
    var out = "" // Mutable, accumulates text.
    // Use the dataArray, since it carries variables already.
    val variables = 0 until ds.dim
    variables.foreach { i =>
      val X = ds.column(i).toSeq

      out += s"Variable ${i + 1} (moyenne): ${average(X)}\n"
      out += s"Variable ${i + 1} (variance): ${variance(X)}\n"
      out += s"Variable ${i + 1} (ecartType): ${ecartType(X)}\n"
    }
    // Get all combinations of two variables, and invoke permutations on each of them.
    variables.combinations(2).foreach{ case Seq(i, j) =>
      val X = ds.column(i).toSeq
      val Y = ds.column(j).toSeq
      out += s"Variable ${i + 1} et ${j + 1} (correlation): ${correlation(X, Y)}\n"
    }
    new TextArea(out)
  }
}
