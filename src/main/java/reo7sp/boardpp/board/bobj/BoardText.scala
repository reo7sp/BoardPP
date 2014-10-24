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

package reo7sp.boardpp.board.bobj

import reo7sp.boardpp.Tools
import reo7sp.boardpp.ui.GraphicsObject
import reo7sp.boardpp.board.{BoardObject, BoardTextParser}

/**
 * Created by reo7sp on 12/6/13 at 9:12 PM
 */
case class BoardText(id: Int) extends BoardObject with GraphicsObject {
  val toolID = Tools.text
  private[this] var _content = "Надпись"
  private[this] var parser = new BoardTextParser(_content)

  def render(): Unit = BoardTextParser.draw(x + 4, y + 24, parser)

  def update(): Unit = ()

  def toArray: Array[String] = Array(x.toString, y.toString, _content)

  def toGraphicsObject: GraphicsObject = this

  def content = _content

  def content_=(p1: String): Unit = {
    _content = p1
    parser = new BoardTextParser(_content)
  }
}
