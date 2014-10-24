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

package reo7sp.boardpp.board

import reo7sp.boardpp.board.brul.{ShowMsgOnLineRule, ShowMsgOnClickRule}
import reo7sp.boardpp.Rules

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
