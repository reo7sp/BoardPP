package reo7sp.boardpp.util

import javax.swing.filechooser.FileFilter
import java.io.File

/**
 * Created by reo7sp on 1/1/14 at 3:05 PM
 */
case class SimpleFileFilter(extension: String) extends FileFilter {
  def getDescription: String = extension.toUpperCase + " files (*." + extension + ")"

  def accept(f: File): Boolean = !f.isFile || f.getName.substring(f.getName.lastIndexOf(".") + 1) == extension
}
