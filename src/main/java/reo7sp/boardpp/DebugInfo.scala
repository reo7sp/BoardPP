package reo7sp.boardpp

/**
 * Created by reo7sp on 11/29/13 at 3:42 PM
 */
object DebugInfo {
  var fps = 0
  var delta = 0
  var deltaCalc = 0
  var calcStartTime = 0L
  var deltaRender = 0
  var renderStartTime = 0L
  var deltaInvalidate = 0
  var invalidateStartTime = 0L

  def calcStart() {
    calcStartTime = System.nanoTime
  }

  def calcEnd() {
    val now = System.nanoTime
    deltaCalc = ((now - calcStartTime) / 1000000).toInt
    calcStartTime = 0
  }

  def renderStart() {
    renderStartTime = System.nanoTime
  }

  def renderEnd() {
    val now = System.nanoTime
    deltaRender = ((now - renderStartTime) / 1000000).toInt
    renderStartTime = 0
  }

  def invalidateStart() {
    invalidateStartTime = System.nanoTime
  }

  def invalidateEnd() {
    val now = System.nanoTime
    deltaInvalidate = ((now - invalidateStartTime) / 1000000).toInt
    invalidateStartTime = 0
  }
}
