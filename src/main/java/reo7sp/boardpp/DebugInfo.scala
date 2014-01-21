package reo7sp.boardpp

import com.badlogic.gdx.Gdx

/**
 * Created by reo7sp on 11/29/13 at 3:42 PM
 */
object DebugInfo {
  var deltaCalc = 0
  var calcStartTime = 0L
  var deltaRender = 0
  var renderStartTime = 0L

  def fps = Gdx.graphics.getFramesPerSecond

  def delta = (Gdx.graphics.getDeltaTime * 1000).toInt

  def calcStart(): Unit = calcStartTime = System.nanoTime

  def calcEnd(): Unit = {
    val now = System.nanoTime
    deltaCalc = ((now - calcStartTime) / 1000000).toInt
  }

  def renderStart(): Unit = renderStartTime = System.nanoTime

  def renderEnd(): Unit = {
    val now = System.nanoTime
    deltaRender = ((now - renderStartTime) / 1000000).toInt
  }
}
