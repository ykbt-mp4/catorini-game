package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import actors.Player;
import tile.TileManager;
import util.FontLoader;

public class GamePanel extends JPanel {

    // Board dimensions
    private final int boardWidth = 750;
    private final int boardHeight = 750;

    // Tile setup
    private final int paddingTiles = 1;
    public final int playTiles = 10;
    public final int totalTiles = playTiles + paddingTiles * 2;
    public final int tileSize = Math.min(boardWidth, boardHeight) / totalTiles;

    // Players
    private final Player player1;
    private final Player player2;

    // Components
    private final TileManager tileManager;
    private final FontLoader fontLoader;

    public GamePanel(Player player1, Player player2) {

        this.setPreferredSize(new Dimension(boardWidth, boardHeight)); // fixed gameboard size
        this.setBackground(new Color(156, 212, 200));
        this.player1 = player1;
        this.player2 = player2;

        this.tileManager = new TileManager(this);
        this.fontLoader = new FontLoader();

    }

    // Public accessors for board coordinates
    public int getBoardX() {
        return (getWidth() - tileSize * totalTiles) / 2;
    }

    public int getBoardY() {
        return (getHeight() - tileSize * totalTiles) / 2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);
        g2.setFont(fontLoader.getPixelFont());
        g2.setColor(Color.BLACK); // light black, slightly transparent

        // REFERENCE POINTS PURPOSES !!
        g2.drawOval(getBoardX(), getBoardY(), tileSize, tileSize);
        // Draw vertical lines
        for (int col = 0; col <= totalTiles; col++) {
            int x = getBoardX() + col * tileSize;
            g2.drawLine(x, 0, x, getHeight());
        } // Draw horizontal lines
        for (int row = 0; row <= totalTiles; row++) {
            int y = getBoardY() + row * tileSize;
            g2.drawLine(0, y, getWidth(), y);
        }

        // drawing the god cards for display
        int imageWidth = 135;
        int imageHeight = 180;

        if (player1.getGodCard() != null) {
            int x1 = getBoardX() - imageWidth;
            int y1 = (getHeight() - imageHeight) / 2;
            player1.getGodCard().draw(g2, x1, y1, imageWidth, imageHeight, "Player 1");
        }

        if (player2.getGodCard() != null) {
            int x2 = getBoardX() + totalTiles * tileSize;
            int y2 = (getHeight() - imageHeight) / 2;
            player2.getGodCard().draw(g2, x2, y2, imageWidth, imageHeight, "Player 2");
        }
    }

}