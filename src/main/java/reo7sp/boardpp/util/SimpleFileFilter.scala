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

import javax.swing.filechooser.FileFilter
import java.io.File

/**
 * Created by reo7sp on 1/1/14 at 3:05 PM
 */
case class SimpleFileFilter(extension: String) extends FileFilter {
  def getDescription: String = extension.toUpperCase + " files (*." + extension + ")"

  def accept(f: File): Boolean = !f.isFile || f.getName.substring(f.getName.lastIndexOf(".") + 1) == extension
}
