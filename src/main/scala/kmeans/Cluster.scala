package kmeans

/**
 * A Cluster consists of a centroid (the "center" of the cluster)
 * and a list of data points - a [[DataSet]] - associated to the cluster.
 * @param centroid A [[Data]] that represents the "center" of the cluster.
 * @param dataset Our data points, as a [[DataSet]].
 */
case class Cluster(
                  // Centroid for this cluster.
                  centroid: Data,
                  // Data associated with the cluster.
                  dataset: DataSet
                  ) {
  /**
   * Returns the number of Datas in the Dataset.
   * @return The numbers of Datas in the dataset.
   */
  def length: Int = dataset.length
}
