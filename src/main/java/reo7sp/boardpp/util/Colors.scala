package reo7sp.boardpp.util

import org.newdawn.slick.{Color => SlickColor}
import java.awt.{Color => AWTColor}

/**
 * Created by reo7sp on 1/1/14 at 8:05 PM
 */
object Colors {
  val gray = new SlickColor(0xEEEEEE)
  val blue = new SlickColor(0x00C8FF)
  val orange = new SlickColor(0xFF8B40)
  val semiTransparentRed = new SlickColor(255, 0, 0, 127)

  def toStr(color: SlickColor) = Integer.toHexString(color.getAlpha) +
    Integer.toHexString(color.getRed) +
    Integer.toHexString(color.getGreen) +
    Integer.toHexString(color.getBlue)

  def toAWT(color: SlickColor) = new AWTColor(color.getRed, color.getGreen, color.getBlue, color.getAlpha)

  def toSlick(color: AWTColor) = new SlickColor(color.getRed, color.getGreen, color.getBlue, color.getAlpha)
}
