package reo7sp.boardpp.util;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by reo7sp on 1/7/14 at 4:39 PM
 */
public class FontFactory {
	private static final Map<FontSpecs, UnicodeFont> cache = new HashMap<FontSpecs, UnicodeFont>();
	private static final Font font = new Font(null, Font.PLAIN, 14);

	public static UnicodeFont newFont(int size, boolean bold, boolean italic) throws SlickException {
		final FontSpecs fontSpecs = new FontSpecs(size, bold, italic);
		UnicodeFont result = cache.get(fontSpecs);
		if (result == null) {
			result = new UnicodeFont(font, size, bold, italic);
			initFont(result);
			if (cache.size() > 4) {
				cache.clear();
			}
			cache.put(fontSpecs, result);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private static void initFont(UnicodeFont font) throws SlickException {
		font.addAsciiGlyphs();
		font.addGlyphs(0x0400, 0x04FF);
		font.addGlyphs(0x1DC0, 0x2BFF);
		font.getEffects().add(new ColorEffect());
		font.loadGlyphs();
	}

	private static class FontSpecs {
		public final int size;
		public final boolean bold, italic;

		private FontSpecs(int size, boolean bold, boolean italic) {
			this.size = size;
			this.bold = bold;
			this.italic = italic;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (!(o instanceof FontSpecs)) {
				return false;
			}
			FontSpecs fontSpecs = (FontSpecs) o;
			return bold == fontSpecs.bold && italic == fontSpecs.italic && size == fontSpecs.size;

		}

		@Override
		public int hashCode() {
			int result = size;
			result = 31 * result + (bold ? 1 : 0);
			result = 31 * result + (italic ? 1 : 0);
			return result;
		}
	}
}
