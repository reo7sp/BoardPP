package reo7sp.boardpp.util

/**
 * Created by reo7sp on 12/28/13 at 1:07 PM
 */
object RenderUtil {
  def makeDiagonalLine(x1: Int, y1: Int, x2: Int, y2: Int, func: (Float, Float) => Any): Unit = {
    var dx = x2 - x1
    if (dx == 0) dx = 1
    val dy = y2 - y1
    var a = 10F - math.abs(x1 - x2)
    if (a < 1) a = 1
    for (j <- 0 until a.toInt) {
      for (i <- math.min(x1, x2) to math.max(x1, x2)) {
        val x = i + j / a
        val y = y1 + dy * (x - x1) / dx
        func(x, y)
      }
    }
  }
}
