package reo7sp.boardpp.board.bobj

import reo7sp.boardpp.Tools
import reo7sp.boardpp.ui.GraphicsObject
import reo7sp.boardpp.board.{BoardObject, BoardTextParser}
import org.newdawn.slick.Graphics

/**
 * Created by reo7sp on 12/6/13 at 9:12 PM
 */
case class BoardText(id: Int) extends BoardObject with GraphicsObject {
  val toolID = Tools.text
  private[this] var _content = "Надпись"
  private[this] var parser = new BoardTextParser(_content)

  def render(g: Graphics): Unit = BoardTextParser.draw(g, x + 4, y + 24, parser)

  def update(): Unit = ()

  def toArray: Array[String] = Array(x.toString, y.toString, _content)

  def toGraphicsObject: GraphicsObject = this

  def content = _content

  def content_=(p1: String): Unit = {
    _content = p1
    parser = new BoardTextParser(_content)
  }
}
