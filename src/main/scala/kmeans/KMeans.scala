package kmeans

import kmeans.DataSet
import Stats.averageVector
import scala.util.Random

class KMeans {}

/**
 * The main KMeans algorithm, and associated methods.
 *
 * {{{
 * val dataset : DataSet = Reader.toData("iris.data")
 * val result : Seq[Cluster] = KMeans(dataset, 3)
 * }}}
 *
 * Given arbitrary Data and a k to partition, KMeans will
 * create k clusters.
 */
object KMeans {
  /**
   * Given two Data, calculate the distance.
   * @param xs The first vector.
   * @param ys The second vector.
   * @return A vector with the distance between the two.
   */
  def dist(xs: Data, ys: Data): Double =
    (xs.toSeq zip ys.toSeq).map({ case (x, y) => math.pow(y - x, 2) }).sum

  /**
   * Chooses initial centroids.
   * @param xss The DataSet from which to choose centroids.
   * @return A single random centroid.
   */
  def randCentroid(xss: DataSet) =
    xss(Random.nextInt(xss.length))

  /**
   * Place a Data into one of the Clusters.
   * @param clusters The list of clusters to put the data into.
   * @param d The data to partition.
   * @return A new list of Clusters with the Data put in one of the clusters.
   */
  def partition(clusters: Seq[Cluster], d: Data): Seq[Cluster] = {
    // Get the cluster with the closest centroid.
    val indexDMin = clusters.map({ case Cluster(c, _) => dist(c, d) })
      .zipWithIndex.min._2 // Get the index of the minimum value.

    // Place into the new cluster.
    val newCluster: Cluster =
      clusters(indexDMin) match { case Cluster(c, ds) => Cluster(c, ds :+ d) }

    // Return the updated list of clusters.
    clusters.patch(indexDMin, Seq(newCluster), 1)
  }

  /**
   * Computes the new centroid of the cluster.
   * @param cluster The cluster to compute the new centroid of.
   * @return A new cluster with an updated centroid.
   */
  def newCentroid(cluster: Cluster): Cluster = {
    val dataOfCluster = cluster match {case Cluster(_, ds) => ds}
    val newCentroid = averageVector(dataOfCluster)

    cluster match {case Cluster(_, ds) => Cluster(newCentroid, ds)}
  }

  /**
   * The main KMeans algorithm.
   *
   * {{{
   * val dataset : DataSet = Reader.toData("iris.data")
   * val result : Seq[Cluster] = KMeans(dataset, 3)
   * }}}
   *
   * Given a dataset, calculate KMeans and return a list of clusters, each
   * with centroid and data. This result can be further used to visualise or
   * see each data.
   *
   * @param ds The DataSet to evalutate.
   * @param k The number of clusters to return.
   * @return A list of K clusters.
   */
  def apply(ds: DataSet, k: Int): Seq[Cluster] = {
    @annotation.tailrec
    def iterate(ds: DataSet, clusters: Seq[Cluster], i: Int): Seq[Cluster] = {
      // Empty clusters, bwith the centroids of the old clusters.
      val centroids: Seq[Data] = clusters.map(_.centroid)

      val emptyClusters: Seq[Cluster] = centroids
        .foldLeft[Seq[Cluster]](Seq()) (_ :+ Cluster(_, Seq()))

      // For each vector of our dataset, partition into a cluster.
      val sortedClusters: Seq[Cluster] = ds
        .foldLeft(emptyClusters) (partition(_, _))

      // Compute a new centroid for each cluster.
      val newClusters: Seq[Cluster] = sortedClusters
        .map(newCentroid)

      newClusters.map({case Cluster(centroid, _) => println(centroid)})

      // Check if the centroids are the same as the old ones. Must converge eventually.
      val oldAndNewCentroids = (newClusters.map(_.centroid) zip clusters.map(_.centroid))
      val finish = oldAndNewCentroids // Each and every centroid must be equal to the old one.
        .foldLeft(true) {case (acc, (oldC, newC)) => acc && (oldC == newC)}
      if (finish)
        newClusters
      else
        iterate(ds, newClusters, i+1)
    }

    // Start with some random centroids.
    val centroids: Seq[Data] = Random.shuffle(ds).take(k)
    val emptyClusters: Seq[Cluster] = centroids
      .foldLeft[Seq[Cluster]](Seq()) (_ :+ Cluster(_, Seq()))

    // Iterate until options are exhausted.
    iterate(ds, emptyClusters, 0)
  }
}
