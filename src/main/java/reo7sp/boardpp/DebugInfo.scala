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

import com.badlogic.gdx.Gdx

/**
 * Created by reo7sp on 11/29/13 at 3:42 PM
 */
object DebugInfo {
  var deltaCalc = 0
  var calcStartTime = 0L
  var deltaRender = 0
  var renderStartTime = 0L

  def fps = Gdx.graphics.getFramesPerSecond

  def delta = (Gdx.graphics.getDeltaTime * 1000).toInt

  def calcStart(): Unit = calcStartTime = System.nanoTime

  def calcEnd(): Unit = {
    val now = System.nanoTime
    deltaCalc = ((now - calcStartTime) / 1000000).toInt
  }

  def renderStart(): Unit = renderStartTime = System.nanoTime

  def renderEnd(): Unit = {
    val now = System.nanoTime
    deltaRender = ((now - renderStartTime) / 1000000).toInt
  }
}
