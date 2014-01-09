package reo7sp.boardpp.ui

import reo7sp.boardpp._
import java.io.File
import javax.swing.filechooser.FileFilter
import reo7sp.boardpp.util.Colors
import reo7sp.boardpp.Canvas
import reo7sp.boardpp.util.SimpleFileFilter
import reo7sp.boardpp.board.{Board, BoardPage}
import reo7sp.boardpp.ui.widgets.my.{Slider, Button, Container}
import javax.swing.{JColorChooser, JFileChooser, JOptionPane}
import org.newdawn.slick.{Color, Graphics}

/**
 * Created by reo7sp on 11/30/13 at 10:24 PM
 */
object ToolPanel extends Container {
  val cellSize = 48

  width = 4 * cellSize
  background = Colors.gray

  // ---

  val createButton = new Button
  createButton.tooltip = "Создать"
  createButton.icon = ImageFactory.get("create.png")
  createButton.x = 0 * cellSize
  createButton.y = 0 * cellSize
  createButton.width = 1 * cellSize
  createButton.height = 1 * cellSize
  createButton.actionOnClick = () => {
    if (JOptionPane.showConfirmDialog(null, "Создать новую доску?", null, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
      Canvas.board.destroy()
      Canvas.board = Board.newBoard
    }
  }
  this += createButton

  val openButton = new Button
  openButton.tooltip = "Открыть"
  openButton.icon = ImageFactory.get("open.png")
  openButton.x = 1 * cellSize
  openButton.y = 0 * cellSize
  openButton.width = 1 * cellSize
  openButton.height = 1 * cellSize
  openButton.actionOnClick = () => {
    if (JOptionPane.showConfirmDialog(null, "Открыть другую доску?", null, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
      val chooser = new JFileChooser
      chooser.setFileFilter(SimpleFileFilter("json"))
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        Canvas.board.destroy()
        Canvas.board = Board.load(chooser.getSelectedFile)
      }
    }
  }
  this += openButton

  val saveButton = new Button
  saveButton.tooltip = "Сохранить"
  saveButton.icon = ImageFactory.get("save.png")
  saveButton.x = 2 * cellSize
  saveButton.y = 0 * cellSize
  saveButton.width = 1 * cellSize
  saveButton.height = 1 * cellSize
  saveButton.actionOnClick = () => {
    if (Canvas.board.file == null) {
      val chooser = new JFileChooser
      chooser.setFileFilter(new FileFilter {
        def getDescription: String = "JSON files (*.json)"

        def accept(f: File): Boolean = !f.isFile || f.getName.substring(f.getName.lastIndexOf(".") + 1) == "json"
      })
      if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        var f = chooser.getSelectedFile
        if (!f.getName.endsWith(".json")) f = new File(f.getAbsolutePath + ".json")
        Canvas.board.file = f
      }
    }
    Canvas.board.save()
  }
  this += saveButton

  val editElementsButton = new Button
  editElementsButton.tooltip = "Редактировать"
  editElementsButton.icon = ImageFactory.get("edit.png")
  editElementsButton.x = 3 * cellSize
  editElementsButton.y = 0 * cellSize
  editElementsButton.width = 1 * cellSize
  editElementsButton.height = 1 * cellSize
  editElementsButton.actionOnClick = () => {
    Canvas.editing = !Canvas.editing
    editElementsButton.background = if (Canvas.editing) Colors.orange else Colors.gray
    Canvas.board.curPage.updateSettings()
    Canvas.board.curPage.invalidate()
  }
  this += editElementsButton

  // ---

  val backButton = new Button
  backButton.tooltip = "На страницу назад"
  backButton.icon = ImageFactory.get("left.png")
  backButton.x = 0 * cellSize
  backButton.y = 1 * cellSize
  backButton.width = 1 * cellSize
  backButton.height = 1 * cellSize
  backButton.actionOnClick = () => if (Canvas.board.curPageID > 0) {
    Canvas.board.curPageID -= 1
    Canvas.board.curPage.updateSettings()
    Canvas.board.curPage.invalidate()
  }
  this += backButton

  val nextButton = new Button
  nextButton.tooltip = "На страницу вперёд"
  nextButton.icon = ImageFactory.get("right.png")
  nextButton.x = 1 * cellSize
  nextButton.y = 1 * cellSize
  nextButton.width = 1 * cellSize
  nextButton.height = 1 * cellSize
  nextButton.actionOnClick = () => {
    Canvas.board.curPageID += 1
    if (Canvas.board.curPageID == Canvas.board.pages.size) {
      Canvas.board += new BoardPage
    }
    Canvas.board.curPage.updateSettings()
    Canvas.board.curPage.invalidate()
  }
  this += nextButton

