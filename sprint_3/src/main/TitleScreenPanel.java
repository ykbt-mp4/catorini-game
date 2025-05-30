package main;

import actors.Player;
import util.FontLoader;
import util.GodCardAssigner;

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

        JLabel title = new JLabel("Cat-orini Game !!", SwingConstants.CENTER);
        title.setFont(pixelFont.deriveFont(48f));
        title.setBorder(BorderFactory.createEmptyBorder(100, 0, 50, 0));
        add(title, BorderLayout.NORTH);

        JLabel title2 = new JLabel("Cat Themed Santorini Game", SwingConstants.CENTER);
        title2.setFont(pixelFont.deriveFont(30f));
        title2.setBorder(BorderFactory.createEmptyBorder(50, 0, 100, 0));
        add(title2, BorderLayout.SOUTH);

        // image only panel
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setFont(pixelFont.deriveFont(20f));

                Image landImage = new ImageIcon(getClass().getResource("/uiextra/Hills.png")).getImage();
                Image charImage = new ImageIcon(getClass().getResource("/uiextra/charemoji.png")).getImage();
                int imgSize = 200;
                int charImgSize = 100;

                int landX = (getWidth() - imgSize) / 4;
                int landY = (3 * (getHeight() - imgSize) / 4) + 20;
                g2.drawImage(landImage, landX, landY, imgSize, imgSize, this);

                int charX = landX + (imgSize - charImgSize) / 2;
                int charY = (landY + (imgSize - charImgSize) / 2) - 20;
                g2.drawImage(charImage, charX, charY, charImgSize, charImgSize, this);

                Image textBoxImage = new ImageIcon(getClass().getResource("/uiextra/dialog_box.png")).getImage();
                int boxX = 3 * (getWidth() - imgSize) / 4;
                int boxY = (getHeight() - imgSize) / 4;
                int boxWidth = 200;
                int boxHeight = 150;
                g2.drawImage(textBoxImage, boxX, boxY, boxWidth, boxHeight, this);

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

        // leader board
        imagePanel.setPreferredSize(new Dimension(500, 0));
        imagePanel.setOpaque(false);
        add(imagePanel, BorderLayout.WEST);

        JPanel imagePanel2 = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                Image boardImage = new ImageIcon(getClass().getResource("/uiextra/menupanel.png")).getImage();
                g2.drawImage(boardImage, 200/2, 0, getWidth() - 200, getHeight(), this);
            }
        };
        imagePanel2.setPreferredSize(new Dimension(500, 0));
        imagePanel2.setOpaque(false);
        add(imagePanel2, BorderLayout.EAST);

        try {
            int width = 180;
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

            JButton exitButton = new JButton("Exit Game");
            exitButton.setIcon(defaultIcon);
            exitButton.setPressedIcon(pressedIcon);
            exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
            exitButton.setVerticalTextPosition(SwingConstants.CENTER);
            exitButton.setFont(pixelFont.deriveFont(20f));

            startButton.setBorderPainted(false);
            startButton.setContentAreaFilled(false);
            startButton.setFocusPainted(false);
            startButton.setOpaque(false);

            exitButton.setBorderPainted(false);
            exitButton.setContentAreaFilled(false);
            exitButton.setFocusPainted(false);
            exitButton.setOpaque(false);

            startButton.addActionListener(e -> promptForNamesAndStartGame(window));

            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            // buttons (middle panel)
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setOpaque(false);

            int spacing = 50;
            buttonPanel.add(Box.createRigidArea(new Dimension(0, spacing)));
            buttonPanel.add(startButton);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, spacing)));
            buttonPanel.add(exitButton);

            add(buttonPanel, BorderLayout.CENTER);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to load button images.");
        }
    }

    public void promptForNamesAndStartGame(JFrame window) {
        JTextField player1Field = new JTextField(10);
        JTextField player2Field = new JTextField(10);

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/uiextra/titleemoji.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Player 1 Name:"));
        panel.add(player1Field);
        panel.add(new JLabel("Player 2 Name:"));
        panel.add(player2Field);

        int result = JOptionPane.showOptionDialog(this,
                panel,
                "Enter Player Names",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                scaledIcon,
                null,
                null);

        if (result == JOptionPane.OK_OPTION) {
            String name1 = player1Field.getText().trim();
            String name2 = player2Field.getText().trim();

            if (!name1.isEmpty() && !name2.isEmpty()) {
                Player player1 = new Player(1, name1);
                Player player2 = new Player(2, name2);
                GodCardAssigner.assignRandomGod(player1);
                GodCardAssigner.assignRandomGod(player2);

                GamePanel gamePanel = new GamePanel(player1, player2);
                SidePanel sidePanel = new SidePanel(player1, player2);
                gamePanel.gameStart();

                window.getContentPane().removeAll();
                window.add(gamePanel, BorderLayout.CENTER);
                window.add(sidePanel, BorderLayout.EAST);
                window.revalidate();
                window.repaint();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please enter names for both players.");
            }
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
