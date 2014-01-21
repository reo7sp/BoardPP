package reo7sp.boardpp.ui.widgets.my

import com.badlogic.gdx.graphics.{Texture, Color}
import reo7sp.boardpp.ui.Renderer

/**
 * Created by reo7sp on 1/7/14 at 2:33 PM
 */
class Label extends Component {
  var color = Color.BLACK
  var text: String = null
  var icon: Texture = null

  override def render(): Unit = {
    super.render()
    drawIcon()
    drawText()
  }

  private[this] def drawIcon(): Unit = if (icon != null) {
    val w = icon.getWidth
    val h = icon.getHeight
    Renderer.batch.draw(icon, realX + width / 2 - w / 2, realY + height / 2 - h / 2)
  }

  private[this] def drawText(): Unit = if (text != null) {
    val bounds = Renderer.font.getBounds(text)
    Renderer.font.setColor(color)
    Renderer.font.draw(Renderer.batch, text, realX + width / 2 - bounds.width / 2, realY - height / 2 + bounds.height / 2)
  }
}
