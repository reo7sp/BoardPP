package reo7sp.boardpp.board.brul

import reo7sp.boardpp.board.BoardRule
import javax.swing.JOptionPane

/**
 * Created by reo7sp on 1/3/14 at 12:27 AM
 */
case class ShowMsgOnClickRule(id: Int) extends BoardRule {
  val ruleID: Int = 1
  var msg: String = ""
  var x1, y1, x2, y2 = 0

  override def onClick(x: Int, y: Int): Unit = {
    if (math.min(x1, x2) < x && x < math.max(x1, x2) && math.min(y1, y2) < y && y < math.max(y1, y2)) {
      JOptionPane.showMessageDialog(null, msg)
    }
  }

  def toArray: Array[String] = Array(msg, x1.toString, y1.toString, x2.toString, y2.toString)

  def conditionString: String = "мышка нажата в регионе ((" + x1 + ", " + y1 + "), (" + x2 + ", " + y2 + "))"

  def actionString: String = "показать \"" + msg + "\""
}
