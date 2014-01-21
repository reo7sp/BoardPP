package reo7sp.boardpp.ui.widgets.my

import reo7sp.boardpp.ui.{Renderer, Mouse}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType

/**
 * Created by reo7sp on 1/7/14 at 3:00 PM
 */
class Slider extends Component {
  var min, max, cur = 0
  var actionOnChange: (Int) => Any = null
  private[this] var sliding = false

  override def render(): Unit = {
    super.render()

    Renderer.batch.end()

    Renderer.shape.begin(ShapeType.Filled)
    Renderer.shape.setColor(Color.LIGHT_GRAY)
    Renderer.shape.rect(realX, realY + height / 2 - 4, width, 8)
    Renderer.shape.end()

    Renderer.shape.begin(ShapeType.Line)
    Renderer.shape.setColor(Color.GRAY)
    Renderer.shape.rect(realX, realY + height / 2 - 4, width, 8)
    Renderer.shape.end()

    Renderer.shape.begin(ShapeType.Filled)
    Renderer.shape.setColor(Color.WHITE)
    Renderer.shape.rect(realX + (cur - min) * width / (max - min), realY + height / 2 - 8, 8, 16)
    Renderer.shape.end()

    Renderer.shape.begin(ShapeType.Line)
    Renderer.shape.setColor(Color.LIGHT_GRAY)
    Renderer.shape.rect(realX + (cur - min) * width / (max - min), realY + height / 2 - 8, 8, 16)
    Renderer.shape.end()

    Renderer.batch.begin()
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
    if (canClick) sliding = true
  }

  override def onMouseReleased(): Unit = {
    super.onMouseReleased()
    sliding = false
  }
}
