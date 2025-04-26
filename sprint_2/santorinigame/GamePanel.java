package sprint_2.santorinigame;

import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    final int gridSize = 20; // can replace with user input for dynamic board size
    final int tileSize = 500/gridSize;
    

    // Add 2 extra tiles (1 on each side)
    final int screenWidth = tileSize * (gridSize + 2);
    final int screenHeight = tileSize * (gridSize + 2);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);

        // Start drawing from tileSize (1 tile of padding)
        int startX = tileSize;
        int startY = tileSize;

        // Draw horizontal lines
        for (int row = 0; row <= gridSize; row++) {
            int y = startY + row * tileSize;
            g.drawLine(startX, y, startX + gridSize * tileSize, y);
        }

        // Draw vertical lines
        for (int col = 0; col <= gridSize; col++) {
            int x = startX + col * tileSize;
            g.drawLine(x, startY, x, startY + gridSize * tileSize);
        }
    }
}
