import actors.Player;
import main.GamePanel;
import main.TitleScreenPanel;
import util.GodCardAssigner;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("Santorini Game");
        window.setSize(1200, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());

        // Create title screen and set up button callback
        TitleScreenPanel titleScreen = new TitleScreenPanel(window, new Runnable() {
            @Override
            public void run() {
                Player player1 = new Player(1);
                Player player2 = new Player(2);
                GodCardAssigner.assignRandomGod(player1);
                GodCardAssigner.assignRandomGod(player2);
                GamePanel gamePanel = new GamePanel(player1, player2);
                gamePanel.gameStart();
                window.getContentPane().removeAll();
                window.add(gamePanel, BorderLayout.WEST);
                window.revalidate();
                window.repaint();
            }
        });


        window.add(titleScreen, BorderLayout.CENTER);
        window.setVisible(true);
    }
}
