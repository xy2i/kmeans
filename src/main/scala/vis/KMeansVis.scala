package vis

import java.awt.Color

import javax.swing.{JComponent, JFrame, JTabbedPane, JTextArea}
import kmeans.{Cluster, Data, DataSet, KMeans, Reader}
import smile.data.Dataset
import smile.plot._
import smile.plot.swing.PlotCanvas

import scala.swing.TabbedPane.Page
import scala.swing._
import scala.swing.event.ButtonClicked

object KMeansVis {
  def apply(dataset: DataSet, columnsIDs: Seq[Int]): BoxPanel =
    new BoxPanel(Orientation.Vertical) {
      // Generate the initial K-Means visu.
      var kmeansComponent = kmeansVis(dataset, columnsIDs)
      // A list of selected boxes that will be used for future usage.
      var list = columnsIDs

      kmeansComponent.minimumSize = new Dimension(300,300)

      val sepalLength = new CheckBox("Sepal length")
      val sepalWidth = new CheckBox("Sepal width")
      val petalLength = new CheckBox("Petal length")
      val petalWidth = new CheckBox("Petal width")
      val variables = Seq(sepalLength, sepalWidth, petalLength, petalWidth)
      val refreshButton = new Button("Run again")
      val warningLabel = new Label("")

      list.map{ i => variables(i).selected = true } // Make sure the checkboxes match the initial repr.

      contents += new BoxPanel(Orientation.Vertical) {
        contents += new FlowPanel {
          contents ++= variables
          contents += refreshButton
        }
        contents += new FlowPanel {
          contents += warningLabel
        }
      }
      listenTo(variables: _*) // Listen to all the checkboxes.
      listenTo(refreshButton)
      reactions += {
        // General handler for both checkboxes and buttons.
        case ButtonClicked(_) => {
          // How many boxes are checked?
          // We get a list of the checked IDs, corresponding to the variable's columns:
          // [0,1,3] means to take the first, third and fourth variable,
          // or the first, third and fourth column.
          list = variables.zipWithIndex
            .filter{
              case (box, _) => box.selected
            }.map{
            case (_, i) => i
            }

          // Check if we are not in more than 3D. If so, tell the user.
          if (list.length > 3 || list.length <= 1) {
            warningLabel.text = "You must choose 2 or 3 variables."
          } else {
            // Remove the warning text if it was set.
            warningLabel.text = ""
            // Remove the KMeans and generate a new one.
            contents.remove(1)
            contents += kmeansVis(dataset, list)
          }
        }
      }
      contents += kmeansComponent
   }

  def kmeansVis(dataset: DataSet, columnsIDs: Seq[Int]): BoxPanel = {
    val result: Seq[Cluster] = KMeans(dataset.columnSlice(columnsIDs), 3)

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
    val centroids: Seq[Data] = result.map{
      _.centroid
    }

    val legends = Array('*', 'O', 'S')
    val colors = Array(Color.RED, Color.BLUE, Color.ORANGE)
    /* In our visu, each color will correspond to a label by K-Means,
    while each shape will correspond to the real classes of each Data.
     */
    (data zip labels).foreach{
      case (data, i) =>
        canvas.point(legends(data.realClassAsInt), colors(i), data.toSeq: _*)
    }
    // Plot the centroids.
    centroids.foreach{
      case data =>
        canvas.point('Q', Color.GREEN, data.toSeq: _*)
    }

    // https://alvinalexander.com/bookmarks/scala/scalas-missing-splat-operator
    // The function is variadic, so splat the list.
    canvas.setAxisLabels(names : _*)

    val prettyVariablesNames = names.mkString(", ")
    canvas.setTitle("K-Means on Iris dataset, variables chosen: " + prettyVariablesNames)

    val panel = new BoxPanel(Orientation.Vertical)
    panel.peer.add(canvas.getToolbar)
    panel.peer.add(canvas)
    panel
  }
}
