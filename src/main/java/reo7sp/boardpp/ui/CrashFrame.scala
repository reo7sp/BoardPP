package reo7sp.boardpp.ui

import javax.swing.{JScrollPane, JTextArea, JFrame}
import javax.swing.border.EmptyBorder
import java.awt.{Frame, Graphics, Font, Color}
import reo7sp.boardpp.Core

/**
 * Created by reo7sp on 12/1/13 at 10:00 PM
 */
object CrashFrame extends JFrame(Core.name) {
  var crashed = false

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

    override def paint(g: Graphics): Unit = {
      if (img != null) g.drawImage(img, getWidth - img.getWidth(null), getHeight - img.getHeight(null), null)
      g.setColor(new Color(0, 0, 0, 0.75F))
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
  setContentPane(new JScrollPane(textArea))

  def crash(e: Throwable) {
    if (!crashed) {
      val s = new StringBuilder
      s ++= "The program crashed because of this unhandled exception:\n\n"
      s ++= e.toString + "\n"
      e.getStackTrace.foreach(s ++= "    at " + _.toString + "\n")
      if (e.getCause != null) s ++= causeReport(e.getCause)
      s ++= "\n\nSend this to omorozenkov@gmail.com, so we could fix it"
      s ++= "\n\nGood luck!"

      e.printStackTrace()
      textArea.setText(s.toString())
      textArea.setCaretPosition(0)
      crashed = true
      setVisible(true)
    } else {
      toFront()
    }

    try {
      Renderer.container.pause()
    } catch {
      case e: Throwable =>
    }
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
