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

import reo7sp.boardpp._
import java.io.File
import javax.swing.filechooser.FileFilter
import reo7sp.boardpp.util.{Colors, SimpleFileFilter}
import reo7sp.boardpp.board.{BoardSession, Board, BoardPage}
import reo7sp.boardpp.ui.widgets.my.{Slider, Button, Container}
import javax.swing.{JColorChooser, JFileChooser, JOptionPane}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.Gdx

/**
 * Created by reo7sp on 11/30/13 at 10:24 PM
 */
object ToolPanel extends Container {
  val cellSize = 48

  background = Colors.gray
  width = 4 * cellSize
  height = 5 * cellSize
  x = Gdx.graphics.getWidth - width - cellSize
  y = cellSize

  // ---

  val createButton = new Button
  createButton.tooltip = "Создать"
  createButton.icon = ImageFactory.get("create.png")
  createButton.x = 0 * cellSize
  createButton.y = 4 * cellSize
  createButton.width = 1 * cellSize
  createButton.height = 1 * cellSize
  createButton.actionOnClick = () => {
    if (JOptionPane.showConfirmDialog(null, "Создать новую доску?", null, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
      BoardSession.board = Board.newBoard
    }
  }
  this += createButton

  val openButton = new Button
  openButton.tooltip = "Открыть"
  openButton.icon = ImageFactory.get("open.png")
  openButton.x = 1 * cellSize
  openButton.y = 4 * cellSize
  openButton.width = 1 * cellSize
  openButton.height = 1 * cellSize
  openButton.actionOnClick = () => {
    if (JOptionPane.showConfirmDialog(null, "Открыть другую доску?", null, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
      val chooser = new JFileChooser
      chooser.setFileFilter(SimpleFileFilter("json"))
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        BoardSession.board = Board.load(chooser.getSelectedFile)
      }
    }
  }
  this += openButton

  val saveButton = new Button
  saveButton.tooltip = "Сохранить"
  saveButton.icon = ImageFactory.get("save.png")
  saveButton.x = 2 * cellSize
  saveButton.y = 4 * cellSize
  saveButton.width = 1 * cellSize
  saveButton.height = 1 * cellSize
  saveButton.actionOnClick = () => {
    if (BoardSession.board.file == null) {
      val chooser = new JFileChooser
      chooser.setFileFilter(new FileFilter {
        def getDescription: String = "JSON files (*.json)"

        def accept(f: File): Boolean = !f.isFile || f.getName.substring(f.getName.lastIndexOf(".") + 1) == "json"
      })
      if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        var f = chooser.getSelectedFile
        if (!f.getName.endsWith(".json")) f = new File(f.getAbsolutePath + ".json")
        BoardSession.board.file = f
      }
    }
    BoardSession.board.save()
  }
  this += saveButton

  val editElementsButton = new Button
  editElementsButton.tooltip = "Редактировать"
  editElementsButton.icon = ImageFactory.get("edit.png")
  editElementsButton.x = 3 * cellSize
  editElementsButton.y = 4 * cellSize
  editElementsButton.width = 1 * cellSize
  editElementsButton.height = 1 * cellSize
  editElementsButton.actionOnClick = () => {
    BoardSession.editing = !BoardSession.editing
    editElementsButton.background = if (BoardSession.editing) Colors.orange else Colors.gray
  }
  this += editElementsButton

  // ---

  val backButton = new Button
  backButton.tooltip = "На страницу назад"
  backButton.icon = ImageFactory.get("left.png")
  backButton.x = 0 * cellSize
  backButton.y = 3 * cellSize
  backButton.width = 1 * cellSize
  backButton.height = 1 * cellSize
  backButton.actionOnClick = () => if (BoardSession.board.curPageID > 0) {
    BoardSession.board.curPageID -= 1
  }
  this += backButton

  val nextButton = new Button
  nextButton.tooltip = "На страницу вперёд"
  nextButton.icon = ImageFactory.get("right.png")
  nextButton.x = 1 * cellSize
  nextButton.y = 3 * cellSize
  nextButton.width = 1 * cellSize
  nextButton.height = 1 * cellSize
  nextButton.actionOnClick = () => {
    BoardSession.board.curPageID += 1
    if (BoardSession.board.curPageID == BoardSession.board.pages.size) {
      BoardSession.board += new BoardPage
    }
  }
  this += nextButton

