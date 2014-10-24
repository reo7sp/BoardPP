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

package reo7sp.boardpp

import reo7sp.boardpp.board.BoardSession

/**
 * Created by reo7sp on 1/1/14 at 4:12 PM
 */
object SaveThread extends Thread("SaveThread") {
  var saving = false
  var lastSave = 0L

  override def run(): Unit = while (!isInterrupted) {
    Thread.sleep(15000)
    if (BoardSession.board != null && BoardSession.board.file != null) {
      saving = true
      BoardSession.board.synchronized(BoardSession.board.save())
      saving = false
      lastSave = System.currentTimeMillis()
    }
  }
}
