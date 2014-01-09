package reo7sp.boardpp.board.bobj

import reo7sp.boardpp.Tools
import reo7sp.boardpp.ui.GraphicsObject
import reo7sp.boardpp.board.BoardObject
import org.newdawn.slick.{Graphics, Color}
import reo7sp.boardpp.util.Colors
import org.newdawn.slick.fills.GradientFill
import org.newdawn.slick.geom.{Rectangle, Shape}

/**
 * Created by reo7sp on 12/30/13 at 2:07 PM
 */
case class BoardShape(id: Int) extends BoardObject with GraphicsObject {
  val toolID: Int = Tools.shape
  var w, h = 100
  var color1 = Color.red
  var color2 = Color.blue
  private[this] var gradient: GradientFill = null
  private[this] var shape: Shape = null

  invalidateGradient()

  def render(g: Graphics): Unit = {
    g.fill(shape, gradient)
  }

  def update(): Unit = ()

  def toArray: Array[String] = Array(x.toString, y.toString, w.toString, h.toString, Colors.toStr(color1), Colors.toStr(color2))

  def toGraphicsObject: GraphicsObject = this

  def invalidateGradient(): Unit = {
    gradient = new GradientFill(x, y, color1, x, y + h, color2)
    shape = new Rectangle(x, y, w, h)
  }
}
