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

import reo7sp.boardpp.board.{BoardSession, BoardRule, BoardPage}
import javax.swing._
import java.awt.BorderLayout
import java.awt.event._
import reo7sp.boardpp.util.Colors
import javax.swing.event.{DocumentEvent, DocumentListener, ChangeEvent, ChangeListener}
import reo7sp.boardpp.board.brul.{ShowMsgOnLineRule, ShowMsgOnClickRule}
import reo7sp.boardpp.ui.widgets.awt.{FancyButton, FancyTextField}
import reo7sp.boardpp.Rules
import reo7sp.boardpp.ui.ShapeGetter.ShapeListener

/**
 * Created by reo7sp on 1/3/14 at 1:15 AM
 */
case class BoardRulesEditor(page: BoardPage) extends JFrame("Редактирование правил") {
  var curBtnID = -1
  var curRule: BoardRule = null

  val rulesList = new JPanel
  rulesList.setLayout(new BoxLayout(rulesList, BoxLayout.PAGE_AXIS))
  add(new JScrollPane(rulesList))

  for (rule <- BoardSession.board.curPage.rules) addRule(rule)

  val buttonsPanel = new JPanel
  add(buttonsPanel, BorderLayout.SOUTH)

  val addButton = new FancyButton
  addButton.setIcon(ImageFactory.getAwtIcon("new.png"))
  addButton.setToolTipText("Добавить")
  addButton.addActionListener(new ActionListener {
    def actionPerformed(p1: ActionEvent): Unit = BoardRulesEditor.showAdder(BoardRulesEditor.this)
  })
  buttonsPanel.add(addButton)

  val editButton = new FancyButton
  editButton.setIcon(ImageFactory.getAwtIcon("edit.png"))
  editButton.setToolTipText("Изменить")
  editButton.addActionListener(new ActionListener {
    def actionPerformed(p1: ActionEvent): Unit = if (curRule != null) {
      BoardRulesEditor.showEditor(BoardRulesEditor.this, curRule)
    }
  })
  buttonsPanel.add(editButton)

  val deleteButton = new FancyButton
  deleteButton.setIcon(ImageFactory.getAwtIcon("delete.png"))
  deleteButton.setToolTipText("Удалить")
  deleteButton.addActionListener(new ActionListener {
    def actionPerformed(p1: ActionEvent): Unit = if (curRule != null) {
      page -= curRule
      rulesList.remove(curBtnID)
      validate()
      curRule = null
      curBtnID = -1
      updBtns()
    }
  })
  buttonsPanel.add(deleteButton)

  setSize(800, 600)
  setLocationRelativeTo(null)
  setVisible(true)

  def addRule(rule: BoardRule) = {
    val btnID = rulesList.getComponentCount
    val btn = new FancyButton
    btn.setText("Если " + rule.conditionString + ", тогда " + rule.actionString)
    if (curBtnID == -1) {
      curBtnID = btnID
      curRule = rule
      updBtns()
    }
    btn.setAlignmentX(0.5F)
    btn.addActionListener(new ActionListener {
      def actionPerformed(p1: ActionEvent): Unit = {
        curBtnID = btnID
        curRule = rule
        updBtns()
      }
    })
    rulesList.add(btn)
    validate()
    repaint()
    btn
  }

  def updBtns(): Unit = {
    var i = 0
    for (c <- rulesList.getComponents) {
      c.setBackground(Colors.toAwt(if (i == curBtnID) Colors.blue else Colors.gray))
      i += 1
    }
    repaint()
  }
}

object BoardRulesEditor {
  private def showAdder(window: BoardRulesEditor): Unit = {
    window.setVisible(false)

    val frame = new JFrame("Выберите тип правила")
    frame.setSize(800, 600)
    frame.setLocationRelativeTo(null)

    def onRuleIDFound(ruleID: Int): Unit = {
      frame.dispose()
      window.setVisible(true)
      val rule = ruleID match {
        case -1 => null
        case Rules.showMsgOnClickRule => new ShowMsgOnClickRule(window.page.lastRuleID + 1)
        case Rules.showMsgOnLineRule => new ShowMsgOnLineRule(window.page.lastRuleID + 1)
      }
      if (rule == null) return

      if (window.curRule != rule) window.curBtnID = -1
      var btn: JButton = if (window.curBtnID == -1) null else window.rulesList.getComponent(window.curBtnID).asInstanceOf[JButton]
      if (btn == null) {
        window.page += rule
        btn = window.addRule(rule)
      }
      btn.setText("Если " + rule.conditionString + ", тогда " + rule.actionString)
      window.updBtns()

      showEditor(window, rule)
    }

    val rulesList = new JPanel
    rulesList.setLayout(new BoxLayout(rulesList, BoxLayout.PAGE_AXIS))
    frame.add(new JScrollPane(rulesList))

    var i = 1
    for (s <- Rules.stringArray) {
      val ruleID = i
      val btn = new FancyButton
      btn.setText(s)
      btn.setAlignmentX(0.5F)
      btn.addActionListener(new ActionListener {
        def actionPerformed(p1: ActionEvent): Unit = onRuleIDFound(ruleID)
      })
      rulesList.add(btn)
      i += 1
    }

    val cancelButton = new FancyButton
    cancelButton.setText("Отмена")
    cancelButton.addActionListener(new ActionListener {
      def actionPerformed(p1: ActionEvent): Unit = {
        frame.dispose()
        window.setVisible(true)
      }
    })
    frame.add(cancelButton, BorderLayout.SOUTH)

    frame.setVisible(true)
  }

