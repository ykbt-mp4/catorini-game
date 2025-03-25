import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;

public class HelloWorld {
    public static void main(String[] args) {
        JFrame frame = new JFrame(); // Create a new JFrame
        frame.setSize(1920, 1080); // Set width and height
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        frame.setTitle("Empty Frame"); // Set title

        JLabel label = new JLabel("Hello World!", JLabel.CENTER); // Create label with text
        label.setFont(new Font("Serif", Font.BOLD, 50));
        frame.add(label); // Add label to the frame

        frame.setVisible(true); // Make frame visible
    }
}