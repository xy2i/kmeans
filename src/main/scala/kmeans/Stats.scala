package kmeans

import kmeans.DataSet

import scala.math.pow
import scala.math.sqrt

class Stats {}
object Stats {

  /**
   * Averages a list of data (a DataSet) and returns
   * a Data as its sum.
   * @param ds The list of Data.
   * @return The sum.
   */
  def averageVector(ds: DataSet): Data = {
    // Transform each element to a sequence.
    val vectors = ds.map(_.toSeq)
    val averageSeq = vectors.transpose.map(average)
    Data(averageSeq)
  }

  /**
   * Averages a list of values.
   * @param X The list of values to average.
   */
  def average(X: Seq[Double]) = {
    X.foldLeft( (0.0, 1) )
    { case ((avg, pos), next) => (avg + (next - avg)/pos, pos+1) }._1
  }

  /**
   * Computes the variance of a list of values.
   * @param X The list.
   * @return The variance of the list.
   */
  def variance(X: Seq[Double]): Double = {
    val avg = average(X)
    X.map{x => pow(x - avg, 2) }.sum / X.length
  }

  /**
   * Computes the ecart-type of a list of values.
   * @param X The list.
   * @return The ecart-type of the list.
   */
  def ecartType(X: Seq[Double]): Double = sqrt(variance(X))

  def covariance(X: Seq[Double], Y: Seq[Double]): Double = {
    val avgX = average(X)
    val avgY = average(Y)
    (X zip Y).map{
      case (x, y) => (x - avgX) * (y - avgY)
    }.sum
  }

  /**
   * Computes the correlation coefficient between two variables.
   * @param X The first variable.
   * @param Y The second variable.
   * @return The coefficient.
   */
  def correlation(X: Seq[Double], Y: Seq[Double]): Double = {
    covariance(X,Y) /
    ( sqrt(X.map{x => pow(x - average(X), 2) }.sum)
      * sqrt(Y.map{y => pow(y - average(Y), 2) }.sum)
      )
  }
}