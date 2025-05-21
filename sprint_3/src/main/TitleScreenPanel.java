package main;

import util.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleScreenPanel extends JPanel {

    Font pixelFont = new FontLoader().getPixelFont();

    public TitleScreenPanel(JFrame window, Runnable onStartGame) {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 42, 60));

        JLabel title = new JLabel("Santorini", SwingConstants.CENTER);
        title.setFont(pixelFont.deriveFont(48f));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(100, 0, 50, 0));
        add(title, BorderLayout.NORTH);

        try {
            int width = 190;
            int height = 60;

            ImageIcon defaultIcon = new ImageIcon(
                    new ImageIcon(getClass().getResource("/buttons/playbutton1.png"))
                            .getImage()
                            .getScaledInstance(width, height, Image.SCALE_SMOOTH)
            );

            ImageIcon pressedIcon = new ImageIcon(
                    new ImageIcon(getClass().getResource("/buttons/playbutton2.png"))
                            .getImage()
                            .getScaledInstance(width, height, Image.SCALE_SMOOTH)
            );

            JButton startButton = new JButton();
            startButton.setIcon(defaultIcon);
            startButton.setPressedIcon(pressedIcon);

            JButton exitButton = new JButton();
            exitButton.setIcon(defaultIcon);
            exitButton.setPressedIcon(pressedIcon);

            // Make it look clean
            startButton.setBorderPainted(false);
            startButton.setContentAreaFilled(false);
            startButton.setFocusPainted(false);
            startButton.setOpaque(false);

            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onStartGame.run(); // Run your start game logic
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(new Color(30, 30, 60));
            buttonPanel.add(startButton);
            add(buttonPanel, BorderLayout.CENTER);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to load button images.");
        }
    }
}
