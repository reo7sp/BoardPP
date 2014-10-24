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
import com.badlogic.gdx.Gdx

/**
 * Created by reo7sp on 12/6/13 at 9:12 PM
 */
case class BoardLine(id: Int) extends BoardObject with GraphicsObject {
  val toolID = Tools.line
  var x2, y2 = 0
  var color = Color.BLACK
  var thickness = 1F

  def render(): Unit = {
    Gdx.gl.glLineWidth(thickness)

    Renderer.batch.end()
    Renderer.shape.begin(ShapeType.Line)

    Renderer.shape.setColor(color)
    Renderer.shape.line(x, y, x2, y2)

    Renderer.shape.end()
    Renderer.batch.begin()

    Gdx.gl.glLineWidth(1)
  }

  def update(): Unit = ()

  def toArray: Array[String] = Array(x.toString, y.toString, x2.toString, y2.toString, Colors.toString(color), thickness.toString)

  def toGraphicsObject: GraphicsObject = this
}
