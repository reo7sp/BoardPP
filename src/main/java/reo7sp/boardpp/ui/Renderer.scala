/*
 Copyright 2014 Reo_SP

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
*/

package reo7sp.boardpp.ui

import reo7sp.boardpp.{DebugInfo, Core}
import reo7sp.boardpp.board.{BoardSession, Board}
import reo7sp.boardpp.util.{RenderUtils, FontFactory}
import com.badlogic.gdx.{Input, ApplicationListener, Gdx}
import com.badlogic.gdx.backends.lwjgl.{LwjglApplicationConfiguration, LwjglApplication}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.graphics.{OrthographicCamera, Color, GL20}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

/**
 * Created by reo7sp on 11/27/13 at 4:29 PM
 */
object Renderer extends ApplicationListener {
  val maxDelta = 16
  var batch: SpriteBatch = null
  var shape: ShapeRenderer = null
  var font: BitmapFont = null
  var camera: OrthographicCamera = null
  var showInfo = true
  var lastKeyPress = 0L

  def create(): Unit = try {
    batch = new SpriteBatch
    shape = new ShapeRenderer
    font = FontFactory.newFont(12, bold = false, italic = false)
    camera = new OrthographicCamera
    camera.setToOrtho(false)
    BoardSession.board = Board.newBoard
  } catch {
    case e: Throwable => CrashFrame.crash(e)
  }

  def render(): Unit = try {
    calc()
    rend()
  } catch {
    case e: Throwable => CrashFrame.crash(e)
  }

  private[this] def calc(): Unit = {
    DebugInfo.calcStart()

    Mouse.lastX = Mouse.x
    Mouse.lastY = Mouse.y
    Mouse.x = Gdx.input.getX
    Mouse.y = RenderUtils.flipY(Gdx.input.getY)

    val pressed = Gdx.input.isButtonPressed(0)
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

    if (Gdx.input.isKeyPressed(Input.Keys.F1)) {
      if (System.currentTimeMillis() - lastKeyPress > 500) {
        Gdx.net.openURI("https://docs.google.com/document/d/13G0EHOmvj67eMbqURupw13n1ROmXsDznSZpF1YPwgEA/edit?usp=sharing")
        lastKeyPress = System.currentTimeMillis()
      }
    } else if (Gdx.input.isKeyPressed(Input.Keys.F2)) {
      if (System.currentTimeMillis() - lastKeyPress > 500) {
        showInfo = !showInfo
        lastKeyPress = System.currentTimeMillis()
      }
    }

    BoardRenderer.update()
    ToolPanel.update()

    DebugInfo.calcEnd()
  }

  private[this] def rend(): Unit = {
    DebugInfo.renderStart()

    Gdx.gl.glClearColor(1, 1, 1, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    camera.update()
    batch.setProjectionMatrix(camera.combined)
    shape.setProjectionMatrix(camera.combined)

    batch.begin()

    BoardRenderer.render()
    ToolPanel.render()

    if (showInfo) {
      val debugTextX = 48
      val debugTextY = 64
      Renderer.font.setColor(Color.BLACK)
      Renderer.font.draw(Renderer.batch, "FPS: " + DebugInfo.fps, debugTextX, debugTextY + 80)
      Renderer.font.draw(Renderer.batch, "Delta: " + DebugInfo.delta, debugTextX, debugTextY + 60)
      Renderer.font.draw(Renderer.batch, "    calc: " + DebugInfo.deltaCalc, debugTextX, debugTextY + 40)
      Renderer.font.draw(Renderer.batch, "    render: " + DebugInfo.deltaRender, debugTextX, debugTextY + 20)
      val freeMem = Runtime.getRuntime.freeMemory / 1024 / 1024
      val totalMem = Runtime.getRuntime.totalMemory / 1024 / 1024
      val usedMem = totalMem - freeMem
      Renderer.font.draw(Renderer.batch, "Mem: " + usedMem + "M/" + totalMem + "M", debugTextX, debugTextY)

      Renderer.font.draw(Renderer.batch, "Нажмите F1, чтобы открыть справку", debugTextX, RenderUtils.flipY(40))
      Renderer.font.draw(Renderer.batch, "Нажмите F2, чтобы убрать эти надписи", debugTextX, RenderUtils.flipY(60))
    }

    Renderer.font.setColor(Color.BLACK)
    Renderer.font.draw(
      Renderer.batch,
      "Страница " + (BoardSession.board.curPageID + 1) + "/" + BoardSession.board.pages.length,
      Gdx.graphics.getWidth - 188, 32
    )

    batch.end()

    DebugInfo.renderEnd()
  }

  def dispose(): Unit = {
    FontFactory.dispose()
    batch.dispose()
    shape.dispose()
  }

  def pause(): Unit = ()

  def resume(): Unit = ()

  def resize(width: Int, height: Int): Unit = {
    camera.setToOrtho(false, width, height)
    ToolPanel.x = width - ToolPanel.width - ToolPanel.cellSize
  }

  private[boardpp] def init(): Unit = {
    val conf = new LwjglApplicationConfiguration
    conf.title = Core.name
    conf.width = 1280
    conf.height = 720
    conf.useGL20 = true
    conf.vSyncEnabled = true
    new LwjglApplication(this, conf)
  }
}
