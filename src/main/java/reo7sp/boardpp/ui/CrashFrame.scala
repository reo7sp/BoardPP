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

import javax.swing.{JScrollPane, JTextArea, JFrame}
import javax.swing.border.EmptyBorder
import java.awt._
import reo7sp.boardpp.Core

/**
 * Created by reo7sp on 12/1/13 at 10:00 PM
 */
object CrashFrame extends JFrame(Core.name) {
  var crashed = false
  var count = 0

  setSize(800, 600)
  setLocationRelativeTo(null)
  setExtendedState(getExtendedState | Frame.MAXIMIZED_BOTH)
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

  private[this] val textArea = new JTextArea {
    val img = try {
      ImageFactory.getAwtIcon("i_dunno_lol.png").getImage
    } catch {
      case e: Throwable => null
    }
    val color0 = new Color(0, 0, 64)
    val color1 = new Color(0, 0, 0, 0.5F)

    override def paint(rawG: Graphics): Unit = {
      val g = rawG.asInstanceOf[Graphics2D]

      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

      g.setColor(color0)
      g.fillRect(0, 0, getWidth, getHeight)
      if (img != null) g.drawImage(img, getWidth - img.getWidth(null), getHeight - img.getHeight(null), null)

      val freeMem = Runtime.getRuntime.freeMemory / 1024 / 1024
      val totalMem = Runtime.getRuntime.totalMemory / 1024 / 1024
      val usedMem = totalMem - freeMem
      g.setColor(Color.WHITE)
      g.drawString("Mem: " + usedMem + "M/" + totalMem + "M", 64, getHeight - 96)
      g.drawString("Count: " + count, 64, getHeight - 64)

      g.setColor(color1)
      g.fillRect(0, 0, getWidth, getHeight)

      super.paint(g)
    }
  }
  textArea.setEditable(false)
  textArea.setBorder(new EmptyBorder(8, 8, 8, 8))
  textArea.setFont(new Font(null, Font.BOLD, 14))
  textArea.setForeground(Color.WHITE)
  textArea.setOpaque(false)
  textArea.setBackground(Color.BLACK)

  private[this] val scrollPane = new JScrollPane(textArea)
  scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0))
  setContentPane(scrollPane)

  def crash(e: Throwable) {
    if (!crashed) {
      e.printStackTrace()

      val s = new StringBuilder
      s ++= "The program crashed because of this unhandled exception:\n\n"
      s ++= e.toString + "\n"
      e.getStackTrace.foreach(s ++= "    at " + _.toString + "\n")
      if (e.getCause != null) s ++= causeReport(e.getCause)
      s ++= "\nSend this to omorozenkov@gmail.com, so we could fix it\n"
      s ++= "\nGood luck!\n"

      textArea.setText(s.toString())
      textArea.setCaretPosition(0)
      crashed = true
      setVisible(true)

      new Thread() {
        override def run(): Unit = while (!isInterrupted) {
          textArea.repaint()
          Thread.sleep(500)
        }
      }.start()
    } else {
      toFront()
    }
    count += 1
  }

  private[this] def causeReport(e: Throwable): String = {
    val s = new StringBuilder
    s ++= "Caused by " + e.toString + "\n"
    for (i <- e.getStackTrace)
      s ++= "    at " + i.toString + "\n"
    if (e.getCause != null)
      s ++= causeReport(e.getCause)
    s.toString()
  }
}
