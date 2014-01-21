package reo7sp.boardpp.ui

import scala.collection.mutable.ArrayBuffer
import reo7sp.boardpp.board.BoardSession
import reo7sp.boardpp.Tools

/**
 * Created by reo7sp on 1/21/14 at 3:40 PM
 */
object ShapeGetter {
  private[this] val listeners = new ArrayBuffer[ShapeListener]

  def +=(listener: ShapeListener) = {
    listeners += listener
    BoardSession.tool = Tools.rect
    this
  }

  def onRect(x1: Int, y1: Int, x2: Int, y2: Int) = if (listeners.isEmpty) {
    false
  } else {
    listeners.foreach(_.onRect(x1, y1, x2, y2))
    listeners.clear()
    true
  }

  def busy = listeners.nonEmpty

  def free = listeners.isEmpty

  trait ShapeListener {
    def onRect(x1: Int, y1: Int, x2: Int, y2: Int)
  }

}
