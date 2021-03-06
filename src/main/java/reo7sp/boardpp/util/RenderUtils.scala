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

package reo7sp.boardpp.util

import com.badlogic.gdx.Gdx
import javax.imageio.ImageIO
import com.badlogic.gdx.graphics.Pixmap
import java.net.URL

/**
 * Created by reo7sp on 12/28/13 at 1:07 PM
 */
object RenderUtils {
  def makeDiagonalLine(x1: Int, y1: Int, x2: Int, y2: Int, func: (Int, Int) => Any): Unit = {
    var dx = x2 - x1
    if (dx == 0) dx = 1
    val dy = y2 - y1
    var a = 50F - math.abs(x1 - x2)
    if (a < 1) a = 1
    for (j <- 0 until a.toInt) {
      for (i <- math.min(x1, x2) to math.max(x1, x2)) {
        val x = i + j / a
        val y = y1 + dy * (x - x1) / dx
        func(x.toInt, y.toInt)
      }
    }
  }

  def flipY(y: Int) = Gdx.graphics.getHeight - y

  def readImg(url: URL) = {
    val img = ImageIO.read(url)
    val pixmap = new Pixmap(img.getWidth, img.getHeight, Pixmap.Format.RGBA8888)
    for (x <- 0 until img.getWidth; y <- 0 until img.getHeight) {
      val color = img.getRGB(x, y)
      pixmap.drawPixel(x, y, (color << 8) | (color >>> 24))
    }
    pixmap
  }
}