  val deleteButton = new Button
  deleteButton.tooltip = "Удалить"
  deleteButton.icon = ImageFactory.get("delete.png")
  deleteButton.x = 2 * cellSize
  deleteButton.y = 3 * cellSize
  deleteButton.width = 1 * cellSize
  deleteButton.height = 1 * cellSize
  deleteButton.actionOnClick = () => {
    if (BoardSession.editing && BoardSession.board.pages.length > 1) {
      if (BoardSession.board.curPageID != 0 && BoardSession.board.curPageID == BoardSession.board.pages.length - 1) {
        BoardSession.board.curPageID -= 1
      }
      BoardSession.board -= BoardSession.board.curPage
    }
  }
  this += deleteButton

  val editRulesButton = new Button
  editRulesButton.tooltip = "Правила"
  editRulesButton.icon = ImageFactory.get("events.png")
  editRulesButton.x = 3 * cellSize
  editRulesButton.y = 3 * cellSize
  editRulesButton.width = 1 * cellSize
  editRulesButton.height = 1 * cellSize
  editRulesButton.actionOnClick = () => if (BoardSession.editing) BoardRulesEditor(BoardSession.board.curPage)
  this += editRulesButton

  // ---

  val cursorButton = new Button
  cursorButton.tooltip = "Курсор"
  cursorButton.icon = ImageFactory.get("cursor.png")
  cursorButton.x = 0 * cellSize
  cursorButton.y = 2 * cellSize
  cursorButton.width = 1 * cellSize
  cursorButton.height = 1 * cellSize
  cursorButton.actionOnClick = () => BoardSession.tool = Tools.cursor
  this += cursorButton

  val penButton = new Button
  penButton.tooltip = "Ручка"
  penButton.icon = ImageFactory.get("pen.png")
  penButton.x = 1 * cellSize
  penButton.y = 2 * cellSize
  penButton.width = 1 * cellSize
  penButton.height = 1 * cellSize
  penButton.actionOnClick = () => BoardSession.tool = Tools.pen
  this += penButton

  val clearButton = new Button
  clearButton.tooltip = "Очистить"
  clearButton.icon = ImageFactory.get("cancel.png")
  clearButton.x = 2 * cellSize
  clearButton.y = 2 * cellSize
  clearButton.width = 1 * cellSize
  clearButton.height = 1 * cellSize
  clearButton.actionOnClick = () => BoardSession.board.curPage.clear()
  this += clearButton

  val eraserButton = new Button
  eraserButton.tooltip = "Ластик"
  eraserButton.icon = ImageFactory.get("eraser.png")
  eraserButton.x = 3 * cellSize
  eraserButton.y = 2 * cellSize
  eraserButton.width = 1 * cellSize
  eraserButton.height = 1 * cellSize
  eraserButton.actionOnClick = () => BoardSession.tool = Tools.eraser
  this += eraserButton

  val thicknessSlider = new Slider
  thicknessSlider.tooltip = "Толщина ручки"
  thicknessSlider.min = 2
  thicknessSlider.max = 20
  thicknessSlider.cur = BoardSession.radius
  thicknessSlider.x = (0.25F * cellSize).toInt
  thicknessSlider.y = 1 * cellSize
  thicknessSlider.width = (2.75F * cellSize).toInt
  thicknessSlider.height = 1 * cellSize
  thicknessSlider.actionOnChange = (cur) => BoardSession.radius = cur
  this += thicknessSlider

