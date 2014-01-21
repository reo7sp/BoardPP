package reo7sp.boardpp.ui

import scala.collection.mutable
import javax.swing.ImageIcon
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

/**
 * Created by reo7sp on 11/30/13 at 11:14 PM
 */
object ImageFactory {
  private[this] val cache = new mutable.HashMap[String, Texture]()

  def get(name: String): Texture = {
    val imageOption = cache.get(name)
    var texture: Texture = null

    if (imageOption == None) {
      texture = new Texture(Gdx.files.classpath("img/" + name))
      cache += name -> texture
    } else {
      texture = imageOption.get
    }

    texture
  }

  def getAwtIcon(name: String) = new ImageIcon(getClass.getResource("/img/" + name))
}
