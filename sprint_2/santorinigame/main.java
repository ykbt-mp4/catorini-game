package sprint_2.santorinigame;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        JFrame frame = new JFrame("Santorini Game"); // Create a new JFrame
        frame.setSize(1920, 1080); // Set width and height
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        frame.setLayout(new GridLayout(5, 5));

        for (int i = 0; i < 25; i++) {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            frame.add(panel);
        }
        frame.setVisible(true); // Make frame visible
    }
}

