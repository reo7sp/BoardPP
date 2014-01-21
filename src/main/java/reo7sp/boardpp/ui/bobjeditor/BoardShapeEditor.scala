package reo7sp.boardpp.ui.bobjeditor

import javax.swing._
import javax.swing.event.{ChangeEvent, ChangeListener}
import javax.swing.border.EmptyBorder
import java.awt.event.{MouseEvent, MouseAdapter, ActionEvent, ActionListener}
import reo7sp.boardpp.ui.ImageFactory
import java.awt._
import reo7sp.boardpp.board.bobj.BoardShape
import reo7sp.boardpp.ui.widgets.awt.FancyButton
import reo7sp.boardpp.util.Colors
import reo7sp.boardpp.board.BoardSession

/**
 * Created by reo7sp on 1/1/14 at 2:08 PM
 */
case class BoardShapeEditor(bobj: BoardShape) extends JFrame("Редактирование") {
  val bobjColor1 = bobj.color1
  val bobjColor2 = bobj.color2
  val bobjX = bobj.x
  val bobjY = bobj.y
  val bobjW = bobj.w
  val bobjH = bobj.h

  val root = new JPanel()
  root.setLayout(new BorderLayout)
  setContentPane(root)

  val editPanel = new JSplitPane
  editPanel.setOneTouchExpandable(false)
  editPanel.setResizeWeight(0.5)
  root.add(editPanel)

  val color1Button = new JButton()
  updateColorButton(color1Button, Colors.toAwt(bobj.color1))
  color1Button.addMouseListener(new MouseAdapter {
    override def mouseClicked(p1: MouseEvent): Unit = {
      val color = JColorChooser.showDialog(null, "Выберите цвет", Colors.toAwt(bobj.color1))
      if (color != null) bobj.color1 = Colors.toGdx(color)
      updateColorButton(color1Button, Colors.toAwt(bobj.color1))
      update()
    }
  })
  editPanel.setLeftComponent(color1Button)

  val color2Button = new JButton()
  updateColorButton(color2Button, Colors.toAwt(bobj.color2))
  color2Button.addMouseListener(new MouseAdapter {
    override def mouseClicked(p1: MouseEvent): Unit = {
      val color = JColorChooser.showDialog(null, "Выберите цвет", Colors.toAwt(bobj.color2))
      if (color != null) bobj.color2 = Colors.toGdx(color)
      updateColorButton(color2Button, Colors.toAwt(bobj.color2))
      update()
    }
  })
  editPanel.setRightComponent(color2Button)

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

  coordsPanel.add(new JLabel("  W: "))
  val wText = new JSpinner(new SpinnerNumberModel(bobj.w, 10, 9999, 10))
  wText.addChangeListener(new ChangeListener {
    def stateChanged(p1: ChangeEvent): Unit = update()
  })
  coordsPanel.add(wText)

  coordsPanel.add(new JLabel("  H: "))
  val hText = new JSpinner(new SpinnerNumberModel(bobj.h, 10, 9999, 10))
  hText.addChangeListener(new ChangeListener {
    def stateChanged(p1: ChangeEvent): Unit = update()
  })
  coordsPanel.add(hText)

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
      bobj.color1 = bobjColor1
      bobj.color2 = bobjColor2
      bobj.x = bobjX
      bobj.y = bobjY
      bobj.w = bobjW
      bobj.h = bobjH
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
    bobj.w = wText.getValue.toString.toInt
    bobj.h = hText.getValue.toString.toInt
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
