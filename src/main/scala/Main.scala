import kmeans.{Cluster, Data, DataSet, KMeans, Reader, Stats}
import java.awt.Color

import javax.swing.{JFrame, JTabbedPane, JTextArea}
import smile.plot._
import vis.KMeansVis
import vis.MainWindow

object Main extends App {
  /*
  println("[+] Testing: Data"); {
    val data: Data = Data(6.3,3.3,6.0,2.5,Some("Iris-virginica"))
    println(s"Data point = $data")
  }
  println("[+] Testing: DataSet"); {
    val d1: Data = Data(6.3,3.3,6.0,2.5,Some("Iris-virginica"))
    val d2: Data = Data(7.0,3.2,4.7,1.4,Some("Iris-versicolor"))
    val ds: DataSet = new DataSet(Seq(d1, d2))
    println(s"DataSet = $ds")
  }
  println("[+] Testing: Cluster"); {
    val d1: Data = Data(6.3,3.3,6.0,2.5,Some("Iris-virginica"))
    val d2: Data = Data(7.0,3.2,4.7,1.4,Some("Iris-versicolor"))
    val ds: DataSet = new DataSet(Seq(d1, d2))
    val centroid: Data = Data(6.84,3.08,5.76,2.08,None)
    val cluster: Cluster = Cluster(centroid, ds)
    println(s"Cluster with centroid and dataset = $cluster")
  }
  println("[+] Testing: Reader.toData"); {
    val ds : DataSet = Reader.toData("iris.data")
    println(s"First line of data = ${ds.data(0)}")
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
    val ds: DataSet = new DataSet(Seq(v1,v2))
    println(s"Average $v1 and $v2 = ${Stats.averageVector(ds.toVectorSeq)}")
    assert(Stats.averageVector(ds.toVectorSeq) == Seq(1, 2.5, 3, 4))
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
    val c1: Cluster = Cluster(cen1, new DataSet(Seq()))
    val cen2: Data = Data(5,6,7,8,None)
    val c2: Cluster = Cluster(cen2, new DataSet(Seq()))
    val clusters : Seq[Cluster] = Seq(c1,c2)
    val dataToPartition: Data = Data(2,3,4,5)
    println(s"Clusters: $clusters")
    val newClusters = KMeans.partition(clusters, dataToPartition)
    println(s"Partitionning data = $newClusters")
    assert(newClusters(0).dataset.data(0) == dataToPartition) // Should go into Cluster 1.
  }
  println("[*] Tests finished")*/

  MainWindow.top
}
