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

/**
 * Created by reo7sp on 1/1/14 at 8:40 PM
 */
object CharMap {
  val default = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?"
  val cyrillic = "йцукенгшщзхъфывапролджэячсмитьбюёЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮЁ"

  val arrowsStr = "➟➡➢➣➤➥➦➧➨➚➘➙➛➜➝➞➸➲➳⏎➴➵➶➷➹➺➻➼➽←↑→↓↔↕↖↗↘↙↚↛↜↝↞↟↠↡↢↣↤↥↦↧↨➫➬➩➪➭➮➯➱↩↪↫↬↭↮↯↰↱↲↳↴↵↶↷↸↹↺↻↼↽↾↿⇀⇁⇂⇃⇄⇅⇆⇇⇈⇉⇊⇋⇌⇍⇎⇏⇐⇑⇒⇓⇔⇕⇖⇗⇘⇙⇚⇛⇜⇝⇞⇟⇠⇡⇢⇣⇤⇥⇦⇧⇨⇩⇪⌦⌧⌫⇫⇬⇭⇮⇯⇰⇱⇲⇳⇴⇵⇶⇷⇸⇹⇺"
  val trianglesStr = "▲▼◄►▶◀◣◢◥◤▸◂▴▾△▽▷◁⊿▻◅▵▿▹◃"
  val ratiosStr = "⅟½⅓⅕⅙⅛⅔⅖⅚⅜¾⅗⅝⅞⅘"
  val circlesStr = "➀➁➂➃➄➅➆➇➈➉➊➋➌➍➎➏➐➑➒➓ⒶⒷⒸⒹⒺⒻⒼⒽⒾⒿⓀⓁⓂⓃⓄⓅⓆⓇⓈⓉⓊⓋⓌⓍⓎⓏⓐⓑⓒⓓⓔⓕⓖⓗⓘⓙⓚⓛⓜⓝⓞⓟⓠⓡⓢⓣⓤⓥⓦⓧⓨⓩ◉○◌◍◎●◐◑◒◓◔◕◖◗❂☢⊗⊙◘◙"
  val mathStr = "℅‰‱≂≃≄≅≆≇≈≉≊≋≌≍≎≏≐≑≒≓≔≕≖≗≘≙≚≛≜≝≞≟≠≡≢≣≤≥≦≧≨≩⊰⊱⋛⋚"
  val moneyStr = "€£Ұ₴₰¢₤¥₳₲₪₵元₣₱฿¤₡₮₭₩ރ円₢₥₫₦ł﷼₠₧₯₨čर"
  val weatherStr = "☼☀☁☂☔☄☾☽❄☃☈☉℃℉°❅✺ϟ☇"
  val yesNoStr = "☑✓✔√☐☒✇✖✗✘✕☓"
  val iconsStr = "♔♕♖♗♘♙♚♛♜♝♞♟♤♧♡♢♠♣♥♦✽✾✿❁❃❋❀⚘☜☞☝☚☛☟✍✌✐✎✏✑✒✉⌨✆☎☏✁✂✃✄⚔"
  val lettersStr = "ℂℍℕℙℚℝℤℬℰℯℱℊℋℎℐℒℓℳℴ℘ℛℭ℮ℌℑℜℨ™℠℗©®"
  val starsStr = "⋆✢✣✤✥✦✧✩✰✪✫✬✭✮✯✡★✱✲✳✴✵✶✷✸✹✻✼❆❇❈❉❊"
  val musicStr = "♪♫♩♬♭♮♯ø"
  val zodiacStr = "♈♉♊♋♌♍♎♏♐♑♒♓"
  val smileyStr = "☹☺☻تヅツッシÜϡﭢ"

  val all = default + cyrillic + arrowsStr + trianglesStr + ratiosStr + circlesStr + mathStr +
    moneyStr + weatherStr + yesNoStr + iconsStr + lettersStr + starsStr + musicStr + zodiacStr + smileyStr

  val arrows = arrowsStr.split("|")
  val triangles = trianglesStr.split("|")
  val ratios = ratiosStr.split("|")
  val circles = circlesStr.split("|")
  val math = mathStr.split("|")
  val money = moneyStr.split("|")
  val weather = weatherStr.split("|")
  val yesNo = yesNoStr.split("|")
  val icons = iconsStr.split("|")
  val letters = lettersStr.split("|")
  val stars = starsStr.split("|")
  val music = musicStr.split("|")
  val zodiac = zodiacStr.split("|")
  val smiley = smileyStr.split("|")
}
