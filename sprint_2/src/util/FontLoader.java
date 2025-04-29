package util;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {

    private Font pixelFont;

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

    public Font getPixelFont() {
        return pixelFont;
    }
}