  val clearButton = new Button
  clearButton.tooltip = "Очистить/Удалить"
  clearButton.icon = ImageFactory.get("cancel.png")
  clearButton.x = 2 * cellSize
  clearButton.y = 1 * cellSize
  clearButton.width = 1 * cellSize
  clearButton.height = 1 * cellSize
  clearButton.actionOnClick = () => {
    if (Canvas.editing) {
      if (Canvas.board.pages.length > 1) {
        if (Canvas.board.curPageID != 0 && Canvas.board.curPageID == Canvas.board.pages.length - 1)
          Canvas.board.curPageID -= 1
        Canvas.board -= Canvas.board.curPage
        Canvas.board.curPage.updateSettings()
        Canvas.board.curPage.invalidate()
      }
    } else Canvas.board.curPage.clear()
  }
  this += clearButton

  val editRulesButton = new Button
  editRulesButton.tooltip = "Правила"
  editRulesButton.icon = ImageFactory.get("events.png")
  editRulesButton.x = 3 * cellSize
  editRulesButton.y = 1 * cellSize
  editRulesButton.width = 1 * cellSize
  editRulesButton.height = 1 * cellSize
  editRulesButton.actionOnClick = () => if (Canvas.editing) BoardRulesEditor(Canvas.board.curPage)
  this += editRulesButton

  // ---

  val cursorButton = new Button
  cursorButton.tooltip = "Курсор"
  cursorButton.icon = ImageFactory.get("cursor.png")
  cursorButton.x = 0 * cellSize
  cursorButton.y = 2 * cellSize
  cursorButton.width = 1 * cellSize
  cursorButton.height = 1 * cellSize
  cursorButton.actionOnClick = () => Canvas.tool = Tools.cursor
  this += cursorButton

  val penButton = new Button
  penButton.tooltip = "Ручка"
  penButton.icon = ImageFactory.get("pen.png")
  penButton.x = 1 * cellSize
  penButton.y = 2 * cellSize
  penButton.width = 1 * cellSize
  penButton.height = 1 * cellSize
  penButton.actionOnClick = () => Canvas.tool = Tools.pen
  this += penButton

  val highlighterButton = new Button
  highlighterButton.tooltip = "Маркер"
  highlighterButton.icon = ImageFactory.get("highlighter.png")
  highlighterButton.x = 2 * cellSize
  highlighterButton.y = 2 * cellSize
  highlighterButton.width = 1 * cellSize
  highlighterButton.height = 1 * cellSize
  highlighterButton.actionOnClick = () => Canvas.tool = Tools.highlighter
  this += highlighterButton

  val eraserButton = new Button
  eraserButton.tooltip = "Ластик"
  eraserButton.icon = ImageFactory.get("eraser.png")
  eraserButton.x = 3 * cellSize
  eraserButton.y = 2 * cellSize
  eraserButton.width = 1 * cellSize
  eraserButton.height = 1 * cellSize
  eraserButton.actionOnClick = () => Canvas.tool = Tools.eraser
  this += eraserButton

  val thicknessSlider = new Slider
  thicknessSlider.tooltip = "Толщина ручки"
  thicknessSlider.min = 2
  thicknessSlider.max = 20
  thicknessSlider.cur = Canvas.thickness
  thicknessSlider.x = (0.25F * cellSize).toInt
  thicknessSlider.y = 3 * cellSize
  thicknessSlider.width = (2.75F * cellSize).toInt
  thicknessSlider.height = 1 * cellSize
  thicknessSlider.actionOnChange = (cur) => Canvas.thickness = cur
  this += thicknessSlider

  val colorButton = new Button()
  colorButton.tooltip = "000000"
  colorButton.background = Canvas.color
  colorButton.x = (3.25F * cellSize).toInt
  colorButton.y = (3.25F * cellSize).toInt
  colorButton.width = (0.5F * cellSize).toInt
  colorButton.height = (0.5F * cellSize).toInt
  colorButton.actionOnClick = () => {
    val color = JColorChooser.showDialog(null, "Выберите цвет", Colors.toAWT(Canvas.color))
    if (color != null) {
      Canvas.color = Colors.toSlick(color)
      var zeros = ""
      val s = Integer.toHexString(color.getRGB & 0xffffff)
      for (i <- 0 until 6 - s.length) {
        zeros += "0"
      }
      colorButton.tooltip = zeros + s
      colorButton.background = Canvas.color
    }
  }
  this += colorButton

  // ---

  val textButton = new Button
  textButton.tooltip = "Текст"
  textButton.icon = ImageFactory.get("text.png")
  textButton.x = 0 * cellSize
  textButton.y = 4 * cellSize
  textButton.width = 1 * cellSize
  textButton.height = 1 * cellSize
  textButton.actionOnClick = () => {
    Canvas.tool = Tools.text
  }
  this += textButton

