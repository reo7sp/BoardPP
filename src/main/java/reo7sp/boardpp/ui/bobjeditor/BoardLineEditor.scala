package reo7sp.boardpp.ui.bobjeditor

import javax.swing._
import reo7sp.boardpp.Canvas
import java.awt.event.{MouseEvent, MouseAdapter, ActionEvent, ActionListener}
import javax.swing.event.{ChangeEvent, ChangeListener}
import javax.swing.border.EmptyBorder
import reo7sp.boardpp.ui.ImageFactory
import java.awt.{Color, FlowLayout, BorderLayout}
import reo7sp.boardpp.board.bobj.BoardLine
import reo7sp.boardpp.util.Colors
import reo7sp.boardpp.ui.widgets.awt.FancyButton

/**
 * Created by reo7sp on 12/27/13 at 9:49 PM
 */
case class BoardLineEditor(bobj: BoardLine) extends JFrame("Редактирование") {
  val bobjX = bobj.x
  val bobjY = bobj.y
  val bobjX2 = bobj.x2
  val bobjY2 = bobj.y2
  val bobjColor = bobj.color
  val bobjThickness = bobj.thickness

  val root = new JPanel()
  root.setLayout(new BorderLayout)
  setContentPane(root)

  val editPanel = new JPanel
  editPanel.setLayout(new FlowLayout(FlowLayout.LEFT))
  root.add(editPanel)

  editPanel.add(new JLabel("Толщина: "))
  val thicknessEdit = new JSpinner(new SpinnerNumberModel(bobjThickness, 0.0, 99.9, 0.1))
  thicknessEdit.addChangeListener(new ChangeListener {
    def stateChanged(p1: ChangeEvent): Unit = update()
  })
  editPanel.add(thicknessEdit)

  editPanel.add(new JLabel("   Цвет: "))
  val colorButton = new JButton()
  updateColorButton(colorButton, Colors.toAWT(bobj.color))
  colorButton.addMouseListener(new MouseAdapter {
    override def mouseClicked(p1: MouseEvent): Unit = {
      val color = JColorChooser.showDialog(null, "Выберите цвет", Colors.toAWT(bobj.color))
      if (color != null) bobj.color = Colors.toSlick(color)
      updateColorButton(colorButton, Colors.toAWT(bobj.color))
      update()
    }
  })
  editPanel.add(colorButton)

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

  coordsPanel.add(new JLabel("  X2: "))
  val x2Text = new JSpinner(new SpinnerNumberModel(bobj.x2, 0, 9999, 10))
  x2Text.addChangeListener(new ChangeListener {
    def stateChanged(p1: ChangeEvent): Unit = update()
  })
  coordsPanel.add(x2Text)

  coordsPanel.add(new JLabel("  Y2: "))
  val y2Text = new JSpinner(new SpinnerNumberModel(bobj.y2, 0, 9999, 10))
  y2Text.addChangeListener(new ChangeListener {
    def stateChanged(p1: ChangeEvent): Unit = update()
  })
  coordsPanel.add(y2Text)

  val buttonsPanel = new JPanel
  bottomPanel.add(buttonsPanel, BorderLayout.EAST)

  val deleteButton = new FancyButton
  deleteButton.setIcon(ImageFactory.getAwtIcon("delete.png"))
  deleteButton.addActionListener(new ActionListener {
    def actionPerformed(p1: ActionEvent): Unit = {
      Canvas.board.curPage -= bobj
      dispose()
    }
  })
  buttonsPanel.add(deleteButton)

  val cancelButton = new FancyButton
  cancelButton.setIcon(ImageFactory.getAwtIcon("cancel.png"))
  cancelButton.addActionListener(new ActionListener {
    def actionPerformed(p1: ActionEvent): Unit = {
      bobj.x = bobjX
      bobj.y = bobjY
      bobj.x2 = bobjX2
      bobj.y2 = bobjY2
      bobj.color = bobjColor
      bobj.thickness = bobjThickness
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
    bobj.x = xText.getValue.toString.toInt
    bobj.y = yText.getValue.toString.toInt
    bobj.x2 = x2Text.getValue.toString.toInt
    bobj.y2 = y2Text.getValue.toString.toInt
    bobj.thickness = thicknessEdit.getValue.toString.toFloat
    bobj.page.invalidate()
    repaint()
  }

  def updateColorButton(btn: JButton, color: Color): Unit = {
    var zeros = ""
    val s = Integer.toHexString(color.getRGB & 0xffffff)
    for (i <- 0 until 6 - s.length) {
      zeros += "0"
    }
    btn.setText(zeros + s)
    btn.setBackground(color)
  }
}
