package reo7sp.boardpp.ui.widgets.my

import reo7sp.boardpp.ui.{Renderer, Mouse, GraphicsObject}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType

/**
 * Created by reo7sp on 1/7/14 at 2:14 PM
 */
abstract class Component extends GraphicsObject {
  var parent: Container = null
  var x, y, width, height = 0
  var background: Color = null
  var tooltip: String = null
  var canClick = false

  def render(): Unit = {
    Renderer.batch.end()
    Renderer.shape.begin(ShapeType.Filled)

    if (background != null) {
      Renderer.shape.setColor(background)
      Renderer.shape.rect(realX, realY, width, height)
    }

    Renderer.shape.end()
    Renderer.batch.begin()
  }

  def drawTooltip(): Unit = if (tooltip != null) {
    if (realX < Mouse.x && Mouse.x < realX + width && realY < Mouse.y && Mouse.y < realY + height) {
      val bounds = Renderer.font.getBounds(tooltip)
      val w = bounds.width + 8
      val h = bounds.height + 8
      val x = realX + width / 2 - w / 2
      val y = realY - h - 2

      Renderer.batch.end()

      Renderer.shape.begin(ShapeType.Filled)
      Renderer.shape.setColor(Color.WHITE)
      Renderer.shape.rect(x, y, w, h)
      Renderer.shape.setColor(Color.BLACK)
      Renderer.shape.end()

      Renderer.shape.begin(ShapeType.Line)
      Renderer.shape.setColor(Color.BLACK)
      Renderer.shape.rect(x, y, w, h)
      Renderer.shape.end()

      Renderer.batch.begin()

      Renderer.font.setColor(Color.BLACK)
      Renderer.font.draw(Renderer.batch, tooltip, x + 4, y + h - 4)
    }
  }

  def onClick(): Unit = ()

  override def onMousePressed(): Unit = {
    if (realX < Mouse.x && Mouse.x < realX + width && realY < Mouse.y && Mouse.y < realY + height) {
      canClick = true
    }
  }

  override def onMouseReleased(): Unit = if (canClick) {
    if (realX < Mouse.x && Mouse.x < realX + width && realY < Mouse.y && Mouse.y < realY + height) {
      onClick()
    }
    canClick = false
  }

  def update(): Unit = ()

  def realX: Int = if (parent == null) x else x + parent.realX

  def realY: Int = if (parent == null) y else y + parent.realY
}
