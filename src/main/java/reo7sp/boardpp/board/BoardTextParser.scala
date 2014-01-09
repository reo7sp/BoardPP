package reo7sp.boardpp.board

import scala.collection.mutable.ArrayBuffer
import org.newdawn.slick.{Color, Graphics}
import reo7sp.boardpp.util.FontFactory

/**
 * Created by reo7sp on 12/26/13 at 9:52 PM
 */
case class BoardTextParser(s: String) {
  private[this] val modifiers = new ArrayBuffer[Modifier]
  private[this] var pos = -1
  var size = 16
  var bold = false
  var italic = false
  var color = 0
  var view = 0
  var char: Char = 0

  findModifiers()

  private[this] def findModifiers(): Unit = {
    var modifier = ""
    var begin = -1
    var backslashMode = false
    var i = 0
    for (ch <- s) {
      if (ch == '\\') backslashMode = true
      else if (ch == '$') {
        if (!backslashMode) {
          if (begin == -1) {
            begin = i
          } else {
            if (!modifier.isEmpty) modifiers += Modifier(modifier(0), modifier.substring(1), begin, i)
            modifier = ""
            begin = -1
          }
        }
      } else if (begin != -1) {
        modifier += ch
      }
      if (ch != '\\') backslashMode = false
      i += 1
    }
  }

  def move(i: Int): Boolean = {
    pos += i
    if (s.length <= pos) return false
    var modifierEnd = -1
    for (modifier <- modifiers; if modifier.begin <= pos && pos <= modifier.end) {
      modifierEnd = modifier.end
      try modifier.action match {
        case 's' => if (!modifier.param.isEmpty) size = modifier.param.toInt
        case 'b' => bold = if (modifier.param.isEmpty) true else modifier.param.toInt != 0
        case 'i' => italic = if (modifier.param.isEmpty) true else modifier.param.toInt != 0
        case 'c' => if (!modifier.param.isEmpty) color = Integer.parseInt(modifier.param, 16)
        case 'v' => if (!modifier.param.isEmpty) view = modifier.param.toInt
        case 'r' => modifier.param match {
          case "s" => size = 16
          case "b" => bold = false
          case "i" => italic = false
          case "c" => color = 0
          case "v" => view = 0
          case _ => resetCurModifiers()
        }
        case _ =>
      } catch {
        case _: Throwable =>
      }
    }
    if (modifierEnd != -1) move(modifierEnd - pos + 1)
    else {
      if (s.length <= pos) return false
      char = s(pos)
      true
    }
  }

  def move(): Boolean = move(1)

  def reset() {
    pos = -1
    resetCurModifiers()
  }

  private[this] def resetCurModifiers() {
    size = 16
    bold = false
    italic = false
    color = 0
    view = 0
  }

  case class Modifier(action: Char, param: String, begin: Int, end: Int)

}

object BoardTextParser {
  def draw(g: Graphics, x: Int, y: Int, parser: BoardTextParser) {
    var textX = x
    var size = -1
    var bold = false
    var italic = false
    var view = 0
    var color = -1

    parser.reset()
    while (parser.move()) {
      val c = parser.char.toString
      if (bold != parser.bold) {
        bold = parser.bold
        g.setFont(FontFactory.newFont(if (view == 0) size else size / 2, bold, italic))
      }
      if (italic != parser.italic) {
        italic = parser.italic
        g.setFont(FontFactory.newFont(if (view == 0) size else size / 2, bold, italic))
      }
      if (size != parser.size) {
        size = parser.size
        g.setFont(FontFactory.newFont(if (view == 0) size else size / 2, bold, italic))
      }
      if (view != parser.view) {
        view = parser.view
        g.setFont(FontFactory.newFont(if (view == 0) size else size / 2, bold, italic))
      }
      if (color != parser.color) {
        color = parser.color
        g.setColor(new Color(color))
      }
      g.drawString(c, textX, y - g.getFont.getHeight(c) - (if (view == 1) size / 2 else 0))
      textX += g.getFont.getWidth(c)
    }
  }
}
