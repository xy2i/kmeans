package vis

import kmeans.DataSet
import kmeans.Stats._

import scala.swing._

object Stats {
  def apply(ds: DataSet): Component = {
    var out = "" // Mutable, accumulates text.
    // Use the dataArray, since it carries variables already.
    val variables = 0 to ds.dim - 1
    variables.foreach{ case i =>
      val X = ds.column(i).toSeq
      println(X)

      out += s"Variable ${i + 1} (moyenne): ${average(X)}\n"
      out += s"Variable ${i + 1} (variance): ${variance(X)}\n"
      out += s"Variable ${i + 1} (ecartType): ${ecartType(X)}\n"
/*
      if (i != 3) {
        val Y = data.foldLeft(Array[Double]()) { case (acc, x) => acc :+ x(i + 1) }
        out += s"Variable ${i + 1} et ${i + 2}: ${correlation(X, Y)}\n"
      }*/
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
