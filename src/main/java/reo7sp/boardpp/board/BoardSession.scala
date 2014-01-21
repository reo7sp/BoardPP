package reo7sp.boardpp.board

import com.badlogic.gdx.graphics.Color
import reo7sp.boardpp.Tools

/**
 * Created by reo7sp on 12/1/13 at 8:57 PM
 */
object BoardSession {
  var board: Board = null
  var editing = false
  var color = Color.BLACK
  var radius = 2
  var tool = Tools.cursor
}
