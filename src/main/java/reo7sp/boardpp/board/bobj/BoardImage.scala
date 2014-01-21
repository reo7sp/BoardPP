package reo7sp.boardpp.board.bobj

import reo7sp.boardpp.Tools
import reo7sp.boardpp.ui.{Renderer, GraphicsObject}
import reo7sp.boardpp.board.BoardObject
import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.Gdx
import java.net.URL
import reo7sp.boardpp.util.RenderUtils

/**
 * Created by reo7sp on 12/30/13 at 2:07 PM
 */
case class BoardImage(id: Int) extends BoardObject with GraphicsObject {
  val toolID: Int = Tools.image
  private[this] var _url: URL = null
  private[this] var _img: Texture = null
  private[this] var loader: Thread = null
  var w, h = 0

  def render(): Unit = if (img == null) {
    Renderer.batch.end()
    Renderer.shape.begin(ShapeType.Line)

    Renderer.shape.setColor(Color.BLACK)
    Renderer.shape.rect(x, y, if (w < 1) 32 else w, if (h < 1) 32 else h)

    if (loader == null) {
      Renderer.shape.line(x, y, x + (if (w < 1) 32 else w), y + (if (h < 1) 32 else h))
      Renderer.shape.line(x + (if (w < 1) 32 else w), y, x, y + (if (h < 1) 32 else h))

      Renderer.shape.end()
      Renderer.batch.begin()
    } else {
      Renderer.shape.end()
      Renderer.batch.begin()

      val msg = "Загрузка..."
      val bounds = Renderer.font.getBounds(msg)
      val w1 = bounds.width
      val h1 = bounds.height
      val x1 = x - w1 / 2 + w / 2
      val y1 = y - h1 / 2 + h / 2
      Renderer.font.setColor(Color.BLACK)
      Renderer.font.draw(Renderer.batch, msg, x1, y1)
    }
  } else {
    Renderer.batch.draw(
      img,
      x, y, if (w < 1) img.getWidth else w, if (h < 1) img.getHeight else h
    )
  }

  def update(): Unit = ()

  def toArray: Array[String] = Array(x.toString, y.toString, w.toString, h.toString, url.toString)

  def toGraphicsObject: GraphicsObject = this

  def url = _url

  def url_=(p1: URL): Unit = if (_url != p1) {
    _url = p1
    Gdx.app.postRunnable(new Runnable {
      def run(): Unit = if (_img != null) {
        _img.dispose()
        _img = null
      } else if (loader != null) {
        loader.interrupt()
        loader = null
      }
    })
  }

  def img = {
    if (_img == null && _url != null && loader == null) {
      loader = newImgLoader
      loader.start()
    }
    _img
  }

  private[this] def newImgLoader = new Thread() {
    override def run(): Unit = {
      val pixmap = RenderUtils.readImg(url)
      if (!isInterrupted) {
        Gdx.app.postRunnable(new Runnable {
          def run(): Unit = _img = new Texture(pixmap)
        })
      } else {
        pixmap.dispose()
      }
    }
  }
}
