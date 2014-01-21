package reo7sp.boardpp.ui.widgets.awt

import javax.swing.JButton
import reo7sp.boardpp.util.Colors
import javax.swing.border.EmptyBorder

/**
 * Created by reo7sp on 11/30/13 at 10:49 PM
 */
class FancyButton extends JButton {
  setBackground(Colors.toAwt(Colors.gray))
  setBorder(new EmptyBorder(8, 8, 8, 8))
  setFocusable(false)
}
