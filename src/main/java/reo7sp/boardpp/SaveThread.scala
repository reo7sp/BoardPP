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
