package view;

import actors.Player;
import main.GamePanel;
import util.FontLoader;
import util.GodCardAssigner;
import util.LeaderBoard;

import javax.swing.*;
import java.awt.*;


public class TitleScreenPanel extends JPanel {

    private final Image waterTile;
    Font pixelFont = new FontLoader().getPixelFont();
    LeaderBoard leaderBoard = new LeaderBoard();

    public TitleScreenPanel(JFrame window, Runnable onStartGame) {
        setLayout(new BorderLayout());
        setBackground(new Color(156, 212, 200));

        waterTile = new ImageIcon(getClass().getResource("/tiles/water.png")).getImage();
        setOpaque(false);

        JLabel title = new JLabel("Cat-orini Game !!", SwingConstants.CENTER);
        title.setFont(pixelFont.deriveFont(48f));
        title.setBorder(BorderFactory.createEmptyBorder(100, 0, 50, 0));
        add(title, BorderLayout.NORTH);

        JLabel subtitle = new JLabel("Cat Themed Santorini Game", SwingConstants.CENTER);
        subtitle.setFont(pixelFont.deriveFont(30f));
        subtitle.setBorder(BorderFactory.createEmptyBorder(50, 0, 100, 0));
        add(subtitle, BorderLayout.SOUTH);

        // image only panel - right panel
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
        imagePanel.setPreferredSize(new Dimension(500, 0));
        imagePanel.setOpaque(false);
        add(imagePanel, BorderLayout.WEST);


        // leader board panel
        JPanel leaderPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g2) {
                super.paintComponent(g2);

                g2.setFont(pixelFont.deriveFont(30f));
                FontMetrics fm = g2.getFontMetrics();
                String title = "Leaderboard";
                int stringWidth = fm.stringWidth(title);
                int x = (getWidth() - stringWidth) / 2;
                int y = 60;

                Image boardImage = new ImageIcon(getClass().getResource("/uiextra/leaderboard.png")).getImage();
                g2.drawImage(boardImage, 100, 0, getWidth() - 200, getHeight(), this);

                g2.drawString(title, x, y);
            }
        };

        leaderPanel.setPreferredSize(new Dimension(500, 0));
        leaderPanel.setOpaque(false);

        JTextArea textArea = new JTextArea(leaderBoard.readLeaderboardFile());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(pixelFont.deriveFont(20f));
        textArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); // Inner padding

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(125, 75, 250, 250);

        leaderPanel.add(scrollPane);
        add(leaderPanel, BorderLayout.EAST);

        // buttons panel - middle panel
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
            System.out.println("Failed to load button images.");
        }
    }

    public void promptForNamesAndStartGame(JFrame window) {
        JTextField player1Field = new JTextField(10);
        JTextField player2Field = new JTextField(10);

        ImageIcon promptIcon = loadScaledIcon("/uiextra/titleemoji.png", 100, 100);

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
                promptIcon,
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

    private JButton createStyledButton(String text, ImageIcon defaultIcon, ImageIcon pressedIcon) {
        JButton button = new JButton(text);
        button.setIcon(defaultIcon);
        button.setPressedIcon(pressedIcon);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(pixelFont.deriveFont(20f));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        return button;
    }

    private ImageIcon loadScaledIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }


    @Override
    protected void paintComponent(Graphics g) {
        if (waterTile != null) {
            g.drawImage(waterTile, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }
}
