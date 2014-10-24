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

import com.badlogic.gdx.graphics.{Color => GdxColor}
import java.awt.{Color => AwtColor}

/**
 * Created by reo7sp on 1/1/14 at 8:05 PM
 */
object Colors {
  val gray = new GdxColor(0xeeeeeeff)
  val blue = new GdxColor(0x00c8ffff)
  val orange = new GdxColor(0xff8b40ff)
  val semiTransparentRed = new GdxColor(255, 0, 0, 127)

  def toGdx(color: AwtColor) = new GdxColor(color.getRed / 255F, color.getGreen / 255F, color.getBlue / 255F, color.getAlpha / 255F)

  def toAwt(color: GdxColor) = new AwtColor(color.r, color.g, color.b, color.a)

  def toGdx(str: String) = new GdxColor(java.lang.Long.parseLong(str, 16).toInt)

  def toAwt(str: String) = new AwtColor(java.lang.Long.parseLong(str, 16).toInt)

  def toRGBA(r: Float, g: Float, b: Float, a: Float): Int = toRGBA((255 * r).toInt, (255 * g).toInt, (255 * b).toInt, (255 * a).toInt)

  def toRGBA(r: Int, g: Int, b: Int, a: Int): Int = (r << 24) | (g << 16) | (b << 8) | a

  def toString(color: GdxColor): String = toString(color.r, color.g, color.b, color.a)

  def toString(color: AwtColor): String = toString(color.getRed, color.getBlue, color.getGreen, color.getAlpha)

  def toString(r: Float, g: Float, b: Float, a: Float): String = toString((255 * r).toInt, (255 * g).toInt, (255 * b).toInt, (255 * a).toInt)

  def toString(r: Int, g: Int, b: Int, a: Int): String = {
    var value = Integer.toHexString(toRGBA(r, g, b, a))
    while (value.length < 8) {
      value = "0" + value
    }
    value
  }
}
