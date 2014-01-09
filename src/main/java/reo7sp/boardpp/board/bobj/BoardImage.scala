package reo7sp.boardpp.board.bobj

import reo7sp.boardpp.Tools
import reo7sp.boardpp.ui.GraphicsObject
import java.net.URL
import javax.imageio.ImageIO
import reo7sp.boardpp.board.BoardObject
import org.newdawn.slick.{Color, Image, Graphics}
import org.newdawn.slick.util.BufferedImageUtil

/**
 * Created by reo7sp on 12/30/13 at 2:07 PM
 */
case class BoardImage(id: Int) extends BoardObject with GraphicsObject {
  val toolID: Int = Tools.image
  private[this] var _url: URL = null
  private[this] var _img: Image = null
  var w, h = 0

  def render(g: Graphics): Unit = if (img == null) {
    g.setColor(Color.black)
    g.drawLine(x, y, x + (if (w < 1) 32 else w), y + (if (h < 1) 32 else h))
    g.drawLine(x + (if (w < 1) 32 else w), y, x, y + (if (h < 1) 32 else h))
  } else {
    g.drawImage(
      img,
      x, y, x + (if (w < 1) img.getWidth else w), y + (if (h < 1) img.getHeight else h),
      0, 0, img.getWidth, img.getHeight
    )
  }

  def update(): Unit = ()

  def toArray: Array[String] = Array(x.toString, y.toString, w.toString, h.toString, url.toString)

  def toGraphicsObject: GraphicsObject = this

  def url = _url

  def url_=(p1: URL): Unit = {
    if (_url != p1) {
      _url = p1
      _img = null
    }
  }

  def img = {
    if (_img == null && url != null) {
      _img = try {
        new Image(BufferedImageUtil.getTexture(url.toString, ImageIO.read(url)))
      } catch {
        case e: Throwable => null
      }
    }
    _img
  }
}
