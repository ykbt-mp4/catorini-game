package view;

import actors.Worker;
import main.GamePanel;
import main.Tile;
import util.FontLoader;
import actors.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * Handles the visual rendering of the game board, including tiles, workers,
 * buildings, highlights of valid moves, UI buttons, and timer display.
 */
public class BoardView {
    private final GamePanel gp;
    private final Tile[] tile;
    private JLabel player1TimerLabel;
    private JLabel player2TimerLabel;
    private final FontLoader fontLoader;

    private final int startX = 0;
    private final int startY = 0;

    /**
     * Constructs a BoardView with a reference to the main GamePanel.
     * @param gp the GamePanel containing the game state.
     */
    public BoardView(GamePanel gp) {
        this.gp = gp;
        this.tile = new Tile[10];
        this.fontLoader = new FontLoader();
        getTileImage();
    }

    /**
     * Loads tile images from the resource folder and stores them in the tile array.
     */
    private void getTileImage() {
        try {
            tile[0] = new Tile(0, 0);
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass1.png")));
            tile[1] = new Tile(0, 0);
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass2.png")));
            tile[2] = new Tile(0, 0);
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass3.png")));
            tile[3] = new Tile(0, 0);
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass4.png")));
            tile[4] = new Tile(0, 0);
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass5.png")));
            tile[5] = new Tile(0, 0);
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass6.png")));
            tile[6] = new Tile(0, 0);
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/hills1.png")));
            tile[7] = new Tile(0, 0);
            tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/hills2.png")));
            tile[8] = new Tile(0, 0);
            tile[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/hills3.png")));
            tile[9] = new Tile(0, 0);
            tile[9].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws water tiles as the background for the board.
     * @param g2 the graphics context to draw on
     */
    public void drawWaterTile(Graphics2D g2) {
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                int x = startX + col * 100;
                int y = startY + row * 100;
                g2.drawImage(tile[9].image, x, y, 100, 100, null);
            }
        }
    }

    /**
     * Draws the main playable and edge tiles of the game board.
     * @param g2 the graphics context to draw on
     */
    public void drawBoardTiles(Graphics2D g2) {
        int tileSize = gp.tileSize;
        int playTiles = gp.playTiles;

        // Top edge
        for (int col = 1; col <= playTiles - 1; col++) {
            int x = startX + col * tileSize;
            int y = startY + tileSize;
            g2.drawImage(tile[1].image, x, y, tileSize, tileSize, null);
        }

        // Bottom edge
        for (int col = 1; col <= playTiles - 1; col++) {
            int x = startX + col * tileSize;
            int y = startY + playTiles * tileSize;
            g2.drawImage(tile[7].image, x, y, tileSize, tileSize, null);
        }

        // Left edge
        for (int row = 1; row <= playTiles - 1; row++) {
            int x = startX + tileSize;
            int y = startY + row * tileSize;
            g2.drawImage(tile[3].image, x, y, tileSize, tileSize, null);
        }

        // Right edge
        for (int row = 1; row <= playTiles - 1; row++) {
            int x = startX + playTiles * tileSize;
            int y = startY + row * tileSize;
            g2.drawImage(tile[5].image, x, y, tileSize, tileSize, null);
        }

        // Middle playable area
        for (int row = 1; row < playTiles - 1; row++) {
            for (int col = 1; col < playTiles - 1; col++) {
                int x = startX + (col + 1) * tileSize;
                int y = startY + (row + 1) * tileSize;
                g2.drawImage(tile[4].image, x, y, tileSize, tileSize, null);
            }
        }

        // Draw corners
        g2.drawImage(tile[0].image, startX + tileSize, startY + tileSize, tileSize, tileSize, null); // top-left
        g2.drawImage(tile[2].image, startX + playTiles * tileSize, startY + tileSize, tileSize, tileSize, null); // top-right
        g2.drawImage(tile[6].image, startX + tileSize, startY + playTiles * tileSize, tileSize, tileSize, null); // bottom-left
        g2.drawImage(tile[8].image, startX + playTiles * tileSize, startY + playTiles * tileSize, tileSize, tileSize, null); // bottom-right
    }

    /**
     * Draws buildings on the board according to their positions.
     * @param g2 the graphics context to draw on
     */
    public void drawBuildings(Graphics2D g2) {
        int tileSize = gp.tileSize;
        int playTiles = gp.playTiles;
        Tile[][] board = gp.getBoard();

        for (int row = 0; row < playTiles; row++) {
            for (int col = 0; col < playTiles; col++) {
                Tile tile = board[row][col];
                int x = startX + tileSize + col * tileSize;
                int y = startY + tileSize + row * tileSize;

                tile.building.draw(g2, x, y, tileSize, tileSize);
            }
        }
    }

    /**
     * Draws all workers and their associated player labels.
     * @param g2 the graphics context to draw on
     */
    public void drawWorkers(Graphics2D g2) {
        g2.setFont(fontLoader.getPixelFont().deriveFont(20f));
        int tileSize = gp.tileSize;

        for (Worker worker : gp.workerPos) {
            int x = startX + tileSize + worker.getCol() * tileSize;
            int y = startY + tileSize + worker.getRow() * tileSize;

            Player player = worker.getPlayerId() == gp.player1.getPlayerId() ? gp.player1 : gp.player2;
            String name = "Player " + player.getPlayerId();
            player.draw(g2, x, y, tileSize, tileSize);
            g2.drawString(name, x, y);
        }
    }

    /**
     * Draws highlighted tiles where the player can take valid actions.
     * @param g2 the graphics context to draw on
     */
    public void drawValidTiles(Graphics2D g2) {
        Tile[][] board = gp.getBoard();
        int tileSize = gp.tileSize;

        Image tileHighlight = new ImageIcon(getClass().getResource("/uiextra/tilehighlight.png")).getImage();

        for (int row = 0; row < gp.playTiles; row++) {
            for (int col = 0; col < gp.playTiles; col++) {
                if (board[row][col].isHighlighted()) {
                    int x = tileSize + col * tileSize;
                    int y = tileSize + row * tileSize;
                    g2.drawImage(tileHighlight, x, y, tileSize, tileSize, null);
                }
            }
        }
    }

    /**
     * Highlights a selected worker on the board.
     * @param g2 the graphics context to draw on
     * @param selectedWorker the currently selected worker
     */
    public void drawWorkerHighlight(Graphics2D g2, Worker selectedWorker) {
        int tileSize = gp.tileSize;
        Image workerHighlight = new ImageIcon(getClass().getResource("/uiextra/workerhighlight.png")).getImage();

        if (selectedWorker != null) {
            int x = tileSize + selectedWorker.getCol() * tileSize;
            int y = tileSize + selectedWorker.getRow() * tileSize;
            g2.drawImage(workerHighlight, x, y, tileSize, tileSize, null);
        }
    }

    /**
     * Creates the UI panel with the "Skip" button (can add extra buttons).
     * @return a JPanel containing the skip button
     */
    public JPanel createButtonPanel() {
        Font pixelFont = fontLoader.getPixelFont().deriveFont(20f);
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

        JButton skipButton = new JButton("Skip");
        skipButton.setIcon(defaultIcon);
        skipButton.setPressedIcon(pressedIcon);
        skipButton.setHorizontalTextPosition(SwingConstants.CENTER);
        skipButton.setVerticalTextPosition(SwingConstants.CENTER);
        skipButton.setFont(pixelFont);
        skipButton.setBorderPainted(false);
        skipButton.setContentAreaFilled(false);
        skipButton.setFocusPainted(false);
        skipButton.setOpaque(false);

        skipButton.addActionListener(e -> gp.turnManager.skipCurrentAction());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(skipButton);

        return buttonPanel;
    }

    /**
     * Creates the UI panel that displays the turn timers for both players.
     * @return a JPanel containing the timer labels
     */
    public JPanel createTimerPanel() {
        Font pixelFont = fontLoader.getPixelFont().deriveFont(20f);

        // Create timer labels for both players
        player1TimerLabel = new JLabel("Player 1 Time: 15:00");
        player1TimerLabel.setFont(pixelFont);
        player1TimerLabel.setForeground(Color.BLACK);
        player1TimerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        player1TimerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        player2TimerLabel = new JLabel("Player 2 Time: 15:00");
        player2TimerLabel.setFont(pixelFont);
        player2TimerLabel.setForeground(Color.BLACK);
        player2TimerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        player2TimerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Timer panel with GridLayout to place labels side by side
        JPanel timerPanel = new JPanel(new GridLayout(2, 1));
        timerPanel.setOpaque(false);
        timerPanel.add(player1TimerLabel);
        timerPanel.add(player2TimerLabel);

        return timerPanel;
    }

    /**
     * Gets the label used to display player 1's timer.
     * @return the player 1 timer JLabel
     */
    public JLabel getPlayer1TimerLabel() {
        return player1TimerLabel;
    }

    /**
     * Gets the label used to display player 2's timer.
     * @return the player 2 timer JLabel
     */
    public JLabel getPlayer2TimerLabel() {
        return player2TimerLabel;
    }
}