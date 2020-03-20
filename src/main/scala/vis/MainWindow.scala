package vis

import kmeans.{DataSet, Reader}

import scala.swing.{Component, Frame, TabbedPane, UIElement}
import scala.swing.TabbedPane.Page

object MainWindow {
  // Obtain data and execute K-Means.
  val dataset: DataSet = Reader.toData("iris.data")
  val columnIDs = Seq(0,1,2)
  val kmeansComponent: Component = KMeansVis(dataset, columnIDs)
  val statsComponent: Component = Stats(dataset)

  def top: Frame = new Frame {
    title = "Hello world"

    contents = new TabbedPane {
      pages += new Page("K-Means", kmeansComponent)
      pages += new Page("Statistics", statsComponent)
    }
    pack()
    centerOnScreen()
    open()
  }
}
