import kmeans.kmeans.DataSet
import kmeans.{Cluster, Data, KMeans, Reader, Stats}
import java.awt.Color

import javax.swing.{JFrame, JTabbedPane, JTextArea}
import smile.plot._

object Main extends App {
  println("[+] Testing: Data"); {
    val data: Data = Data(6.3,3.3,6.0,2.5,Some("Iris-virginica"))
    println(s"Data point = $data")
  }
  println("[+] Testing: DataSet"); {
    val d1: Data = Data(6.3,3.3,6.0,2.5,Some("Iris-virginica"))
    val d2: Data = Data(7.0,3.2,4.7,1.4,Some("Iris-versicolor"))
    val ds: DataSet = Seq(d1, d2)
    println(s"DataSet = $ds")
  }
  println("[+] Testing: Cluster"); {
    val d1: Data = Data(6.3,3.3,6.0,2.5,Some("Iris-virginica"))
    val d2: Data = Data(7.0,3.2,4.7,1.4,Some("Iris-versicolor"))
    val ds: DataSet = Seq(d1, d2)
    val centroid: Data = Data(6.84,3.08,5.76,2.08,None)
    val cluster: Cluster = Cluster(centroid, ds)
    println(s"Cluster with centroid and dataset = $cluster")
  }
  println("[+] Testing: Reader.toData"); {
    val data : DataSet = Reader.toData("iris.data")
    println(s"First line of data = ${data(0)}")
  }
  println("[+] Testing: KMeans.dist"); {
    val v1: Data = Data(1,2,3,4,None)
    val v2: Data = Data(1,3,3,4,None)
    println(s"Distance $v1 and $v2 = ${KMeans.dist(v1,v2)}")
    assert(KMeans.dist(v1,v2) == 1)
  }
  println("[+] Testing: Stats.average"); {
    val v1: Data = Data(1,2,3,4,None)
    val v2: Data = Data(1,3,3,4,None)
    val ds: DataSet = Seq(v1,v2)
    println(s"Average $v1 and $v2 = ${Stats.averageVector(ds)}")
    assert(Stats.averageVector(ds) == Data(1, 2.5, 3, 4))
  }
  println("[+] Testing: Stats.variance"); {
    val v1: Data = Data(1,2,3,4,None)
    val v2: Data = Data(2,3,3,4,None)
    val v3: Data = Data(3,3,3,4,None)
    val x1 = v1.sepalLength
    val x2 = v2.sepalLength
    val x3 = v3.sepalLength
    val list = Seq(x1,x2,x3)
    println(s"Average $x1 and $x2 and $x3 = ${Stats.variance(list)}")
  }
  println("[+] Testing: Stats.ecartType"); {
    val v1: Data = Data(1,2,3,4,None)
    val v2: Data = Data(4,3,3,4,None)
    val x1 = v1.sepalLength
    val x2 = v2.sepalLength
    val list = Seq(x1,x2)
    println(s"Average $x1 and $x2 = ${Stats.ecartType(list)}")
  }
  println("[+] Testing: KMeans.partition"); {
    val cen1: Data = Data(1,2,3,4,None)
    val c1: Cluster = Cluster(cen1, Seq())
    val cen2: Data = Data(5,6,7,8,None)
    val c2: Cluster = Cluster(cen2, Seq())
    val clusters : Seq[Cluster] = Seq(c1,c2)
    val dataToPartition: Data = Data(2,3,4,5)
    println(s"Clusters: $clusters")
    val newClusters = KMeans.partition(clusters, dataToPartition)
    println(s"Partitionning data = $newClusters")
    assert(newClusters(0).dataset(0) == dataToPartition) // Should go into Cluster 1.
  }
  println("[*] Tests finished")

  // Obtain data and execute K-Means.
  val dataset : DataSet = Reader.toData("iris.data")
  val result : Seq[Cluster] = KMeans(dataset, 3)

/* Ugly.
  val clusters: Array[Array[Double]] = result.zipWithIndex
    .flatMap({case (cluster, i) => Seq(cluster.dataset
      .map({case Data(sl,sw,pl,_,_) => Array(sl, sw, pl, i)}))
    }).flatten.toArray */

