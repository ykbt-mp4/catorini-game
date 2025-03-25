import javax.swing.*;

public class Test {


    public static void main(String[] args) {
        JFrame frame = new JFrame(); // Create a new JFrame
        frame.setSize(1920, 1080); // Set width and height
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        frame.setTitle("Test Executable"); // Set title

        JLabel label = new JLabel("Hello World!", JLabel.CENTER); // Create label with text
        frame.add(label); // Add label to the frame

        frame.setVisible(true); // Make frame visible
    }
}

