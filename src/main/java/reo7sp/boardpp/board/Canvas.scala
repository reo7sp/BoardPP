package reo7sp.boardpp.board

import com.badlogic.gdx.graphics.Color
import scala.collection.mutable

/**
 * Created by reo7sp on 1/17/14 at 2:42 PM
 */
class Canvas {
  val points = new mutable.HashSet[Point]

  def +=(x: Int, y: Int): Canvas = +=(x, y, BoardSession.color, BoardSession.radius)

  def +=(x: Int, y: Int, color: Color, radius: Int): Canvas = {
    points += Point(x, y, color, radius)
    this
  }

  def -=(x: Int, y: Int, radius: Int) = {
    def intersects(x1: Int, y1: Int, radius1: Int) = {
      val x0 = x - radius / 2
      val y0 = y - radius / 2

      val xmin = x1
      val xmax = xmin + radius1

      val ymin = y1
      val ymax = ymin + radius1

      ((xmin > x0 && xmin < x0 + radius) && (xmax > x0 && xmax < x0 + radius)) && ((ymin > y0 && ymin < y0 + radius) && (ymax > y0 && ymax < y0 + radius))
    }

    for (point <- points) {
      if (intersects(point.x, point.y, point.radius)) {
        points -= point
      }
    }
    this
  }

  case class Point(x: Int, y: Int, color: Color, radius: Int)

}
