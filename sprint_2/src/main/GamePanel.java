package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import tile.TileManager;

public class GamePanel extends JPanel {

    private final int boardWidth = 750;
    private final int boardHeight = 750;
    private final int paddingTiles = 1;    
    public final int playTiles = 5;  // playable area - can be changed according to user input
    public final int totalTiles = playTiles + paddingTiles * 2; // 7x7 total for a 5x5 playable area
    public final int tileSize = Math.min(boardWidth, boardHeight) / totalTiles;

    TileManager tileM;

    public GamePanel() {
        this.setPreferredSize(new Dimension(boardWidth, boardHeight)); // fixed gameboard size
        this.setBackground(new Color(156, 212, 200));

        tileM = new TileManager(this);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tileM.draw(g2);

        int startX = (getWidth() - tileSize * totalTiles) / 2;
        int startY = (getHeight() - tileSize * totalTiles) / 2;
        g2.setColor(new Color(0, 0, 0, 50)); // light black, slightly transparent

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
    }
}