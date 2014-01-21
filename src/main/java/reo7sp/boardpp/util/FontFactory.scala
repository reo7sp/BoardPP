package reo7sp.boardpp.util

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.Gdx
import scala.collection.mutable
import com.badlogic.gdx.graphics.g2d.BitmapFont

/**
 * Created by reo7sp on 1/14/14 at 8:23 PM
 */
object FontFactory {
  val cache = new mutable.HashMap[(Int, Boolean, Boolean), BitmapFont]

  val regularFont = new FreeTypeFontGenerator(Gdx.files.classpath("font/DejaVuSans.ttf"))
  val boldFont = new FreeTypeFontGenerator(Gdx.files.classpath("font/DejaVuSans-Bold.ttf"))
  val boldObliqueFont = new FreeTypeFontGenerator(Gdx.files.classpath("font/DejaVuSans-BoldOblique.ttf"))
  val obliqueFont = new FreeTypeFontGenerator(Gdx.files.classpath("font/DejaVuSans-Oblique.ttf"))

  def newFont(size: Int, bold: Boolean, italic: Boolean): BitmapFont = {
    val fontOption = cache.get((size, bold, italic))
    if (fontOption == None) {
      val font = if (bold) {
        if (italic) {
          boldObliqueFont.generateFont(size, CharMap.all, false)
        } else {
          boldFont.generateFont(size, CharMap.all, false)
        }
      } else if (italic) {
        obliqueFont.generateFont(size, CharMap.all, false)
      } else {
        regularFont.generateFont(size, CharMap.all, false)
      }
      cache += (size, bold, italic) -> font
      font
    } else {
      fontOption.get
    }
  }

  def dispose(): Unit = {
    regularFont.dispose()
    boldFont.dispose()
    boldObliqueFont.dispose()
    obliqueFont.dispose()

    for ((specs, font) <- cache) {
      font.dispose()
    }
    cache.clear()
  }
}
