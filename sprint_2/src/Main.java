import actors.Player;
import main.GamePanel;
import util.GodCardAssigner;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Santorini Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1300, 800);

        Player player1 = new Player();
        Player player2 = new Player();

        GodCardAssigner.assignRandomGod(player1);
        GodCardAssigner.assignRandomGod(player2);

        // debugging purposes
        System.out.println("Player 1 was assigned the god: " + player1.getGodCard().getName());
        System.out.println("Player 2 was assigned the god: " + player2.getGodCard().getName());

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Adding in the gameboard 
        GamePanel gamePanel = new GamePanel(player1, player2);

        // Add panels
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