  val colorButton = new Button()
  colorButton.tooltip = "000000"
  colorButton.background = BoardSession.color
  colorButton.x = (3.25F * cellSize).toInt
  colorButton.y = (1.25F * cellSize).toInt
  colorButton.width = (0.5F * cellSize).toInt
  colorButton.height = (0.5F * cellSize).toInt
  colorButton.actionOnClick = () => {
    val color = JColorChooser.showDialog(null, "Выберите цвет", Colors.toAwt(BoardSession.color))
    if (color != null) {
      BoardSession.color = Colors.toGdx(color)
      var zeros = ""
      val s = Integer.toHexString(color.getRGB & 0xffffff)
      for (i <- 0 until 6 - s.length) {
        zeros += "0"
      }
      colorButton.tooltip = zeros + s
      colorButton.background = BoardSession.color
    }
  }
  this += colorButton

  // ---

  val textButton = new Button
  textButton.tooltip = "Текст"
  textButton.icon = ImageFactory.get("text.png")
  textButton.x = 0 * cellSize
  textButton.y = 0 * cellSize
  textButton.width = 1 * cellSize
  textButton.height = 1 * cellSize
  textButton.actionOnClick = () => if (BoardSession.editing) BoardSession.tool = Tools.text
  this += textButton

  val imageButton = new Button
  imageButton.tooltip = "Изображение"
  imageButton.icon = ImageFactory.get("image.png")
  imageButton.x = 1 * cellSize
  imageButton.y = 0 * cellSize
  imageButton.width = 1 * cellSize
  imageButton.height = 1 * cellSize
  imageButton.actionOnClick = () => if (BoardSession.editing) BoardSession.tool = Tools.image
  this += imageButton

  val shapeButton = new Button
  shapeButton.tooltip = "Прямоугольник"
  shapeButton.icon = ImageFactory.get("rectangle.png")
  shapeButton.x = 2 * cellSize
  shapeButton.y = 0 * cellSize
  shapeButton.width = 1 * cellSize
  shapeButton.height = 1 * cellSize
  shapeButton.actionOnClick = () => if (BoardSession.editing) BoardSession.tool = Tools.rect
  this += shapeButton

  val lineButton = new Button
  lineButton.tooltip = "Линия"
  lineButton.icon = ImageFactory.get("line.png")
  lineButton.x = 3 * cellSize
  lineButton.y = 0 * cellSize
  lineButton.width = 1 * cellSize
  lineButton.height = 1 * cellSize
  lineButton.actionOnClick = () => BoardSession.tool = Tools.line
  this += lineButton

  // ---

  override def render(): Unit = if (ShapeGetter.free) {
    super.render()

    // lines
    Renderer.batch.end()
    Renderer.shape.begin(ShapeType.Line)

    Renderer.shape.setColor(Color.GRAY)
    for (i <- 1 to 4) {
      Renderer.shape.line(realX, realY + i * cellSize, realX + 4 * cellSize, realY + i * cellSize)
    }
    Renderer.shape.rect(realX, realY, width, height)

    Renderer.shape.end()
    Renderer.batch.begin()
  }

  override def update(): Unit = if (ShapeGetter.free) {
    super.update()

    if (SaveThread.saving) {
      saveButton.background = Color.YELLOW
    } else if (System.currentTimeMillis() - SaveThread.lastSave < 1000) {
      saveButton.background = Color.GREEN
    } else {
      saveButton.background = null
    }

    cursorButton.background = if (BoardSession.tool == Tools.cursor) Colors.blue else null
    penButton.background = if (BoardSession.tool == Tools.pen) Colors.blue else null
    eraserButton.background = if (BoardSession.tool == Tools.eraser) Colors.blue else null
    textButton.background = if (BoardSession.tool == Tools.text) Colors.blue else null
    imageButton.background = if (BoardSession.tool == Tools.image) Colors.blue else null
    shapeButton.background = if (BoardSession.tool == Tools.rect) Colors.blue else null
    lineButton.background = if (BoardSession.tool == Tools.line) Colors.blue else null

    if (!BoardSession.editing) {
      deleteButton.background = Color.LIGHT_GRAY
      editRulesButton.background = Color.LIGHT_GRAY
      textButton.background = Color.LIGHT_GRAY
      imageButton.background = Color.LIGHT_GRAY
      shapeButton.background = Color.LIGHT_GRAY
    } else {
      deleteButton.background = null
      editRulesButton.background = null
    }
  }
}
