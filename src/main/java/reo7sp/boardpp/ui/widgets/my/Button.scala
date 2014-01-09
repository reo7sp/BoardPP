package reo7sp.boardpp.ui.widgets.my

import reo7sp.boardpp.ui.Mouse

/**
 * Created by reo7sp on 1/7/14 at 2:22 PM
 */
class Button extends Label {
  var actionOnClick: () => Any = null

  override def onClick(): Unit = if (actionOnClick != null) {
    if (realX < Mouse.x && Mouse.x < realX + width && realY < Mouse.y && Mouse.y < realY + height) {
      actionOnClick()
    }
  }
}
