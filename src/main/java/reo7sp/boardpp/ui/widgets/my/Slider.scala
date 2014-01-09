package reo7sp.boardpp.ui.widgets.my

import org.newdawn.slick.{Color, Graphics}
import reo7sp.boardpp.ui.Mouse

/**
 * Created by reo7sp on 1/7/14 at 3:00 PM
 */
class Slider extends Component {
  var min, max, cur = 0
  var actionOnChange: (Int) => Any = null
  private[this] var sliding = false

  override def render(g: Graphics): Unit = {
    super.render(g)

    g.setColor(Color.lightGray)
    g.fillRect(realX, realY + height / 2 - 4, width, 8)
    g.setColor(Color.gray)
    g.drawRect(realX, realY + height / 2 - 4, width, 8)

    g.setColor(Color.white)
    g.fillRect(realX + (cur - min) * width / (max - min), realY + height / 2 - 8, 8, 16)
    g.setColor(Color.lightGray)
    g.drawRect(realX + (cur - min) * width / (max - min), realY + height / 2 - 8, 8, 16)
  }

  override def update(): Unit = if (sliding) {
    if (realX < Mouse.x && Mouse.x < realX + width && realY < Mouse.y && Mouse.y < realY + height) {
      cur = (Mouse.x - realX) * (max - min) / width + min
      if (cur > max) cur = max
      else if (cur < min) cur = min
      if (actionOnChange != null) actionOnChange(cur)
    }
  }

  override def onMousePressed(): Unit = {
    super.onMousePressed()
    sliding = true
  }

  override def onMouseReleased(): Unit = {
    super.onMouseReleased()
    sliding = false
  }
}
