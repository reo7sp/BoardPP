package reo7sp.boardpp.ui

import scala.collection.mutable
import javax.swing.ImageIcon
import org.newdawn.slick.Image
import org.newdawn.slick.util.BufferedImageUtil
import javax.imageio.ImageIO

/**
 * Created by reo7sp on 11/30/13 at 11:14 PM
 */
object ImageFactory {
  private[this] val cache = new mutable.HashMap[String, Image]()

  def get(name: String): Image = {
    val imageOption = cache.get(name)
    var image: Image = null

    if (imageOption == None) {
      val s = "/img/" + name
      image = new Image(BufferedImageUtil.getTexture(s, ImageIO.read(getClass.getResource(s))))
      cache += name -> image
    } else {
      image = imageOption.get
    }

    image
  }

  def getAwtIcon(name: String) = new ImageIcon(getClass.getResource("/img/" + name))
}
