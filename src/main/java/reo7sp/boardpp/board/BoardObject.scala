package reo7sp.boardpp.board

import reo7sp.boardpp.ui.GraphicsObject
import java.net.URL
import reo7sp.boardpp.Tools
import reo7sp.boardpp.board.bobj.{BoardShape, BoardLine, BoardImage, BoardText}
import reo7sp.boardpp.util.Colors

/**
 * Created by reo7sp on 12/4/13 at 10:41 PM
 */
trait BoardObject {
  var x, y = 0

  val id: Int

  val toolID: Int

  def toArray: Array[String]

  def toGraphicsObject: GraphicsObject
}

object BoardObject {
  def newInstance(id: Int, toolID: Int, data: Array[String]): BoardObject = toolID match {
    case Tools.text =>
      val bobj = new BoardText(id)
      bobj.x = data(0).toInt
      bobj.y = data(1).toInt
      bobj.content = data(2)
      bobj

    case Tools.image =>
      val bobj = new BoardImage(id)
      bobj.x = data(0).toInt
      bobj.y = data(1).toInt
      bobj.w = data(2).toInt
      bobj.h = data(3).toInt
      bobj.url = try {
        new URL(data(4))
      } catch {
        case _: Throwable => null
      }
      bobj

    case Tools.`rect` =>
      val bobj = new BoardShape(id)
      bobj.x = data(0).toInt
      bobj.y = data(1).toInt
      bobj.w = data(2).toInt
      bobj.h = data(3).toInt
      bobj.color1 = Colors.toGdx(data(4))
      bobj.color2 = Colors.toGdx(data(5))
      bobj

    case Tools.line =>
      val bobj = new BoardLine(id)
      bobj.x = data(0).toInt
      bobj.y = data(1).toInt
      bobj.x2 = data(2).toInt
      bobj.y2 = data(3).toInt
      bobj.color = Colors.toGdx(data(4))
      bobj.thickness = data(5).toFloat
      bobj
  }
}
