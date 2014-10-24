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

package reo7sp.boardpp.ui.bobjeditor

import javax.swing._
import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.event.{ChangeEvent, ChangeListener, DocumentEvent, DocumentListener}
import javax.swing.border.EmptyBorder
import reo7sp.boardpp.util.CharMap
import reo7sp.boardpp.ui.ImageFactory
import java.awt._
import reo7sp.boardpp.board.bobj.BoardText
import reo7sp.boardpp.ui.widgets.awt.{FancyButton, FancyTextField}
import reo7sp.boardpp.board.BoardSession

/**
 * Created by reo7sp on 12/27/13 at 9:49 PM
 */
case class BoardTextEditor(bobj: BoardText) extends JFrame("Редактирование") {
  val bobjContent = bobj.content
  val bobjX = bobj.x
  val bobjY = bobj.y

  val root = new JPanel()
  root.setLayout(new BorderLayout)
  setContentPane(root)

  val editPanel = new JPanel
  editPanel.setLayout(new BorderLayout)
  root.add(editPanel)

  val editArea = new FancyTextField {
    override def paint(g: Graphics): Unit = {
      super.paint(g)
      val color0 = new Color(0, 255, 0, 64)
      val color1 = new Color(0, 255, 255, 64)
      val color2 = new Color(255, 0, 0, 64)
      val fontMetrics = getFontMetrics(getFont)
      val s = getText
      var textX = 8
      var first$X = -1
      var backslashMode = false
      for (ch <- s) {
        if (ch == '\\') {
          backslashMode = true
          g.setColor(color2)
          g.fillRect(textX, 0, fontMetrics.charWidth(ch), getHeight)
        } else if (ch == '$') {
          if (!backslashMode) {
            g.setColor(color1)
            g.fillRect(textX, 0, fontMetrics.charWidth(ch), getHeight)
            if (first$X == -1) {
              first$X = textX + fontMetrics.charWidth(ch)
            } else {
              g.setColor(color0)
              g.fillRect(first$X, 0, textX - first$X, getHeight)
              first$X = -1
            }
          }
        }
        if (ch != '\\') backslashMode = false
        textX += fontMetrics.charWidth(ch)
      }
    }
  }
  editArea.setText(bobj.content)
  editArea.getDocument.addDocumentListener(new DocumentListener {
    def insertUpdate(p1: DocumentEvent): Unit = update()

    def changedUpdate(p1: DocumentEvent): Unit = update()

    def removeUpdate(p1: DocumentEvent): Unit = update()
  })
  editPanel.add(editArea)

  val addButton = new FancyButton
  addButton.setText(" * ")
  addButton.setBackground(Color.WHITE)
  addButton.setBorder(new EmptyBorder(0, 8, 0, 8))
  addButton.addActionListener(new ActionListener {
    def actionPerformed(p1: ActionEvent): Unit = new JFrame("Спец. символы") {
      val actionListener = new ActionListener {
        def actionPerformed(e: ActionEvent): Unit = editArea.setText(editArea.getText + e.getSource.asInstanceOf[JButton].getText)
      }

      val tabbedPane = new JTabbedPane
      newSpecCharPanel("Стрелки", CharMap.arrows)
      newSpecCharPanel("Треугольники", CharMap.triangles)
      newSpecCharPanel("Дроби", CharMap.ratios)
      newSpecCharPanel("Круги", CharMap.circles)
      newSpecCharPanel("Математика", CharMap.math)
      newSpecCharPanel("Деньги", CharMap.money)
      newSpecCharPanel("Погода", CharMap.weather)
      newSpecCharPanel("Да/Нет", CharMap.yesNo)
      newSpecCharPanel("Значки", CharMap.icons)
      newSpecCharPanel("Буквы", CharMap.letters)
      newSpecCharPanel("Звёзды", CharMap.stars)
      newSpecCharPanel("Музыка", CharMap.music)
      newSpecCharPanel("Зодиак", CharMap.zodiac)
      newSpecCharPanel("Смайлики", CharMap.smiley)
      add(tabbedPane)

      setSize(600, 800)
      setLocationRelativeTo(null)
      setResizable(false)
      setVisible(true)

      def newSpecCharPanel(name: String, chars: Array[String]) = {
        val panel = new JPanel
        panel.setLayout(new GridLayout(12, 24))
        for (c <- chars; if c.nonEmpty) {
          val button = new FancyButton
          button.setText(c)
          button.setFont(new Font(null, Font.PLAIN, 32))
          button.addActionListener(actionListener)
          panel.add(button)
        }
        tabbedPane.addTab(name, panel)
      }
    }
  })
  editPanel.add(addButton, BorderLayout.EAST)

  val bottomPanel = new JPanel
  bottomPanel.setLayout(new BorderLayout)
  bottomPanel.setBorder(new EmptyBorder(8, 16, 8, 16))
  root.add(bottomPanel, BorderLayout.SOUTH)

  val coordsPanel = new JPanel
  bottomPanel.add(coordsPanel, BorderLayout.WEST)

  coordsPanel.add(new JLabel("X: "))
  val xText = new JSpinner(new SpinnerNumberModel(bobj.x, 0, 9999, 10))
  xText.addChangeListener(new ChangeListener {
    def stateChanged(p1: ChangeEvent): Unit = update()
  })
  coordsPanel.add(xText)

  coordsPanel.add(new JLabel("  Y: "))
  val yText = new JSpinner(new SpinnerNumberModel(bobj.y, 0, 9999, 10))
  yText.addChangeListener(new ChangeListener {
    def stateChanged(p1: ChangeEvent): Unit = update()
  })
  coordsPanel.add(yText)

  val buttonsPanel = new JPanel
  bottomPanel.add(buttonsPanel, BorderLayout.EAST)

  val deleteButton = new FancyButton
  deleteButton.setIcon(ImageFactory.getAwtIcon("delete.png"))
  deleteButton.addActionListener(new ActionListener {
    def actionPerformed(p1: ActionEvent): Unit = {
      BoardSession.board.curPage -= bobj
      dispose()
    }
  })
  buttonsPanel.add(deleteButton)

  val cancelButton = new FancyButton
  cancelButton.setIcon(ImageFactory.getAwtIcon("cancel.png"))
  cancelButton.addActionListener(new ActionListener {
    def actionPerformed(p1: ActionEvent): Unit = {
      bobj.content = bobjContent
      bobj.x = bobjX
      bobj.y = bobjY
      dispose()
    }
  })
  buttonsPanel.add(cancelButton)

  val saveButton = new FancyButton
  saveButton.setIcon(ImageFactory.getAwtIcon("ok.png"))
  saveButton.addActionListener(new ActionListener {
    def actionPerformed(p1: ActionEvent): Unit = dispose()
  })
  buttonsPanel.add(saveButton)

  setSize(800, 125)
  setVisible(true)

  def update() {
    bobj.content = editArea.getText
    bobj.x = xText.getValue.toString.toInt
    bobj.y = yText.getValue.toString.toInt
    repaint()
  }
}
