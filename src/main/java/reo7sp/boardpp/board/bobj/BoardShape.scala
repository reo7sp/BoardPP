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
import reo7sp.boardpp.ui.{Renderer, GraphicsObject}
import reo7sp.boardpp.board.BoardObject
import reo7sp.boardpp.util.Colors
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType

/**
 * Created by reo7sp on 12/30/13 at 2:07 PM
 */
case class BoardShape(id: Int) extends BoardObject with GraphicsObject {
  val toolID: Int = Tools.rect
  var w, h = 100
  var color1 = Color.RED
  var color2 = Color.BLUE

  def render(): Unit = {
    Renderer.batch.end()
    Renderer.shape.begin(ShapeType.Filled)

    Renderer.shape.rect(x, y, w, h, color1, color1, color2, color2)

    Renderer.shape.end()
    Renderer.batch.begin()
  }

  def update(): Unit = ()

  def toArray: Array[String] = Array(x.toString, y.toString, w.toString, h.toString, Colors.toString(color1), Colors.toString(color2))

  def toGraphicsObject: GraphicsObject = this
}
