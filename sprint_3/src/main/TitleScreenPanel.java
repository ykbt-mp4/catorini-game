package main;

import util.FontLoader;
import actors.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleScreenPanel extends JPanel {

    Font pixelFont = new FontLoader().getPixelFont();
    private final Image waterTile;

    public TitleScreenPanel(JFrame window, Runnable onStartGame) {
        setLayout(new BorderLayout());
        setBackground(new Color(156, 212, 200));
        waterTile = new ImageIcon(getClass().getResource("/tiles/water.png")).getImage();
        setOpaque(false);

        JLabel title = new JLabel("Cat-orini Game", SwingConstants.CENTER);
        title.setFont(pixelFont.deriveFont(48f));
        title.setBorder(BorderFactory.createEmptyBorder(100, 0, 50, 0));
        add(title, BorderLayout.NORTH);

        JLabel title2 = new JLabel("Cat Themed Santorini Game", SwingConstants.CENTER);
        title2.setFont(pixelFont.deriveFont(30f));
        title2.setBorder(BorderFactory.createEmptyBorder(50, 0, 100, 0));
        add(title2, BorderLayout.SOUTH);

        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                Image landImage = new ImageIcon(getClass().getResource("/uiextra/Hills.png")).getImage();
                Image textBoxImage = new ImageIcon(getClass().getResource("/uiextra/dialog_box.png")).getImage();

                Player player = new Player(1);
                int imgSize = 200; // Adjust as needed
                int imgX = (getWidth() - imgSize) / 4;
                int imgY = 3*(getHeight() - imgSize) / 4;
                g2.drawImage(landImage, imgX, imgY+20, imgSize, imgSize, this);
                player.draw(g2, imgX, imgY, imgSize, imgSize);

                // Draw dialog background
                int boxX = 3* (getWidth() - imgSize) / 4;
                int boxY = (getHeight() - imgSize) / 4;
                int boxWidth = 200;
                int boxHeight = 150;

                g2.drawImage(textBoxImage, boxX, boxY, boxWidth, boxHeight, this);

                g2.setFont(pixelFont.deriveFont(20f));
                g2.setColor(Color.BLACK);

                String message = "Meow!\nFIT3077 Sprint 3\nAssignment 2025";
                int lineHeight = g2.getFontMetrics().getHeight();
                int textX = 3* (getWidth() - imgSize) / 4 + 22;
                int textY = (getHeight() - imgSize) / 4 + 60;

                for (String line : message.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += lineHeight;
                }
            }
        };
        imagePanel.setPreferredSize(new Dimension(500, 0));
        imagePanel.setOpaque(false);
        add(imagePanel, BorderLayout.WEST);

        JPanel imagePanel2 = new JPanel();
        imagePanel2.setPreferredSize(new Dimension(500, 0));
        imagePanel2.setOpaque(false);
        add(imagePanel2, BorderLayout.EAST);

        try {
            int width = 190;
            int height = 60;

            ImageIcon defaultIcon = new ImageIcon(
                    new ImageIcon(getClass().getResource("/uiextra/button1.png"))
                            .getImage()
                            .getScaledInstance(width, height, Image.SCALE_SMOOTH)
            );

            ImageIcon pressedIcon = new ImageIcon(
                    new ImageIcon(getClass().getResource("/uiextra/button2.png"))
                            .getImage()
                            .getScaledInstance(width, height, Image.SCALE_SMOOTH)
            );

            JButton startButton = new JButton("Start New Game");
            startButton.setIcon(defaultIcon);
            startButton.setPressedIcon(pressedIcon);
            startButton.setHorizontalTextPosition(SwingConstants.CENTER);
            startButton.setVerticalTextPosition(SwingConstants.CENTER);
            startButton.setFont(pixelFont.deriveFont(20f));

            JButton loadButton = new JButton("Load Game");
            loadButton.setIcon(defaultIcon);
            loadButton.setPressedIcon(pressedIcon);
            loadButton.setHorizontalTextPosition(SwingConstants.CENTER);
            loadButton.setVerticalTextPosition(SwingConstants.CENTER);
            loadButton.setFont(pixelFont.deriveFont(20f));

            JButton exitButton = new JButton("Exit Game");
            exitButton.setIcon(defaultIcon);
            exitButton.setPressedIcon(pressedIcon);
            exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
            exitButton.setVerticalTextPosition(SwingConstants.CENTER);
            exitButton.setFont(pixelFont.deriveFont(20f));

            // Make it look clean
            startButton.setBorderPainted(false);
            startButton.setContentAreaFilled(false);
            startButton.setFocusPainted(false);
            startButton.setOpaque(false);

            loadButton.setBorderPainted(false);
            loadButton.setContentAreaFilled(false);
            loadButton.setFocusPainted(false);
            loadButton.setOpaque(false);

            exitButton.setBorderPainted(false);
            exitButton.setContentAreaFilled(false);
            exitButton.setFocusPainted(false);
            exitButton.setOpaque(false);

            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onStartGame.run(); // Run your start game logic
                }
            });

//            loadButton.addActionListener(e -> {
//                try {
//                    JFileChooser fileChooser = new JFileChooser();
//                    int result = fileChooser.showOpenDialog(window);
//                    if (result == JFileChooser.APPROVE_OPTION) {
//                        GamePanel loadedGame = GameSaveManager.loadGame(fileChooser.getSelectedFile().getAbsolutePath());
//                        GameStateManager.setState(GameState.PLAYER_TURN);
//                        window.setContentPane(loadedGame);
//                        window.revalidate();
//                        window.repaint();
//                    }
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                    JOptionPane.showMessageDialog(window, "Failed to load game.");
//                }
//            });

           exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setOpaque(false);

            int spacing = 20;
            buttonPanel.add(startButton);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, spacing)));
            buttonPanel.add(loadButton);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, spacing)));
            buttonPanel.add(exitButton);

            add(buttonPanel, BorderLayout.CENTER);


        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to load button images.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (waterTile != null) {
            g.drawImage(waterTile, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }
}
