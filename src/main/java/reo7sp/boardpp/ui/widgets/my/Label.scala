package reo7sp.boardpp.ui.widgets.my

import org.newdawn.slick.{Image, Color, Graphics}

/**
 * Created by reo7sp on 1/7/14 at 2:33 PM
 */
class Label extends Component {
  var color = Color.black
  var text: String = null
  var icon: Image = null

  override def render(g: Graphics): Unit = {
    super.render(g)
    drawIcon(g)
    drawText(g)
  }

  private[this] def drawIcon(g: Graphics): Unit = if (icon != null) {
    val w = icon.getWidth
    val h = icon.getHeight
    g.drawImage(icon, realX + width / 2 - w / 2, realY + height / 2 - h / 2)
  }

  private[this] def drawText(g: Graphics): Unit = if (text != null) {
    g.setColor(color)
    val w = g.getFont.getWidth(text)
    val h = g.getFont.getHeight(text)
    g.drawString(text, realX + width / 2 - w / 2, realY + height / 2 - h / 2)
  }
}