  val imageButton = new Button
  imageButton.tooltip = "Изображение"
  imageButton.icon = ImageFactory.get("image.png")
  imageButton.x = 1 * cellSize
  imageButton.y = 4 * cellSize
  imageButton.width = 1 * cellSize
  imageButton.height = 1 * cellSize
  imageButton.actionOnClick = () => Canvas.tool = Tools.image
  this += imageButton

  val shapeButton = new Button
  shapeButton.tooltip = "Прямоугольник"
  shapeButton.icon = ImageFactory.get("rectangle.png")
  shapeButton.x = 2 * cellSize
  shapeButton.y = 4 * cellSize
  shapeButton.width = 1 * cellSize
  shapeButton.height = 1 * cellSize
  shapeButton.actionOnClick = () => Canvas.tool = Tools.shape
  this += shapeButton

  val lineButton = new Button
  lineButton.tooltip = "Линия"
  lineButton.icon = ImageFactory.get("line.png")
  lineButton.x = 3 * cellSize
  lineButton.y = 4 * cellSize
  lineButton.width = 1 * cellSize
  lineButton.height = 1 * cellSize
  lineButton.actionOnClick = () => Canvas.tool = Tools.line
  this += lineButton

  // ---

  override def render(g: Graphics): Unit = {
    super.render(g)

    // lines
    g.setColor(Color.gray)
    for (i <- 1 to 5; if i != 3) {
      g.drawLine(realX + 0 * cellSize, i * cellSize, realX + 4 * cellSize, i * cellSize)
    }

    // page thumbnails
    if (Canvas.board != null) {
      val startY = (5.5F * cellSize).toInt
      val w = (1.5F * cellSize).toInt
      val h = cellSize
      val gap = cellSize / 4
      for (i <- 0 until Canvas.board.pages.length) {
        val x = realX + gap + (w + gap * 2) * (i % 2)
        val y = startY + (h + gap) * (i / 2)

        g.drawImage(
          Canvas.board.pages(i).img,
          x, y, x + w, y + h,
          0, 0, Canvas.board.pages(i).img.getWidth, Canvas.board.pages(i).img.getHeight
        )
        g.setColor(if (i == Canvas.board.curPageID) Colors.blue else Color.lightGray)
        g.drawRect(x, y, w, h)

        g.setColor(Color.black)
        val c = (i + 1).toString
        val cw = g.getFont.getWidth(c)
        val ch = g.getFont.getHeight(c)
        g.drawString(c, x + w / 2 - cw / 2, y + h / 2 - ch / 2)
      }
    }

    // debug info
    val startY = height - 140
    g.setFont(Renderer.font)
    g.setColor(Color.black)
    g.drawString("FPS: " + DebugInfo.fps, realX + 10, startY)
    g.drawString("Delta: " + DebugInfo.delta, realX + 10, startY + 20)
    g.drawString("    calc: " + DebugInfo.deltaCalc, realX + 10, startY + 40)
    g.drawString("    render: " + DebugInfo.deltaRender, realX + 10, startY + 60)
    g.drawString("    invalidate: " + DebugInfo.deltaInvalidate, realX + 10, startY + 80)
    val freeMem = Runtime.getRuntime.freeMemory / 1024 / 1024
    val totalMem = Runtime.getRuntime.totalMemory / 1024 / 1024
    val usedMem = totalMem - freeMem
    g.drawString("Mem: " + usedMem + "M/" + totalMem + "M", realX + 10, startY + 100)
  }

  override def update(): Unit = {
    super.update()

    x = Renderer.container.getWidth - width
    height = Renderer.container.getHeight

    if (SaveThread.saving) {
      saveButton.background = Color.yellow
    } else if (System.currentTimeMillis() - SaveThread.lastSave < 1000) {
      saveButton.background = Color.green
    } else {
      saveButton.background = Color.transparent
    }

    if (Canvas.editing) {
      clearButton.icon = ImageFactory.get("delete.png")
    } else {
      clearButton.icon = ImageFactory.get("cancel.png")
    }

    cursorButton.background = if (Canvas.tool == Tools.cursor) Colors.blue else Color.transparent
    penButton.background = if (Canvas.tool == Tools.pen) Colors.blue else Color.transparent
    highlighterButton.background = if (Canvas.tool == Tools.highlighter) Colors.blue else Color.transparent
    eraserButton.background = if (Canvas.tool == Tools.eraser) Colors.blue else Color.transparent
    textButton.background = if (Canvas.tool == Tools.text) Colors.blue else Color.transparent
    imageButton.background = if (Canvas.tool == Tools.image) Colors.blue else Color.transparent
    shapeButton.background = if (Canvas.tool == Tools.shape) Colors.blue else Color.transparent
    lineButton.background = if (Canvas.tool == Tools.line) Colors.blue else Color.transparent
  }
}
