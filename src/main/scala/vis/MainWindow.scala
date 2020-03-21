package vis

import javax.swing.WindowConstants
import kmeans.{DataSet, Reader}

import scala.swing.{Action, Component, Frame, SimpleSwingApplication, TabbedPane, UIElement}
import scala.swing.TabbedPane.Page

object MainWindow {
  // Obtain data and execute K-Means.
  val dataset: DataSet = Reader.toData("iris.data")
  val columnIDs = 0 to 2
  val kmeansComponent: Component = KMeansVis(dataset, columnIDs)
  val statsComponent: Component = Stats(dataset)

  def top: Frame = new Frame {
    title = "K-Means from scratch"
    this.peer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

    contents = new TabbedPane {
      pages += new Page("K-Means", kmeansComponent)
      pages += new Page("Statistics", statsComponent)
    }
    pack()
    centerOnScreen()
    open()
    override def closeOperation() {
      top.dispose()
      super.closeOperation()
    }
  }
}
