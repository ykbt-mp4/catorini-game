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

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.setFocusPainted(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onStartGame.run(); // Calls the passed game start logic
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 30, 60));
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.CENTER);
    }
}
