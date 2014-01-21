package reo7sp.boardpp.ui.widgets.my

import scala.collection.mutable.ArrayBuffer

/**
 * Created by reo7sp on 1/7/14 at 2:12 PM
 */
class Container extends Component {
  val elements = new ArrayBuffer[Component]

  override def render(): Unit = {
    super.render()
    elements.foreach(_.render())
    elements.foreach(_.drawTooltip())
  }

  override def update(): Unit = elements.foreach(_.update())

  override def onMousePressed(): Unit = elements.foreach(_.onMousePressed())

  override def onMouseReleased(): Unit = elements.foreach(_.onMouseReleased())

  def +=(c: Component): Unit = {
    elements += c
    c.parent = this
  }

  def -=(c: Component): Unit = {
    elements -= c
    c.parent = null
  }
}
