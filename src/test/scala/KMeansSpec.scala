import kmeans.{Cluster, Data, DataSet, KMeans, Stats}
import org.scalatest._

class KMeansSpec extends FlatSpec {
  val data: Data = Data(Seq(1.5,2.5,3.5,4.5), Option("Iris-setosa"))
  "A Data" should "convert to a Seq" in {
    val seq = data.toSeq
    assert(seq == Seq(1.5,2.5,3.5,4.5))
  }
  it should "convert to a Double if it has only one dimension" in {
    val secondColumn = data.column(1)
    val value = secondColumn.toDouble
    assert(value == 2.5)
  }
  it should "convert to a lesser dimension Data when needed" in {
    val reducedData = data.columnSlice(1 to 3)
    assert(reducedData == Data(Seq(2.5,3.5,4.5), Option("Iris-setosa"), 1 to 3))
  }
  it should "give its real class as an Int" in {
    assert(data.realClassAsInt == 0)
  }
  it should "have direct access" in {
    assert(data(0) == 1.5)
  }

  val ds: DataSet = kmeans.Reader.toData("test.data")
  "A DataSet" should "have a dimension" in {
    assert(ds.dim == 4)
  }
  it should "convert to a Seq[Double] if it has only one dimension" in {
    val reducedDs = ds.column(1)
    val seq = reducedDs.toSeq
    assert(seq == Seq(2.5, 2.8, 3.3))
  }
  it should "convert to a lesser dimension Dataset" in {
    val reducedDs = ds.columnSlice(1 to 2)
    val seq = reducedDs.toVectorSeq
    assert(seq(0) == Seq(2.5, 3.0))
  }
  it should "give a name given a column" in {
    val name = ds.namedColumn(2)
    assert(name == "Petal length")
  }
  it should "give a list of names given a column slice" in {
    val names = ds.namedSlice(2 to 3)
    assert(names == Seq("Petal length", "Petal width"))
  }
  it should "return a length of its elements" in {
    assert(ds.length == 3)
  }

  def truncateAt(n: Double, p: Int): Double = { val s = math pow (10, p); (math floor n * s) / s }

  "Stats" should "calculate averages" in {
    val v1 = Seq[Double](1,2,3,4)
    val v2 = Seq[Double](1,3,3,4)
    val list = Seq(v1,v2)
    assert(Stats.averageVector(list) == Seq(1, 2.5, 3, 4))
  }
  it should "calculate variance" in {
    val v1: Data = Data(Seq(1))
    val v2: Data = Data(Seq(2))
    val v3: Data = Data(Seq(3))
    val statsDs = DataSet(Seq(v1,v2,v3))
    val list = statsDs.column(0).toSeq
    assert(truncateAt(Stats.variance(list), 2) == 0.66)
  }
  it should "calculate ecartType" in {
    val v1: Data = Data(Seq(1))
    val v2: Data = Data(Seq(2))
    val v3: Data = Data(Seq(3))
    val statsDs = DataSet(Seq(v1,v2,v3))
    val list = statsDs.column(0).toSeq
    assert(truncateAt(Stats.ecartType(list), 2) == 0.81)
  }
  it should "calculate covariance" in {
    val v1: Data = Data(Seq(1,2  ,4))
    val v2: Data = Data(Seq(2,2.5,4))
    val v3: Data = Data(Seq(3,2.7,4))
    val ds = DataSet(Seq(v1,v2,v3))
    val X = ds.column(0).toSeq
    val Y = ds.column(1).toSeq
    val Z = ds.column(2).toSeq
    assert(truncateAt(Stats.covariance(X, Y), 5) == 0.7)
    assert(Stats.covariance(X, Z) == 0)
  }
  it should "calculate correlation" in {
    val v1: Data = Data(Seq(1,2  ,4))
    val v2: Data = Data(Seq(2,2.5,4))
    val v3: Data = Data(Seq(3,2.7,4))
    val ds = DataSet(Seq(v1,v2,v3))
    val X = ds.column(0).toSeq
    val Y = ds.column(1).toSeq
    val Z = ds.column(2).toSeq
    assert(truncateAt(Stats.correlation(X, Y), 4) == 0.9707)
  }

  "KMeans" should "be able to calculate distances" in {
    val v1: Data = Data(Seq(1.5,2.5,3.5,4.5))
    val v2: Data = Data(Seq(1.5,3.5,3.5,4.5))
    assert(KMeans.dist(v1,v2) == 1)
  }
  it should "partition data" in {
    val cen1: Data = Data(Seq(1,2,3,4))
    val c1: Cluster = Cluster(cen1, new DataSet(Seq()))
    val cen2: Data = Data(Seq(5,6,7,8))
    val c2: Cluster = Cluster(cen2, new DataSet(Seq()))
    val clusters : Seq[Cluster] = Seq(c1,c2)
    val dataToPartition: Data = Data(Seq(2,3,4,5))
    val newClusters = KMeans.partition(clusters, dataToPartition)
    assert(newClusters(0).dataset.data(0) == dataToPartition) // Should go into Cluster 1.
  }
}