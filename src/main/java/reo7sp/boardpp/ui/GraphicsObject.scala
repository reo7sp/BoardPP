package reo7sp.boardpp.ui

import org.newdawn.slick.Graphics

/**
 * Created by reo7sp on 11/29/13 at 3:18 PM
 */
trait GraphicsObject {
  def render(g: Graphics): Unit

  def update(): Unit

  def onMousePressed(): Unit = ()

  def onMouseReleased(): Unit = ()
}
