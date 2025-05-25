import actors.Player;
import main.GamePanel;
import main.TitleScreenPanel;
import util.GodCardAssigner;
import main.SidePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("Santorini Game");
        window.setSize(1200, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());

        setCustomCursor(window);

        TitleScreenPanel titleScreen = new TitleScreenPanel(window, new Runnable() {
            @Override
            public void run() {

                Player player1 = new Player(1);
                Player player2 = new Player(2);
                GodCardAssigner.assignRandomGod(player1);
                GodCardAssigner.assignRandomGod(player2);
                System.out.println("Player 1 God Card: " + player1.getGodCard().getName());
                System.out.println("Player 2 God Card: " + player2.getGodCard().getName());

                GamePanel gamePanel = new GamePanel(player1, player2);
                SidePanel sidePanel = new SidePanel(player1, player2);
                gamePanel.gameStart();

                window.getContentPane().removeAll();
                window.add(gamePanel, BorderLayout.CENTER);
                window.add(sidePanel, BorderLayout.EAST);
                window.revalidate();
                window.repaint();

            }
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
