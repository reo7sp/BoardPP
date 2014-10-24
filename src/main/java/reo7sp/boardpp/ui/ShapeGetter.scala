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

import scala.collection.mutable.ArrayBuffer
import reo7sp.boardpp.board.BoardSession
import reo7sp.boardpp.Tools

/**
 * Created by reo7sp on 1/21/14 at 3:40 PM
 */
object ShapeGetter {
  private[this] val listeners = new ArrayBuffer[ShapeListener]

  def +=(listener: ShapeListener) = {
    listeners += listener
    BoardSession.tool = Tools.rect
    this
  }

  def onRect(x1: Int, y1: Int, x2: Int, y2: Int) = if (listeners.isEmpty) {
    false
  } else {
    listeners.foreach(_.onRect(x1, y1, x2, y2))
    listeners.clear()
    true
  }

  def busy = listeners.nonEmpty

  def free = listeners.isEmpty

  trait ShapeListener {
    def onRect(x1: Int, y1: Int, x2: Int, y2: Int)
  }

}
