package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import actors.Player;
import tile.TileManager;
import util.FontLoader;

public class GamePanel extends JPanel {

    private final int boardWidth = 750;
    private final int boardHeight = 750;
    private final int paddingTiles = 1;
    public final int playTiles = 5;  // playable area - can be changed according to user input
    public final int totalTiles = playTiles + paddingTiles * 2; // 7x7 total for a 5x5 playable area
    public final int tileSize = Math.min(boardWidth, boardHeight) / totalTiles;
    private final Player player1;
    private final Player player2;

    TileManager tileM;
    private FontLoader fontLoader;

    public GamePanel(Player player1, Player player2) {
        this.setPreferredSize(new Dimension(boardWidth, boardHeight)); // fixed gameboard size
        this.setBackground(new Color(156, 212, 200));
        this.player1 = player1;
        this.player2 = player2;

        tileM = new TileManager(this);
        fontLoader = new FontLoader();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        g2.setFont(fontLoader.getPixelFont());

        // just to see the grids for the board and place items correctly
        int startX = (getWidth() - tileSize * totalTiles) / 2;
        int startY = (getHeight() - tileSize * totalTiles) / 2;
        g2.setColor(Color.BLACK); // light black, slightly transparent

        // Draw vertical lines
        for (int col = 0; col <= totalTiles; col++) {
            int x = startX + col * tileSize;
            g2.drawLine(x, 0, x, getHeight());
        }

        // Draw horizontal lines
        for (int row = 0; row <= totalTiles; row++) {
            int y = startY + row * tileSize;
            g2.drawLine(0, y, getWidth(), y);
        }

        int imageWidth = 135;
        int imageHeight = 180;

        BufferedImage img1 = player1.getGodCard().getGodImage();
        BufferedImage img2 = player2.getGodCard().getGodImage();

        if (img1 != null) {
            int x1 = startX - imageWidth; // left side of the board
            int y1 = (getHeight() - imageHeight) / 2;
            g2.drawImage(img1, x1, y1, imageWidth, imageHeight, null);

            String label1 = "Player 1";
            String name1 = player1.getGodCard().getName();

            int textWidth1 = g2.getFontMetrics().stringWidth(name1);
            
            g2.drawString(label1, x1 + (imageWidth - textWidth1) / 2, y1);
            g2.drawString(player1.getGodCard().getName(), x1 + (imageWidth - textWidth1)/2, y1 + imageHeight);

        }

        if (img2 != null) {
            int x2 = startX + totalTiles * tileSize; // left side of the board
            int y2 = (getHeight() - imageHeight) / 2;
            g2.drawImage(img2, x2, y2, imageWidth, imageHeight, null);

            String label2 = "Player 2";
            String name2 = player2.getGodCard().getName();

            int textWidth2 = g2.getFontMetrics().stringWidth(name2);

            g2.drawString(label2, x2 + (imageWidth - textWidth2) / 2, y2);
            g2.drawString(name2, x2 + (imageWidth - textWidth2) / 2, y2 + imageHeight);
        }
    }
}