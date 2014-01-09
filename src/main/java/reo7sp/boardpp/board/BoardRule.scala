package reo7sp.boardpp.board

import reo7sp.boardpp.board.brul.{ShowMsgOnLineRule, ShowMsgOnClickRule}

/**
 * Created by reo7sp on 1/2/14 at 11:54 PM
 */
trait BoardRule {
  val id: Int

  val ruleID: Int

  def onClick(x: Int, y: Int): Unit = ()

  def onLine(x1: Int, y1: Int, x2: Int, y2: Int): Unit = ()

  def toArray: Array[String]

  def conditionString: String

  def actionString: String
}

object BoardRule {
  def newInstance(id: Int, ruleID: Int, data: Array[String]): BoardRule = ruleID match {
    case Rules.showMsgOnClickRule =>
      val brule = new ShowMsgOnClickRule(id)
      brule.msg = data(0)
      brule.x1 = data(1).toInt
      brule.y1 = data(2).toInt
      brule.x2 = data(3).toInt
      brule.y2 = data(4).toInt
      brule
    case Rules.showMsgOnLineRule =>
      val brule = new ShowMsgOnLineRule(id)
      brule.msg = data(0)
      brule.x11 = data(1).toInt
      brule.y11 = data(2).toInt
      brule.x12 = data(3).toInt
      brule.y12 = data(4).toInt
      brule.x21 = data(5).toInt
      brule.y21 = data(6).toInt
      brule.x22 = data(7).toInt
      brule.y22 = data(8).toInt
      brule
  }
}
