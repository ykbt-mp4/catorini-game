import view.TitleScreenPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Entry point for the Santorini Game application.
 * Initializes the main window, sets a custom cursor, and loads the title screen.
 */

public class Main {
    /**
     * Main method. Creates the application window and displays the title screen.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // Create the main application window
        JFrame window = new JFrame("Santorini Game");
        window.setSize(1200, 750); // Set fixed window size
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false); // Prevent resizing
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());

        // Set a custom cursor from an image file
        setCustomCursor(window);

        // Create and add the title screen panel to the center of the window
        TitleScreenPanel titleScreen = new TitleScreenPanel(window, () -> {
        });
        window.add(titleScreen, BorderLayout.CENTER);
        window.setVisible(true);
    }

    /**
     * Sets a custom cursor image for the application window.
     * The image must be located at /uiextra/cursor.png in the resource directory.
     * @param window The main application window to set the cursor on.
     */
    private static void setCustomCursor(JFrame window) {
        try {
            // Load the custom cursor image from resources
            BufferedImage cursorImage = ImageIO.read(
                    Objects.requireNonNull(Main.class.getResource("/uiextra/cursor.png"))
            );

            // Create a custom cursor with the image
            Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    cursorImage, new Point(0, 0), "Custom Cursor"
            );

            // Apply the custom cursor to the window
            window.setCursor(customCursor);

        } catch (IOException | NullPointerException e) {
            System.err.println("Failed to load custom cursor.");
            e.printStackTrace();
        }
    }
}
