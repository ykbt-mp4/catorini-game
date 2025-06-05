package view;

import actors.Player;
import main.GamePanel;
import util.FontLoader;
import util.GodCardAssigner;
import util.LeaderBoard;

import javax.swing.*;
import java.awt.*;

/**
 * TitleScreenPanel represents the main menu UI for the game.
 * It features a title, background art, leaderboard, and buttons to start or exit the game.
 */
public class TitleScreenPanel extends JPanel {

    private final Image waterTile;
    Font pixelFont = new FontLoader().getPixelFont();
    LeaderBoard leaderBoard = new LeaderBoard(); // leaderboard instance to get player scores.

    /**
     * Constructs a new TitleScreenPanel with custom layout and UI elements.
     * @param window       the main application window (JFrame)
     * @param onStartGame  callback to trigger the game start
     */
    public TitleScreenPanel(JFrame window, Runnable onStartGame) {
        setLayout(new BorderLayout());
        setBackground(new Color(156, 212, 200));
        setOpaque(false);

        // used for a background image of the screen
        waterTile = new ImageIcon(getClass().getResource("/tiles/water.png")).getImage();

        // Title and subtitle
        JLabel title = new JLabel("Cat-orini Game !!", SwingConstants.CENTER);
        title.setFont(pixelFont.deriveFont(48f));
        title.setBorder(BorderFactory.createEmptyBorder(100, 0, 50, 0));
        add(title, BorderLayout.NORTH);

        JLabel subtitle = new JLabel("Cat Themed Santorini Game", SwingConstants.CENTER);
        subtitle.setFont(pixelFont.deriveFont(30f));
        subtitle.setBorder(BorderFactory.createEmptyBorder(50, 0, 100, 0));
        add(subtitle, BorderLayout.SOUTH);

        // Left image panel
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setFont(pixelFont.deriveFont(20f));

                // Loading UI images
                Image landImage = new ImageIcon(getClass().getResource("/uiextra/Hills.png")).getImage();
                Image charImage = new ImageIcon(getClass().getResource("/uiextra/charemoji.png")).getImage();
                Image textBoxImage = new ImageIcon(getClass().getResource("/uiextra/dialog_box.png")).getImage();

                int imgSize = 200;
                int charImgSize = 100;

                int landX = (getWidth() - imgSize) / 4;
                int landY = (3 * (getHeight() - imgSize) / 4) + 20;
                g2.drawImage(landImage, landX, landY, imgSize, imgSize, this);

                int charX = landX + (imgSize - charImgSize) / 2;
                int charY = (landY + (imgSize - charImgSize) / 2) - 20;
                g2.drawImage(charImage, charX, charY, charImgSize, charImgSize, this);

                int boxX = 3 * (getWidth() - imgSize) / 4;
                int boxY = (getHeight() - imgSize) / 4;
                g2.drawImage(textBoxImage, boxX, boxY, 200, 150, this);

                String message = "Meow!\nFIT3077 Sprint 3\nAssignment 2025";
                int lineHeight = g2.getFontMetrics().getHeight();
                int textX = boxX + 22;
                int textY = boxY + 60;
                for (String line : message.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += lineHeight;
                }
            }
        };
        imagePanel.setPreferredSize(new Dimension(500, 0));
        imagePanel.setOpaque(false);
        add(imagePanel, BorderLayout.WEST);

        // Right panel - leaderboard
        JPanel leaderPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g2) {
                super.paintComponent(g2);
                g2.setFont(pixelFont.deriveFont(30f));
                FontMetrics fm = g2.getFontMetrics();
                String title = "Leaderboard";
                int x = (getWidth() - fm.stringWidth(title)) / 2;
                g2.drawImage(new ImageIcon(getClass().getResource("/uiextra/leaderboard.png")).getImage(),
                        100, 0, getWidth() - 200, getHeight(), this);
                g2.drawString(title, x, 60);
            }
        };

        leaderPanel.setPreferredSize(new Dimension(500, 0));
        leaderPanel.setOpaque(false);

        // Scrollable leaderboard text area
        JTextArea textArea = new JTextArea(leaderBoard.readLeaderboardFile());
        textArea.setEditable(false);
        textArea.setFont(pixelFont.deriveFont(20f));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(125, 75, 250, 250);

        leaderPanel.add(scrollPane);
        add(leaderPanel, BorderLayout.EAST);

        // Center panel with buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        try {
            ImageIcon defaultIcon = loadScaledIcon("/uiextra/button1.png", 180, 60);
            ImageIcon pressedIcon = loadScaledIcon("/uiextra/button2.png", 180, 60);

            JButton startButton = createStyledButton("Start New Game", defaultIcon, pressedIcon);
            JButton exitButton = createStyledButton("Exit Game", defaultIcon, pressedIcon);

            startButton.addActionListener(e -> promptForNamesAndStartGame(window));
            exitButton.addActionListener(e -> System.exit(0));

            int spacing = 50;
            buttonPanel.add(Box.createRigidArea(new Dimension(0, spacing)));
            buttonPanel.add(startButton);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, spacing)));
            buttonPanel.add(exitButton);

            add(buttonPanel, BorderLayout.CENTER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Prompts the user to enter names for both players and starts the game.
     * @param window the main game window
     */
    private void promptForNamesAndStartGame(JFrame window) {
        JTextField player1Field = new JTextField(10);
        JTextField player2Field = new JTextField(10);

        ImageIcon promptIcon = loadScaledIcon("/uiextra/titleemoji.png", 100, 100);
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Player 1 Name:"));
        panel.add(player1Field);
        panel.add(new JLabel("Player 2 Name:"));
        panel.add(player2Field);

        int result = JOptionPane.showOptionDialog(this, panel, "Enter Player Names",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, promptIcon, null, null);

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
                JOptionPane.showMessageDialog(this, "Please enter names for both players.");
            }
        }
    }

    /**
     * Helper function to create a custom-styled button with images and pixel font.
     * @param text          button label
     * @param defaultIcon   default icon image
     * @param pressedIcon   icon when pressed
     * @return a styled {@code JButton}
     */
    private JButton createStyledButton(String text, ImageIcon defaultIcon, ImageIcon pressedIcon) {
        JButton button = new JButton(text);
        button.setIcon(defaultIcon);
        button.setPressedIcon(pressedIcon);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setFont(pixelFont.deriveFont(20f));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        return button;
    }

    /**
     * Loads an image icon and scales it to the specified size.
     * @param path   resource path
     * @param width  desired width
     * @param height desired height
     * @return scaled {@code ImageIcon}
     */
    private ImageIcon loadScaledIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    /**
     * Draws the water tile background.
     * @param g the graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (waterTile != null) {
            g.drawImage(waterTile, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }
}
