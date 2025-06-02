package util;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Manages all font and text-related operations in the game.
 */
public class FontLoader {

    private Font pixelFont;

    /**
     * Constructs a FontLoader and attempts to load the font from the specified path.
     * If unsuccessful, throws a RuntimeException.
     * @throws RuntimeException if any font fails to load
     */
    public FontLoader() {
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/pixelFont-7-8x14-sproutLands.ttf");
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(25f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pixelFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the font to use in text
     * @return The pixel font used in the game
     */
    public Font getPixelFont() {
        return pixelFont;
    }
}

