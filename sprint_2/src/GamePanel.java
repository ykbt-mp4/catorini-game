package sprint_2.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import sprint_2.src.tile.TileManager;

public class GamePanel extends JPanel {

    private final int boardWidth = 750;
    private final int boardHeight = 750;
    private final int paddingTiles = 1;    
    public final int playTiles = 5;  // playable area - can be changed according to user input 
    public final int totalTiles = playTiles + paddingTiles * 2; // 7x7 total for a 5x5 playable area
    public final int tileSize = Math.min(boardWidth, boardHeight) / totalTiles;

    TileManager tileM;

    public GamePanel() {
        this.setPreferredSize(new Dimension(boardHeight, boardWidth)); // fixed gameboard size
        this.setBackground(new Color(156, 212, 200));

        tileM = new TileManager(this);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tileM.draw(g2);

    }
}