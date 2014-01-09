package reo7sp.boardpp.ui.widgets.my

import reo7sp.boardpp.ui.{Mouse, GraphicsObject}
import org.newdawn.slick.{Color, Graphics}

/**
 * Created by reo7sp on 1/7/14 at 2:14 PM
 */
abstract class Component extends GraphicsObject {
  var parent: Container = null
  var x, y, width, height = 0
  var background = Color.transparent
  var tooltip: String = null
  private[this] var canClick = false

  def render(g: Graphics): Unit = {
    g.setColor(background)
    g.fillRect(realX, realY, width, height)
  }

  def drawTooltip(g: Graphics): Unit = if (tooltip != null) {
    if (realX < Mouse.x && Mouse.x < realX + width && realY < Mouse.y && Mouse.y < realY + height) {
      val w = g.getFont.getWidth(tooltip) + 4
      val h = g.getFont.getHeight(tooltip) + 4
      val x = realX + width / 2 - w / 2
      val y = realY + height + 2
      g.setColor(Color.white)
      g.fillRect(x, y, w, h)
      g.setColor(Color.black)
      g.drawRect(x, y, w, h)
      g.drawString(tooltip, x + 2, y + 2)
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
