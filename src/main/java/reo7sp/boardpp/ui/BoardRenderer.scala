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

package reo7sp.boardpp.ui

import reo7sp.boardpp.Tools
import reo7sp.boardpp.ui.bobjeditor.{BoardLineEditor, BoardShapeEditor, BoardImageEditor, BoardTextEditor}
import reo7sp.boardpp.board.bobj.{BoardLine, BoardShape, BoardImage, BoardText}
import reo7sp.boardpp.util.RenderUtils
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import reo7sp.boardpp.board.BoardSession
import com.badlogic.gdx.Gdx

/**
 * Created by reo7sp on 11/30/13 at 5:16 PM
 */
object BoardRenderer extends GraphicsObject {
  private[this] var drawing = false

  def render(): Unit = {
    page.render()
    if (Mouse.dragging) {
      Renderer.batch.end()
      Renderer.shape.begin(ShapeType.Line)

      Renderer.shape.setColor(Color.BLUE)
      BoardSession.tool match {
        case Tools.line => Renderer.shape.line(Mouse.dragStartX, Mouse.dragStartY, Mouse.x, Mouse.y)
        case Tools.`rect` => Renderer.shape.rect(Mouse.dragStartX, Mouse.dragStartY, Mouse.x - Mouse.dragStartX, Mouse.y - Mouse.dragStartY)
        case _ =>
      }

      Renderer.shape.end()
      Renderer.batch.begin()
    }
    if (ShapeGetter.busy) {
      Renderer.font.setColor(Color.BLACK)
      val msg = "Выделите регион, чтобы продолжить"
      val bounds = Renderer.font.getBounds(msg)
      Renderer.font.draw(
        Renderer.batch,
        msg,
        Gdx.graphics.getWidth / 2 - bounds.width / 2,
        Gdx.graphics.getHeight / 2 - bounds.height / 2
      )
    }
  }

  def update(): Unit = BoardSession.tool match {
    case Tools.pen | Tools.eraser => draw()
    case _ =>
  }

  override def onMousePressed(): Unit = if (Mouse.x < ToolPanel.x) {
    drawing = true
  }

  override def onMouseReleased(): Unit = if (drawing) {
    drawing = false
    BoardSession.tool match {
      case Tools.cursor => cursor()
      case _ => edit()
    }
    if (!BoardSession.editing && BoardSession.tool == Tools.line) {
      page.rules.foreach(_.onLine(Mouse.dragStartX, Mouse.dragStartY, Mouse.x, Mouse.y))
    } else if (BoardSession.tool == Tools.cursor) {
      page.rules.foreach(_.onClick(Mouse.x, Mouse.y))
    }
  }

  private[this] def cursor(): Unit = {
    val elems = page.elements.filter(elem => elem.x < Mouse.x && Mouse.x < elem.x + 32 && elem.y < Mouse.y && Mouse.y < elem.y + 32)
    if (elems.nonEmpty) {
      elems(0) match {
        case elem: BoardText => if (BoardSession.editing) BoardTextEditor(elem)
        case elem: BoardImage => if (BoardSession.editing) BoardImageEditor(elem)
        case elem: BoardShape => if (BoardSession.editing) BoardShapeEditor(elem)
        case elem: BoardLine => if (BoardSession.editing) BoardLineEditor(elem)
      }
    }
  }

  private[this] def draw(): Unit = if (drawing) {
    if (BoardSession.tool == Tools.eraser) {
      BoardSession.board.curPage.canvas.-=(Mouse.x, Mouse.y, BoardSession.radius * 16)
    } else {
      RenderUtils.makeDiagonalLine(
        Mouse.lastX, Mouse.lastY, Mouse.x, Mouse.y,
        BoardSession.board.curPage.canvas.+=
      )
    }
  }

  private[this] def edit(): Unit = if (BoardSession.editing) try {
    BoardSession.tool match {
      case Tools.text =>
        val elem = new BoardText(page.lastElementID + 1)
        elem.x = Mouse.x
        elem.y = Mouse.y
        BoardTextEditor(elem)
        page += elem

      case Tools.image =>
        val elem = new BoardImage(page.lastElementID + 1)
        elem.x = Mouse.x
        elem.y = Mouse.y
        BoardImageEditor(elem)
        page += elem

      case Tools.rect => if (ShapeGetter.free) {
        val elem = new BoardShape(page.lastElementID + 1)
        elem.x = math.min(Mouse.dragStartX, Mouse.x)
        elem.y = math.min(Mouse.dragStartY, Mouse.y)
        elem.w = math.abs(Mouse.x - Mouse.dragStartX)
        elem.h = math.abs(Mouse.y - Mouse.dragStartY)
        BoardShapeEditor(elem)
        page += elem
      } else {
        ShapeGetter.onRect(
          math.min(Mouse.dragStartX, Mouse.x),
          math.min(Mouse.dragStartY, Mouse.y),
          math.max(Mouse.dragStartX, Mouse.x),
          math.max(Mouse.dragStartY, Mouse.y)
        )
      }

      case Tools.line =>
        val elem = new BoardLine(page.lastElementID + 1)
        elem.x = Mouse.dragStartX
        elem.y = Mouse.dragStartY
        elem.x2 = Mouse.x
        elem.y2 = Mouse.y
        BoardLineEditor(elem)
        page += elem

      case _ =>
    }
  } catch {
    case _: Throwable =>
  }

  def page = BoardSession.board.curPage
}
