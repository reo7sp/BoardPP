package reo7sp.boardpp.ui.bobjeditor

import javax.swing._
import javax.swing.event.{ChangeEvent, ChangeListener, DocumentEvent, DocumentListener}
import javax.swing.border.EmptyBorder
import java.awt.event.{ActionEvent, ActionListener}
import reo7sp.boardpp.util.SimpleFileFilter
import reo7sp.boardpp.ui.ImageFactory
import java.awt.{Color, BorderLayout}
import reo7sp.boardpp.board.bobj.BoardImage
import reo7sp.boardpp.ui.widgets.awt.{FancyButton, FancyTextField}
import reo7sp.boardpp.board.BoardSession
import java.net.URL

/**
 * Created by reo7sp on 1/1/14 at 2:08 PM
 */
case class BoardImageEditor(bobj: BoardImage) extends JFrame("Редактирование") {
  val bobjUrl = bobj.url
  val bobjX = bobj.x
  val bobjY = bobj.y
  val bobjW = bobj.w
  val bobjH = bobj.h

  val root = new JPanel()
  root.setLayout(new BorderLayout)
  setContentPane(root)

  val editPanel = new JPanel
  editPanel.setBackground(Color.WHITE)
  editPanel.setLayout(new BorderLayout)
  root.add(editPanel)

  val editArea = new FancyTextField
  editArea.setText(if (bobj.url != null) bobj.url.toString else "")
  editArea.getDocument.addDocumentListener(new DocumentListener {
    def insertUpdate(p1: DocumentEvent): Unit = update()

    def changedUpdate(p1: DocumentEvent): Unit = update()

    def removeUpdate(p1: DocumentEvent): Unit = update()
  })
  editPanel.add(editArea)

  val openFileButton = new FancyButton
  openFileButton.setText("Открыть")
  openFileButton.setBackground(Color.WHITE)
  openFileButton.setBorder(new EmptyBorder(0, 8, 0, 8))
  openFileButton.addActionListener(new ActionListener {
    def actionPerformed(p1: ActionEvent): Unit = {
      val chooser = new JFileChooser
      chooser.addChoosableFileFilter(SimpleFileFilter("jpg"))
      chooser.addChoosableFileFilter(SimpleFileFilter("png"))
      chooser.addChoosableFileFilter(SimpleFileFilter("gif"))
      chooser.addChoosableFileFilter(SimpleFileFilter("bmp"))
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        editArea.setText(chooser.getSelectedFile.toURI.toURL.toString)
      }
    }
  })
  editPanel.add(openFileButton, BorderLayout.EAST)

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
  val wText = new JSpinner(new SpinnerNumberModel(bobj.w, 0, 9999, 10))
  wText.addChangeListener(new ChangeListener {
    def stateChanged(p1: ChangeEvent): Unit = update()
  })
  coordsPanel.add(wText)

  coordsPanel.add(new JLabel("  H: "))
  val hText = new JSpinner(new SpinnerNumberModel(bobj.h, 0, 9999, 10))
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
      bobj.url = bobjUrl
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
    bobj.url = try {
      new URL(editArea.getText)
    } catch {
      case _: Throwable => null
    }
    bobj.x = xText.getValue.toString.toInt
    bobj.y = yText.getValue.toString.toInt
    bobj.w = wText.getValue.toString.toInt
    bobj.h = hText.getValue.toString.toInt
    repaint()
  }
}
