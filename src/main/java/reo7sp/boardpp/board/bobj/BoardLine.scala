package reo7sp.boardpp.board.bobj

import reo7sp.boardpp.Tools
import reo7sp.boardpp.ui.GraphicsObject
import reo7sp.boardpp.board.BoardObject
import org.newdawn.slick.{Color, Graphics}
import reo7sp.boardpp.util.Colors

/**
 * Created by reo7sp on 12/6/13 at 9:12 PM
 */
case class BoardLine(id: Int) extends BoardObject with GraphicsObject {
  val toolID = Tools.line
  var x2, y2 = 0
  var color = Color.black
  var thickness = 1F

  def render(g: Graphics): Unit = {
    g.setColor(color)
    g.setLineWidth(thickness)
    g.drawLine(x, y, x2, y2)
    g.setLineWidth(1)
  }

  def update(): Unit = ()

  def toArray: Array[String] = Array(x.toString, y.toString, x2.toString, y2.toString, Colors.toStr(color), thickness.toString)

  def toGraphicsObject: GraphicsObject = this
}
