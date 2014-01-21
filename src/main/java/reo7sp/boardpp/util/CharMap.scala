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
