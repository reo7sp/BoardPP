package reo7sp.boardpp.ui

import reo7sp.boardpp.{Tools, Canvas}
import scala.util.Random
import reo7sp.boardpp.ui.bobjeditor.{BoardLineEditor, BoardShapeEditor, BoardImageEditor, BoardTextEditor}
import org.newdawn.slick.{Color, Graphics}
import reo7sp.boardpp.board.bobj.{BoardLine, BoardShape, BoardImage, BoardText}
import reo7sp.boardpp.util.RenderUtil

/**
 * Created by reo7sp on 11/30/13 at 5:16 PM
 */
object BoardRenderer extends GraphicsObject {
  private[this] var drawing = false
  private[this] val gArray = Array(page.canvasG, page.highlightG)

  def render(g: Graphics): Unit = {
    Canvas.tool match {
      case Tools.pen | Tools.highlighter | Tools.eraser => draw()
      case _ =>
    }
    page.update()
    page.render()
    g.drawImage(page.img, 0, 0)
    g.setColor(Color.blue)
    if (Mouse.dragging) Canvas.tool match {
      case Tools.line => g.drawLine(Mouse.dragStartX, Mouse.dragStartY, Mouse.x, Mouse.y)
      case Tools.shape => g.drawRect(Mouse.dragStartX, Mouse.dragStartY, Mouse.x - Mouse.dragStartX, Mouse.y - Mouse.dragStartY)
      case _ =>
    }
  }

  def update(): Unit = ()

  override def onMousePressed(): Unit = if (Mouse.x < ToolPanel.x) {
    drawing = true
  }

  override def onMouseReleased(): Unit = if (drawing) {
    drawing = false
    Canvas.tool match {
      case Tools.cursor => cursor()
      case _ => edit()
    }
    if (!Canvas.editing && Canvas.tool == Tools.line) {
      page.rules.foreach(_.onLine(Mouse.dragStartX, Mouse.dragStartY, Mouse.x, Mouse.y))
    } else if (Canvas.tool == Tools.cursor) {
      page.rules.foreach(_.onClick(Mouse.x, Mouse.y))
    }
  }

  private[this] def cursor(): Unit = {
    val elems = page.elements.filter(elem => elem.x < Mouse.x && Mouse.x < elem.x + 32 && elem.y < Mouse.y && Mouse.y < elem.y + 32)
    if (elems.nonEmpty) {
      elems(0) match {
        case elem: BoardText => if (Canvas.editing) BoardTextEditor(elem)
        case elem: BoardImage => if (Canvas.editing) BoardImageEditor(elem)
        case elem: BoardShape => if (Canvas.editing) BoardShapeEditor(elem)
        case elem: BoardLine => if (Canvas.editing) BoardLineEditor(elem)
      }
    }
  }

  private[this] def draw(): Unit = if (drawing) {
    if (Canvas.tool == Tools.eraser) {
      val size = Canvas.thickness * 8

      for (g <- gArray) {
        g.setDrawMode(Graphics.MODE_COLOR_MULTIPLY)
        g.setColor(Color.transparent)
        RenderUtil.makeDiagonalLine(
          Mouse.lastX, Mouse.lastY, Mouse.x, Mouse.y,
          (x, y) => g.fillOval(x - size / 2, y - size / 2, size, size)
        )
        g.setDrawMode(Graphics.MODE_NORMAL)
      }
    } else {
      val size = Canvas.thickness * (if (Canvas.tool == Tools.pen) 1 else 8)
      RenderUtil.makeDiagonalLine(
        Mouse.lastX, Mouse.lastY, Mouse.x, Mouse.y,
        (x, y) => page.g.fillOval(x - size / 2, y - size / 2, size, size)
      )
    }
    page.invalidate()
  }

  private[this] def edit(): Unit = if (Canvas.editing) {
    Canvas.tool match {
      case Tools.text =>
        val elem = new BoardText(Random.nextInt())
        elem.x = Mouse.x
        elem.y = Mouse.y
        page += elem
        BoardTextEditor(elem)

      case Tools.image =>
        val elem = new BoardImage(Random.nextInt())
        elem.x = Mouse.x
        elem.y = Mouse.y
        page += elem
        BoardImageEditor(elem)

      case Tools.shape =>
        val elem = new BoardShape(Random.nextInt())
        elem.x = math.min(Mouse.dragStartX, Mouse.x)
        elem.y = math.min(Mouse.dragStartY, Mouse.y)
        elem.w = math.abs(Mouse.x - Mouse.dragStartX)
        elem.h = math.abs(Mouse.y - Mouse.dragStartY)
        elem.invalidateGradient()
        page += elem
        BoardShapeEditor(elem)

      case Tools.line =>
        val elem = new BoardLine(Random.nextInt())
        elem.x = math.min(Mouse.dragStartX, Mouse.x)
        elem.y = math.min(Mouse.dragStartY, Mouse.y)
        elem.x2 = math.abs(Mouse.x - Mouse.dragStartX)
        elem.x2 = math.abs(Mouse.y - Mouse.dragStartY)
        page += elem
        BoardLineEditor(elem)

      case _ =>
    }
  }

  def page = Canvas.board.curPage
}
