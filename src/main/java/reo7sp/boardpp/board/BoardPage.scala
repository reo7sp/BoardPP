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
import reo7sp.boardpp.ui.Renderer
import reo7sp.boardpp.board.brul.{ShowMsgOnLineRule, ShowMsgOnClickRule}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType

/**
 * Created by reo7sp on 1/2/14 at 3:37 PM
 */
class BoardPage {
  val elements = new ArrayBuffer[BoardObject]
  val rules = new ArrayBuffer[BoardRule]
  val canvas = new Canvas

  def render(): Unit = {
    elements.foreach(_.toGraphicsObject.update())
    elements.foreach(_.toGraphicsObject.render())

    Renderer.batch.end()
    Renderer.shape.begin(ShapeType.Filled)

    for (point <- canvas.points) {
      Renderer.shape.setColor(point.color)
      Renderer.shape.circle(point.x, point.y, point.radius)
    }

    if (BoardSession.editing) {
      Renderer.shape.end()
      Renderer.shape.begin(ShapeType.Line)

      Renderer.shape.setColor(Color.RED)
      elements.foreach(elem => Renderer.shape.rect(elem.x, elem.y, 32, 32))

      Renderer.shape.end()
      Renderer.batch.begin()
      Renderer.font.setColor(Color.RED)
      elements.foreach(elem => Renderer.font.draw(Renderer.batch, elem.id.toString, elem.x + 8, elem.y + 16))
      Renderer.batch.end()
      Renderer.shape.begin(ShapeType.Line)

      Renderer.shape.setColor(Color.MAGENTA)
      rules.foreach {
        case brul: ShowMsgOnClickRule =>
          Renderer.shape.rect(brul.x1, brul.y1, brul.x2 - brul.x1, brul.y2 - brul.y1)
          Renderer.shape.end()
          Renderer.batch.begin()
          Renderer.font.setColor(Color.MAGENTA)
          Renderer.font.draw(Renderer.batch, brul.msg, brul.x1 + 8, brul.y2 - 8)
          Renderer.batch.end()
          Renderer.shape.begin(ShapeType.Line)

        case brul: ShowMsgOnLineRule =>
          Renderer.shape.rect(brul.x11, brul.y11, brul.x12 - brul.x11, brul.y12 - brul.y11)
          Renderer.shape.rect(brul.x21, brul.y21, brul.x22 - brul.x21, brul.y22 - brul.y21)
          Renderer.shape.end()
          Renderer.batch.begin()
          Renderer.font.setColor(Color.MAGENTA)
          Renderer.font.draw(Renderer.batch, brul.msg, brul.x11 + 8, brul.y12 - 8)
          Renderer.font.draw(Renderer.batch, brul.msg, brul.x21 + 8, brul.y22 - 8)
          Renderer.batch.end()
          Renderer.shape.begin(ShapeType.Line)
      }
    }

    Renderer.shape.end()
    Renderer.batch.begin()
  }

  def clear(): Unit = {
    canvas.points.clear()
  }

  def +=(bobj: BoardObject): BoardPage = {
    elements += bobj
    elements.sortWith((a, b) => a.id < b.id)
    this
  }

  def -=(bobj: BoardObject): BoardPage = {
    elements -= bobj
    elements.sortWith((a, b) => a.id < b.id)
    this
  }

  def +=(brule: BoardRule): BoardPage = {
    rules += brule
    rules.sortWith((a, b) => a.id < b.id)
    this
  }

  def -=(brule: BoardRule): BoardPage = {
    rules -= brule
    rules.sortWith((a, b) => a.id < b.id)
    this
  }

  def lastElementID = if (elements.nonEmpty) {
    elements.sortWith((a, b) => a.id < b.id)
    elements(elements.length - 1).id
  } else 0

  def lastRuleID = if (rules.nonEmpty) {
    rules.sortWith((a, b) => a.id < b.id)
    rules(rules.length - 1).id
  } else 0
}
