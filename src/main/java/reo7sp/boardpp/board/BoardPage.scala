package reo7sp.boardpp.board

import scala.collection.mutable.ArrayBuffer
import reo7sp.boardpp.ui.Renderer
import reo7sp.boardpp.{DebugInfo, Tools, Canvas}
import org.newdawn.slick.{Color, Image}
import reo7sp.boardpp.util.Colors
import reo7sp.boardpp.board.brul.{ShowMsgOnLineRule, ShowMsgOnClickRule}

/**
 * Created by reo7sp on 1/2/14 at 3:37 PM
 */
class BoardPage {
  val elements = new ArrayBuffer[BoardObject]
  val rules = new ArrayBuffer[BoardRule]

  private[this] var needRender = true
  private[this] var needUpdate = true

  val img = new Image(Renderer.container.getWidth, Renderer.container.getHeight)
  val imgG = img.getGraphics
  imgG.setAntiAlias(true)

  val canvas = new Image(Renderer.container.getWidth, Renderer.container.getHeight)
  val canvasG = canvas.getGraphics
  canvasG.setAntiAlias(true)

  val highlight = new Image(Renderer.container.getWidth, Renderer.container.getHeight)
  val highlightG = highlight.getGraphics
  highlightG.setAntiAlias(true)

  def invalidate(): Unit = needRender = true

  def render(): Unit = if (needRender) {
    DebugInfo.invalidateStart()

    imgG.setColor(Color.white)
    imgG.fillRect(0, 0, img.getWidth, img.getHeight)
    elements.foreach(_.toGraphicsObject.update())
    elements.foreach(_.toGraphicsObject.render(imgG))
    imgG.drawImage(canvas, 0, 0)
    imgG.drawImage(highlight, 0, 0, Colors.semiTransparentRed)
    if (Canvas.editing) {
      imgG.setColor(Color.red)
      elements.foreach(elem => imgG.drawRect(elem.x, elem.y, 32, 32))

      imgG.setColor(Color.magenta)
      imgG.setFont(Renderer.font)
      rules.foreach {
        case brul: ShowMsgOnClickRule =>
          imgG.drawRect(brul.x1, brul.y1, brul.x2 - brul.x1, brul.y2 - brul.y1)
          imgG.drawString(brul.msg, brul.x1, brul.y2)

        case brul: ShowMsgOnLineRule =>
          imgG.drawRect(brul.x11, brul.y11, brul.x12 - brul.x11, brul.y12 - brul.y11)
          imgG.drawRect(brul.x21, brul.y21, brul.x22 - brul.x21, brul.y22 - brul.y21)
          imgG.drawString(brul.msg, brul.x11, brul.y12)
          imgG.drawString(brul.msg, brul.x21, brul.y22)
      }
    }

    DebugInfo.invalidateEnd()
  }

  def update(): Unit = if (needUpdate) {
    canvasG.setColor(Canvas.color)
    highlightG.setColor(Color.red)
    needUpdate = false
  }

  def clear(): Unit = {
    canvasG.clear()
    highlightG.clear()
    invalidate()
  }

  def flush(): Unit = {
    imgG.clear()
    canvasG.clear()
    highlightG.clear()
    invalidate()
  }

  def destroy(): Unit = {
    imgG.destroy()
    canvasG.destroy()
    highlightG.destroy()

    img.destroy()
    canvas.destroy()
    highlight.destroy()
  }

  def updateSettings(): Unit = needUpdate = true

  def g = if (Canvas.tool == Tools.highlighter) highlightG else canvasG

  def +=(bobj: BoardObject): BoardPage = {
    elements += bobj
    bobj.page = this
    invalidate()
    this
  }

  def -=(bobj: BoardObject): BoardPage = {
    elements -= bobj
    bobj.page = null
    invalidate()
    this
  }

  def +=(brule: BoardRule): BoardPage = {
    rules += brule
    this
  }

  def -=(brule: BoardRule): BoardPage = {
    rules -= brule
    this
  }
}
