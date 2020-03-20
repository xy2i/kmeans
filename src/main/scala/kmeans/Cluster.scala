package kmeans

/* A Cluster is a data point and a sequence of data:
 (centroid, list of points)
 */
case class Cluster(
                  // Centroid for this cluster.
                  centroid: Data,
                  // Data associated with the cluster.
                  dataset: DataSet
                  ) {
  def length = dataset.length
}
