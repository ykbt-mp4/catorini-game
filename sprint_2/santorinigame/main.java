package sprint_2.santorinigame;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        JFrame frame = new JFrame("Santorini Game"); // Create a new JFrame

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        frame.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack();

        frame.setLocationRelativeTo(null); // opens game in center of screen
        frame.setVisible(true); // Make frame visible

    }
}

