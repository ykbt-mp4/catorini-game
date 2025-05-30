import main.TitleScreenPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("Cat-orini Game");
        window.setSize(1200, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());

        setCustomCursor(window);

        TitleScreenPanel titleScreen = new TitleScreenPanel(window, () -> {
        });

        window.add(titleScreen, BorderLayout.CENTER);
        window.setVisible(true);
    }

    private static void setCustomCursor(JFrame window) {
        try {
            BufferedImage cursorImage = ImageIO.read(
                    Objects.requireNonNull(Main.class.getResource("/uiextra/cursor.png"))
            );
            Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    cursorImage, new Point(0, 0), "Custom Cursor"
            );
            window.setCursor(customCursor);
        } catch (IOException | NullPointerException e) {
            System.err.println("Failed to load custom cursor.");
            e.printStackTrace();
        }
    }
}
