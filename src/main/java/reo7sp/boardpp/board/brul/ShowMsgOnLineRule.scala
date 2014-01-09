package reo7sp.boardpp.board.brul

import javax.swing.JOptionPane
import reo7sp.boardpp.board.BoardRule

/**
 * Created by reo7sp on 1/3/14 at 12:28 AM
 */
case class ShowMsgOnLineRule(id: Int) extends BoardRule {
  val ruleID: Int = 2
  var msg: String = ""
  var x11, y11, x12, y12, x21, y21, x22, y22 = 0

  override def onLine(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
    val a = math.min(x11, x12) < x1 && x1 < math.max(x11, x12) && math.min(y11, y12) < y1 && y1 < math.max(y11, y12) &&
      math.min(x21, x22) < x2 && x2 < math.max(x21, x22) && math.min(y21, y22) < y2 && y2 < math.max(y21, y22)

    val b = math.min(x21, x22) < x1 && x1 < math.max(x21, x22) && math.min(y21, y22) < y1 && y1 < math.max(y21, y22) &&
      math.min(x11, x12) < x2 && x2 < math.max(x11, x12) && math.min(y11, y12) < y2 && y2 < math.max(y11, y12)

    if (a || b) JOptionPane.showMessageDialog(null, msg)
  }

  def toArray: Array[String] = Array(msg, x11.toString, y11.toString, x12.toString, y12.toString, x21.toString, y21.toString, x22.toString, y22.toString)

  def conditionString: String =
    "проведена линия между регионами ((" + x11 + ", " + y11 + ") к (" + x12 + ", " + y12 + ")) и ((" + x21 + ", " + y21 + ") к (" + x22 + ", " + y22 + "))"

  def actionString: String = "показать \"" + msg + "\""
}