  // List of tuples: List[ (data, clusterID) ]
  val dataAndClusterId: Seq[(Data, Int)] =
    result.zipWithIndex.flatMap{ // We have a Seq of Seq[(Data, Int)], collapse with flatMap.
      // For each cluster, get each DataSet (a Seq[Data])...
      case (cluster, i) => cluster.dataset
        // and transform it into Seq[(Data, Int) where Int is the cluster id.
          .map{(_, i)}
    }

  // Transform an Arbitrary data to a Seq[Double].
  // Data(1,2,3,4,5) => Seq(1,2,3).
  // Note that the data has four dimensions, and we can only keep three.
  def to3D(a: Data): Seq[Double] =
    a match {case Data(sl, sw, pl, _, _) => Seq(sl, sw, pl)}

  // Separate data and labels.
  // We need two separate lists for Smile: one with the data, and one with the ids (where each data belongs.)
  val dataSeq : Seq[Seq[Double]] = dataAndClusterId.map{case (data, _) => to3D(data)}
  val labelSeq : Seq[Int] = dataAndClusterId.map{case (_, clusterID) => clusterID}

  // Convert to Arrays.
  val dataArray : Array[Array[Double]] =
    dataSeq.map(_.toArray).toArray
  val labelArray : Array[Int] = labelSeq.toArray

  // We need to get centroids in a Java array, too.
  val centroidSeq: Seq[Seq[Double]] = result.map( _.centroid ).map(to3D)
  val centroidArray: Array[Array[Double]] =
    centroidSeq.map(_.toArray).toArray

  // First canvas: expected data
  val canvas = swing.plot(dataArray, labelArray, Array('*', '+', 'o'), Array(Color.RED, Color.BLUE, Color.CYAN))
  canvas.setAxisLabels("sepalLength", "sepalWidth", "petalLength")
  canvas.setTitle("K-Means of iris.data")
  canvas.points(centroidArray, '*', Color.GREEN)

  // Second canvas: actual data
  val labelToInt = Map(
    "Iris-setosa" -> 0,
    "Iris-versicolor" -> 1,
    "Iris-virginica" -> 2,
  )
  // Convert the Strings to real data.
  // We should do this in the data gathering part...
  val realLabels: Array[Int] = result
    .flatMap(_.dataset)
    .flatMap(_.actualClass) // FlatMap on an Option[String] => String.
    .map(labelToInt)
    .toArray

  val canvas2 = swing.plot(dataArray, realLabels, Array('*', '+', 'o'), Array(Color.RED, Color.BLUE, Color.CYAN))
  canvas2.setAxisLabels("sepalLength", "sepalWidth", "petalLength")
  canvas2.setTitle("Real classes of iris.data")

  // Output for each variable.

  val dataFullSeq : Seq[Seq[Double]] = dataAndClusterId.map{case (data, _) => data.toSeq}
  val dataFullArray : Array[Array[Double]] =
    dataFullSeq.map(_.toArray).toArray
  var out = "" // Mutable, accumulates text.
  // Use the dataArray, since it carries variables already.
  val len = dataFullArray(0).length
  for (i <- 0 until len) {
    val X = dataFullArray.foldLeft(Array[Double]())
    {case (acc, x) => acc :+ x(i)}

    out += s"Variable ${i+1} (moyenne): ${Stats.average(X)}\n"
    out += s"Variable ${i+1} (variance): ${Stats.variance(X)}\n"
    out += s"Variable ${i+1} (ecartType): ${Stats.ecartType(X)}\n"

    if (i != 3) {
      val Y = dataFullArray.foldLeft(Array[Double]())
      {case (acc, x) => acc :+ x(i+1)}
      out += s"Variable ${i+1} et ${i+2}: ${Stats.correlation(X, Y)}\n"
    }
  }
  println(s"out, $out")

  val statAnalysis = new JTextArea(out)

  // GUI.
  val tabs = new JTabbedPane();
  tabs.addTab("KMeans", canvas)
  tabs.addTab("Real data", canvas2)
  tabs.addTab("Statistical analysis", statAnalysis)
  tabs.setVisible(true)

  val main = new JFrame()
  main.add(tabs)
  main.setSize(800,600);
  main.setVisible(true)
  /*
  show(canvas)
  show(canvas2)
  */
}
