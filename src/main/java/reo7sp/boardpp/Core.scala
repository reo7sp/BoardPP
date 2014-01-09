package reo7sp.boardpp

import reo7sp.boardpp.ui.{CrashFrame, Renderer}

/**
 * Created by reo7sp on 11/27/13 at 4:01 PM
 */
object Core extends App {
  val version = 0.1
  val name = "Board++ " + version + " by Reo_SP"

  try {
    SaveThread.start()
    Renderer.init()
  } catch {
    case e: Throwable => CrashFrame.crash(e)
  }
}
