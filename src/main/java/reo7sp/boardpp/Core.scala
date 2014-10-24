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

package reo7sp.boardpp

import reo7sp.boardpp.ui.{CrashFrame, Renderer}

/**
 * Created by reo7sp on 11/27/13 at 4:01 PM
 */
object Core extends App {
  val version = "0.1.1"
  val versionInt = 2
  val name = "Board++ " + version + " by Reo_SP"

  try {
    SaveThread.start()
    Renderer.init()
  } catch {
    case e: Throwable => CrashFrame.crash(e)
  }
}
