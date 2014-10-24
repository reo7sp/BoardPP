/*
 Copyright 2014 Reo_SP

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
*/

package reo7sp.boardpp.board

import scala.collection.mutable.ArrayBuffer
import reo7sp.boardpp.util.{Colors, FontFactory}
import reo7sp.boardpp.ui.Renderer
import com.badlogic.gdx.graphics.Color

/**
 * Created by reo7sp on 12/26/13 at 9:52 PM
 */
case class BoardTextParser(s: String) {
  private[this] val modifiers = new ArrayBuffer[Modifier]
  private[this] var pos = -1
  var size = 14
  var bold = false
  var italic = false
  var color = Color.BLACK
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
    def fixColorStr(str: String) = {
      var result = str
      while (result.length < 6) {
        result += "0"
      }
      result + "ff"
    }

    pos += i
    if (s.length <= pos) return false
    var modifierEnd = -1
    for (modifier <- modifiers; if modifier.begin <= pos && pos <= modifier.end) {
      modifierEnd = modifier.end
      try modifier.action match {
        case 's' => if (!modifier.param.isEmpty) size = modifier.param.toInt
        case 'b' => bold = if (modifier.param.isEmpty) true else modifier.param.toInt != 0
        case 'i' => italic = if (modifier.param.isEmpty) true else modifier.param.toInt != 0
        case 'c' => if (!modifier.param.isEmpty) color = Colors.toGdx(fixColorStr(modifier.param))
        case 'v' => if (!modifier.param.isEmpty) view = modifier.param.toInt
        case 'r' => modifier.param match {
          case "s" => size = 16
          case "b" => bold = false
          case "i" => italic = false
          case "c" => color = Color.BLACK
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
    color = Color.BLACK
    view = 0
  }

  case class Modifier(action: Char, param: String, begin: Int, end: Int)

}

object BoardTextParser {
  def draw(x: Int, y: Int, parser: BoardTextParser) {
    var font = Renderer.font
    var textX = x
    var size = 12
    var bold = false
    var italic = false
    var view = 0
    var color: Color = null

    parser.reset()
    while (parser.move()) {
      val c = parser.char
      var newFont = false

      if (bold != parser.bold) {
        bold = parser.bold
        newFont = true
      }
      if (italic != parser.italic) {
        italic = parser.italic
        newFont = true
      }
      if (size != parser.size) {
        size = parser.size
        newFont = true
      }
      if (view != parser.view) {
        view = parser.view
        newFont = true
      }
      if (newFont) {
        font = FontFactory.newFont(if (view == 0) size else size / 2, bold, italic)
      }
      if (color != parser.color || newFont) {
        color = parser.color
        font.setColor(new Color(color))
      }

      val glyph = font.getData.getGlyph(c)
      if (glyph != null) {
        font.draw(Renderer.batch, c.toString, textX, y + (if (view == 1) size / 2 else 0) + font.getData.getGlyph('A').height)
        textX += glyph.xadvance
      }
    }
  }
}