  private def showEditor(window: BoardRulesEditor, rule: BoardRule): Unit = BoardRuleEditor(window, rule)

  private case class BoardRuleEditor(window: BoardRulesEditor, rule: BoardRule) {
    window.setVisible(false)

    val frame = new JFrame("Редактирование правила")
    frame.addWindowListener(new WindowAdapter {
      override def windowClosing(e: WindowEvent): Unit = window.setVisible(true)
    })
    frame.setSize(400, 600)
    frame.setLocationRelativeTo(null)

    rule match {
      case brul: ShowMsgOnClickRule => showEditor(brul)
      case brul: ShowMsgOnLineRule => showEditor(brul)
    }

    val buttonsPanel = new JPanel
    frame.add(buttonsPanel, BorderLayout.SOUTH)

    val cancelButton = new FancyButton
    cancelButton.setIcon(ImageFactory.getAwtIcon("cancel.png"))
    cancelButton.setToolTipText("Отмена")
    cancelButton.addActionListener(new ActionListener {
      def actionPerformed(p1: ActionEvent): Unit = {
        frame.dispose()
        window.setVisible(true)
      }
    })
    buttonsPanel.add(cancelButton)

    val saveButton = new FancyButton
    saveButton.setIcon(ImageFactory.getAwtIcon("ok.png"))
    saveButton.setToolTipText("Сохранить")
    saveButton.addActionListener(new ActionListener {
      def actionPerformed(p1: ActionEvent): Unit = {
        update()
        frame.dispose()
        window.setVisible(true)
      }
    })
    buttonsPanel.add(saveButton)

    frame.setVisible(true)

    def update(): Unit = {
      if (window.curRule != rule) window.curBtnID = -1
      var btn: JButton = if (window.curBtnID == -1) null else window.rulesList.getComponent(window.curBtnID).asInstanceOf[JButton]
      if (btn == null) {
        window.page += rule
        btn = window.addRule(rule)
      }
      btn.setText("Если " + rule.conditionString + ", тогда " + rule.actionString)
      window.updBtns()
    }

    def showEditor(brul: ShowMsgOnClickRule): Unit = {
      val msgEdit = new FancyTextField
      msgEdit.setText(brul.msg)
      msgEdit.getDocument.addDocumentListener(new DocumentListener {
        def insertUpdate(p1: DocumentEvent): Unit = brul.msg = msgEdit.getText

        def changedUpdate(p1: DocumentEvent): Unit = brul.msg = msgEdit.getText

        def removeUpdate(p1: DocumentEvent): Unit = brul.msg = msgEdit.getText
      })
      frame.add(msgEdit, BorderLayout.NORTH)

      val coordsPanel = new JPanel
      coordsPanel.setLayout(null)
      frame.add(coordsPanel)

      // ---

      val x1Label = new JLabel("X1:")
      x1Label.setBounds(40, 10, 50, 25)
      coordsPanel.add(x1Label)

      val x1Text = new JSpinner(new SpinnerNumberModel(brul.x1, 0, 9999, 10))
      x1Text.setBounds(100, 10, 50, 25)
      x1Text.addChangeListener(new ChangeListener {
        def stateChanged(p1: ChangeEvent): Unit = brul.x1 = x1Text.getValue.toString.toInt
      })
      coordsPanel.add(x1Text)

      val y1Label = new JLabel("Y1:")
      y1Label.setBounds(200, 10, 50, 25)
      coordsPanel.add(y1Label)

      val y1Text = new JSpinner(new SpinnerNumberModel(brul.y1, 0, 9999, 10))
      y1Text.setBounds(260, 10, 50, 25)
      y1Text.addChangeListener(new ChangeListener {
        def stateChanged(p1: ChangeEvent): Unit = brul.y1 = y1Text.getValue.toString.toInt
      })
      coordsPanel.add(y1Text)

      // ---

      val x2Label = new JLabel("X2:")
      x2Label.setBounds(40, 60, 50, 25)
      coordsPanel.add(x2Label)

      val x2Text = new JSpinner(new SpinnerNumberModel(brul.x2, 0, 9999, 10))
      x2Text.setBounds(100, 60, 50, 25)
      x2Text.addChangeListener(new ChangeListener {
        def stateChanged(p1: ChangeEvent): Unit = brul.x2 = x2Text.getValue.toString.toInt
      })
      coordsPanel.add(x2Text)

      val y2Label = new JLabel("Y2:")
      y2Label.setBounds(200, 60, 50, 25)
      coordsPanel.add(y2Label)

      val y2Text = new JSpinner(new SpinnerNumberModel(brul.y2, 0, 9999, 10))
      y2Text.setBounds(260, 60, 50, 25)
      y2Text.addChangeListener(new ChangeListener {
        def stateChanged(p1: ChangeEvent): Unit = brul.y2 = y2Text.getValue.toString.toInt
      })
      coordsPanel.add(y2Text)

      // ---

      val rectButton = new FancyButton
      rectButton.setIcon(ImageFactory.getAwtIcon("cursor.png"))
      rectButton.setBounds(320, 40, 50, 50)
      rectButton.addActionListener(new ActionListener {
        def actionPerformed(p1: ActionEvent): Unit = {
          frame.setVisible(false)

          ShapeGetter.+=(new ShapeListener {
            def onRect(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
              x1Text.setValue(x1)
              y1Text.setValue(y1)
              x2Text.setValue(x2)
              y2Text.setValue(y2)

              brul.x1 = x1Text.getValue.toString.toInt
              brul.y1 = y1Text.getValue.toString.toInt
              brul.x2 = x2Text.getValue.toString.toInt
              brul.y2 = y2Text.getValue.toString.toInt

              frame.setVisible(true)
            }
          })
        }
      })
      coordsPanel.add(rectButton)
    }

    def showEditor(brul: ShowMsgOnLineRule): Unit = {
      val msgEdit = new FancyTextField
      msgEdit.setText(brul.msg)
      msgEdit.getDocument.addDocumentListener(new DocumentListener {
        def insertUpdate(p1: DocumentEvent): Unit = brul.msg = msgEdit.getText

        def changedUpdate(p1: DocumentEvent): Unit = brul.msg = msgEdit.getText

        def removeUpdate(p1: DocumentEvent): Unit = brul.msg = msgEdit.getText
      })
      frame.add(msgEdit, BorderLayout.NORTH)

      val coordsPanel = new JPanel
      coordsPanel.setLayout(null)
      frame.add(coordsPanel)

      // ---

      val x11Label = new JLabel("X1:")
      x11Label.setBounds(40, 10, 50, 25)
      coordsPanel.add(x11Label)

      val x11Text = new JSpinner(new SpinnerNumberModel(brul.x11, 0, 9999, 10))
      x11Text.setBounds(100, 10, 50, 25)
      x11Text.addChangeListener(new ChangeListener {
        def stateChanged(p1: ChangeEvent): Unit = brul.x11 = x11Text.getValue.toString.toInt
      })
      coordsPanel.add(x11Text)

      val y11Label = new JLabel("Y1:")
      y11Label.setBounds(200, 10, 50, 25)
      coordsPanel.add(y11Label)

      val y11Text = new JSpinner(new SpinnerNumberModel(brul.y11, 0, 9999, 10))
      y11Text.setBounds(260, 10, 50, 25)
      y11Text.addChangeListener(new ChangeListener {
        def stateChanged(p1: ChangeEvent): Unit = brul.y11 = y11Text.getValue.toString.toInt
      })
      coordsPanel.add(y11Text)

      // ---

      val x12Label = new JLabel("X2:")
      x12Label.setBounds(40, 60, 50, 25)
      coordsPanel.add(x12Label)

      val x12Text = new JSpinner(new SpinnerNumberModel(brul.x12, 0, 9999, 10))
      x12Text.setBounds(100, 60, 50, 25)
      x12Text.addChangeListener(new ChangeListener {
        def stateChanged(p1: ChangeEvent): Unit = brul.x12 = x12Text.getValue.toString.toInt
      })
      coordsPanel.add(x12Text)

      val y12Label = new JLabel("Y2:")
      y12Label.setBounds(200, 60, 50, 25)
      coordsPanel.add(y12Label)

      val y12Text = new JSpinner(new SpinnerNumberModel(brul.y12, 0, 9999, 10))
      y12Text.setBounds(260, 60, 50, 25)
      y12Text.addChangeListener(new ChangeListener {
        def stateChanged(p1: ChangeEvent): Unit = brul.y12 = y12Text.getValue.toString.toInt
      })
      coordsPanel.add(y12Text)

      // ---

      val rect1Button = new FancyButton
      rect1Button.setIcon(ImageFactory.getAwtIcon("cursor.png"))
      rect1Button.setBounds(320, 40, 50, 50)
      rect1Button.addActionListener(new ActionListener {
        def actionPerformed(p1: ActionEvent): Unit = {
          frame.setVisible(false)

          ShapeGetter.+=(new ShapeListener {
            def onRect(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
              x11Text.setValue(x1)
              y11Text.setValue(y1)
              x12Text.setValue(x2)
              y12Text.setValue(y2)

              brul.x11 = x11Text.getValue.toString.toInt
              brul.y11 = y11Text.getValue.toString.toInt
              brul.x12 = x12Text.getValue.toString.toInt
              brul.y12 = y12Text.getValue.toString.toInt

              frame.setVisible(true)
            }
          })
        }
      })
      coordsPanel.add(rect1Button)

      // ---

      val x21Label = new JLabel("X1:")
      x21Label.setBounds(40, 210, 50, 25)
      coordsPanel.add(x21Label)

      val x21Text = new JSpinner(new SpinnerNumberModel(brul.x21, 0, 9999, 10))
      x21Text.setBounds(100, 210, 50, 25)
      x21Text.addChangeListener(new ChangeListener {
        def stateChanged(p1: ChangeEvent): Unit = brul.x21 = x21Text.getValue.toString.toInt
      })
      coordsPanel.add(x21Text)

      val y21Label = new JLabel("Y1:")
      y21Label.setBounds(200, 210, 50, 25)
      coordsPanel.add(y21Label)

      val y21Text = new JSpinner(new SpinnerNumberModel(brul.y21, 0, 9999, 10))
      y21Text.setBounds(260, 210, 50, 25)
      y21Text.addChangeListener(new ChangeListener {
        def stateChanged(p1: ChangeEvent): Unit = brul.y21 = y21Text.getValue.toString.toInt
      })
      coordsPanel.add(y21Text)

      // ---

      val x22Label = new JLabel("X2:")
      x22Label.setBounds(40, 260, 50, 25)
      coordsPanel.add(x22Label)

      val x22Text = new JSpinner(new SpinnerNumberModel(brul.x22, 0, 9999, 10))
      x22Text.setBounds(100, 260, 50, 25)
      x22Text.addChangeListener(new ChangeListener {
        def stateChanged(p1: ChangeEvent): Unit = brul.x22 = x22Text.getValue.toString.toInt
      })
      coordsPanel.add(x22Text)

      val y22Label = new JLabel("Y2:")
      y22Label.setBounds(200, 260, 50, 25)
      coordsPanel.add(y22Label)

      val y22Text = new JSpinner(new SpinnerNumberModel(brul.y22, 0, 9999, 10))
      y22Text.setBounds(260, 260, 50, 25)
      y22Text.addChangeListener(new ChangeListener {
        def stateChanged(p1: ChangeEvent): Unit = brul.y22 = y22Text.getValue.toString.toInt
      })
      coordsPanel.add(y22Text)

      // ---

      val rect2Button = new FancyButton
      rect2Button.setIcon(ImageFactory.getAwtIcon("cursor.png"))
      rect2Button.setBounds(320, 240, 50, 50)
      rect2Button.addActionListener(new ActionListener {
        def actionPerformed(p1: ActionEvent): Unit = {
          frame.setVisible(false)

          ShapeGetter.+=(new ShapeListener {
            def onRect(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
              x21Text.setValue(x1)
              y21Text.setValue(y1)
              x22Text.setValue(x2)
              y22Text.setValue(y2)

              brul.x21 = x21Text.getValue.toString.toInt
              brul.y21 = y21Text.getValue.toString.toInt
              brul.x22 = x22Text.getValue.toString.toInt
              brul.y22 = y22Text.getValue.toString.toInt

              frame.setVisible(true)
            }
          })
        }
      })
      coordsPanel.add(rect2Button)
    }
  }

}