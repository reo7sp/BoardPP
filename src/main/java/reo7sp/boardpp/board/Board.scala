package reo7sp.boardpp.board

import java.io.{PrintWriter, File}
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import org.json.{JSONArray, JSONObject}
import reo7sp.boardpp.Core

/**
 * Created by reo7sp on 12/4/13 at 10:38 PM
 */
class Board private() {
  var name = "Безымянная доска"
  val pages = new ArrayBuffer[BoardPage]
  var curPageID = 0
  var file: File = null

  def save(): Unit = if (file != null) {
    val writer = new PrintWriter(file)
    val json = new JSONObject

    json.put("name", name)
    json.put("version", Core.version)
    json.put("encoding", System.getProperty("file.encoding"))

    val jsonPages = new JSONArray
    for (page <- pages) {
      val jsonPage = new JSONObject

      val jsonElems = new JSONArray
      for (elem <- page.elements) {
        val jsonElem = new JSONObject
        jsonElem.put("id", elem.id)
        jsonElem.put("toolID", elem.toolID)
        jsonElem.put("data", elem.toArray)

        jsonElems.put(jsonElem)
      }
      jsonPage.put("elements", jsonElems)

      val jsonRules = new JSONArray
      for (elem <- page.rules) {
        val jsonRule = new JSONObject
        jsonRule.put("id", elem.id)
        jsonRule.put("ruleID", elem.ruleID)
        jsonRule.put("data", elem.toArray)

        jsonRules.put(jsonRule)
      }
      jsonPage.put("rules", jsonRules)

      jsonPages.put(jsonPage)
    }
    json.put("pages", jsonPages)

    writer.println(json.toString(4))
    writer.close()
  }

  def curPage = pages(curPageID)

  def +=(page: BoardPage): Board = {
    pages += page
    this
  }

  def -=(page: BoardPage): Board = {
    pages -= page
    page.destroy()
    this
  }

  def destroy(): Unit = {
    for (page <- pages) {
      page.destroy()
    }
    pages.clear()
  }
}

object Board {
  def newBoard = new Board += new BoardPage

  def load(f: File): Board = load(f, System.getProperty("file.encoding"))

  def load(f: File, encoding: String): Board = {
    val board = new Board
    val source = Source.fromFile(f)
    val json = new JSONObject(source.mkString)
    if (json.optString("encoding", encoding) != encoding) {
      return load(f, json.optString("encoding", encoding))
    }

    board.file = f
    board.name = json.optString("name", "Безымянная доска")

    val jsonPages = json.optJSONArray("pages")
    if (jsonPages != null) for (i <- 0 until jsonPages.length()) {
      val jsonPage = jsonPages.optJSONObject(i)
      val page = new BoardPage

      val jsonElements = jsonPage.optJSONArray("elements")
      if (jsonElements != null) for (j <- 0 until jsonElements.length()) {
        val jsonElem = jsonElements.optJSONObject(j)
        val id = jsonElem.getInt("id")
        val toolID = jsonElem.getInt("toolID")
        val jsonElemData = jsonElem.getJSONArray("data")
        val data = new Array[String](jsonElemData.length())
        for (j <- 0 until jsonElemData.length()) {
          data(j) = jsonElemData.getString(j)
        }
        page += BoardObject.newInstance(id, toolID, data)
      }

      val jsonRules = jsonPage.optJSONArray("rules")
      if (jsonRules != null) for (j <- 0 until jsonRules.length()) {
        val jsonRule = jsonRules.optJSONObject(j)
        val id = jsonRule.getInt("id")
        val ruleID = jsonRule.getInt("ruleID")
        val jsonRuleData = jsonRule.getJSONArray("data")
        val data = new Array[String](jsonRuleData.length())
        for (j <- 0 until jsonRuleData.length()) {
          data(j) = jsonRuleData.getString(j)
        }
        page += BoardRule.newInstance(id, ruleID, data)
      }

      board += page
    }

    source.close()
    board
  }
}
