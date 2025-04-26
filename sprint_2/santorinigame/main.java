package sprint_2.santorinigame;

import java.awt.*;
import java.awt.GridLayout;
import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        JFrame frame = new JFrame("Santorini Game"); // Create a new JFrame
        frame.setSize(1920, 1080); // Set width and height
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(5, 5)); // 5x5 grid with 10px gaps

        for (int i = 0; i < 25; i++) {
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(100, 100)); // Squares of 100px height and width
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            gridPanel.add(panel);
        }

        // Create a wrapper to centre the grid in the window
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.add(gridPanel); // Centre the grid panel inside the wrapper
       
        // Add the wrapper panel to the frame
        frame.add(wrapperPanel, BorderLayout.CENTER);
        
        frame.setVisible(true); // Make frame visible

    }
}

