package reo7sp.boardpp.ui

import reo7sp.boardpp.{DebugInfo, Canvas, Core}
import reo7sp.boardpp.board.Board
import org.newdawn.slick._
import reo7sp.boardpp.util.FontFactory

/**
 * Created by reo7sp on 11/27/13 at 4:29 PM
 */
object Renderer extends BasicGame(Core.name) {
  var font: UnicodeFont = null
  var container: GameContainer = null

  def init(container: GameContainer): Unit = try {
    this.container = container

    font = FontFactory.newFont(14, false, false)

    container.setVSync(true)
    container.setShowFPS(false)
    container.setAlwaysRender(true)
    container.setDefaultFont(font)

    Canvas.board = Board.newBoard
  } catch {
    case e: Throwable => CrashFrame.crash(e)
  }

  def update(container: GameContainer, delta: Int): Unit = try {
    DebugInfo.calcStart()

    Mouse.lastX = Mouse.x
    Mouse.lastY = Mouse.y
    Mouse.x = container.getInput.getMouseX
    Mouse.y = container.getInput.getMouseY

    val pressed = container.getInput.isMouseButtonDown(0)
    if (!Mouse.pressed && pressed) {
      BoardRenderer.onMousePressed()
      ToolPanel.onMousePressed()
    } else if (Mouse.pressed && !pressed) {
      BoardRenderer.onMouseReleased()
      ToolPanel.onMouseReleased()
      Mouse.dragging = false
      Mouse.dragStartX = 0
      Mouse.dragStartY = 0
    } else if (Mouse.pressed && pressed) {
      Mouse.dragging = true
      if (Mouse.dragStartX == 0) Mouse.dragStartX = Mouse.x
      if (Mouse.dragStartY == 0) Mouse.dragStartY = Mouse.y
    }
    Mouse.pressed = pressed

    DebugInfo.fps = container.getFPS
    DebugInfo.delta = delta

    BoardRenderer.update()
    ToolPanel.update()

    DebugInfo.calcEnd()
  } catch {
    case e: Throwable => CrashFrame.crash(e)
  }

  def render(container: GameContainer, g: Graphics): Unit = try {
    DebugInfo.renderStart()
    g.setAntiAlias(true)
    g.setFont(font)
    BoardRenderer.render(g)
    ToolPanel.render(g)
    DebugInfo.renderEnd()
  } catch {
    case e: Throwable => CrashFrame.crash(e)
  }

  private[boardpp] def init() {
    val app = new AppGameContainer(this)
    app.setDisplayMode(app.getScreenWidth - 32, app.getScreenHeight - 64, false)
    app.start()
  }
}
