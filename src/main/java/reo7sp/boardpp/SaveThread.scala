package reo7sp.boardpp

/**
 * Created by reo7sp on 1/1/14 at 4:12 PM
 */
object SaveThread extends Thread("SaveThread") {
  var saving = false
  var lastSave = 0L

  override def run(): Unit = while (!isInterrupted) {
    Thread.sleep(15000)
    if (Canvas.board != null && Canvas.board.file != null) {
      saving = true
      Canvas.board.synchronized(Canvas.board.save())
      saving = false
      lastSave = System.currentTimeMillis()
    }
  }
}
