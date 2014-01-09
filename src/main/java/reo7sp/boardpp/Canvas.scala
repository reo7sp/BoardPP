package reo7sp.boardpp

import reo7sp.boardpp.board.Board
import org.newdawn.slick.Color

/**
 * Created by reo7sp on 12/1/13 at 8:57 PM
 */
object Canvas {
  var board: Board = null
  var editing = false
  private[this] var _color = Color.black
  private[this] var _thickness = 2
  private[this] var _tool = Tools.cursor

  def color = _color

  def color_=(p1: Color) {
    _color = p1
    Canvas.board.curPage.updateSettings()
  }

  def thickness = _thickness

  def thickness_=(p1: Int) {
    _thickness = p1
    Canvas.board.curPage.updateSettings()
  }

  def tool = _tool

  def tool_=(p1: Int) {
    _tool = p1
    Canvas.board.curPage.updateSettings()
  }
}
