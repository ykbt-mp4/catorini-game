package sprint_2.santorinigame;

import java.awt.GridLayout;
import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        JFrame frame = new JFrame(); // Create a new JFrame
        frame.setSize(1920, 1080); // Set width and height
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        frame.setTitle("Santorini Game"); // Set title
        frame.setLayout(new GridLayout(5, 5));
        frame.setVisible(true); // Make frame visible
    }
}

