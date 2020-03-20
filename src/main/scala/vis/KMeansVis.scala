package vis

import java.awt.Color

import javax.swing.{JComponent, JFrame, JTabbedPane, JTextArea}
import kmeans.{Cluster, Data, DataSet, KMeans, Reader}
import smile.data.Dataset
import smile.plot._
import smile.plot.swing.PlotCanvas

import scala.swing.TabbedPane.Page
import scala.swing._

object KMeansVis {
  def apply(dataset: DataSet, columnsIDs: Seq[Int]): BoxPanel =
    new BoxPanel(Orientation.Vertical) {
      var kmeansComponent = kmeansVis(dataset, columnsIDs, "K-Means on Iris dataset")
      kmeansComponent.minimumSize = new Dimension(300,300)
      println(kmeansComponent)
      contents += new Button("Click me") {
        reactions += {
          case event.ButtonClicked(_) =>
            contents.remove(1)
            contents += kmeansVis(dataset, Seq(0,1), "K-Means on Iris dataset")
        }
      }
      contents += kmeansComponent
   }

  def kmeansVis(dataset: DataSet, columnsIDs: Seq[Int], title: String): BoxPanel = {
    val result: Seq[Cluster] = KMeans(dataset, 3)

    val names: Seq[String] = dataset.namedSlice(columnsIDs)
    val columns: DataSet = dataset.columnSlice(columnsIDs)
    // Let's go in the Java world: convert to Arrays.
    val columnsArray: Array[Array[Double]] = columns.toArray
    val canvas: PlotCanvas = swing.plot( columnsArray )
    canvas.clear()

    // Get the labels as an array: [1,1,1,2,2,3...]
    val labels: Seq[Int] = result.zipWithIndex
      .flatMap {
        case (cluster, i) => Array.fill(cluster.length)(i)
      }
    val data: Seq[Data] = result.flatMap{
      _.dataset.data
    }
    val legends = Array('*', '+', 'o')
    val colors = Array(Color.RED, Color.BLUE, Color.ORANGE)
    (data zip labels).foreach{
      case (data, i) => println(data, i);
        canvas.point(legends(data.realClassAsInt), colors(i), data.toSeq: _*)
    }

    // https://alvinalexander.com/bookmarks/scala/scalas-missing-splat-operator
    // The function is variadic, so splat the list.
    canvas.setAxisLabels(names : _*)
    canvas.setTitle(title)

    val panel = new BoxPanel(Orientation.Vertical)
    panel.peer.add(canvas.getToolbar)
    panel.peer.add(canvas)
    panel
  }
}
