import main.GamePanel;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Santorini Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1200, 800);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Adding in the gameboard 
        GamePanel gamePanel = new GamePanel();

        // Add panels
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

