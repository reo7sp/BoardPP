package reo7sp.boardpp.ui

/**
 * Created by reo7sp on 11/29/13 at 3:18 PM
 */
trait GraphicsObject {
  def render(): Unit

  def update(): Unit

  def onMousePressed(): Unit = ()

  def onMouseReleased(): Unit = ()
}
