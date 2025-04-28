package sprint_2.src;

import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import sprint_2.src.tile.Tile;

public class ControlManager {

        ControlPanel c;
        Tile[] tile;
        private final int waterTileSize = 107;  // Fixed size for water tiles
        private final int waterGridWidth = 7;   // Width of the water grid (number of tiles horizontally)
        private final int waterGridHeight = 7;

    
        public ControlManager(ControlPanel c){
            this.c = c;
            tile = new Tile[10];
            getTileImage();
           
        }
    
        // load tile images
        public void getTileImage() {
            try {
        
                tile[0] = new Tile(); 
                tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hills4.png"));
    
                tile[1] = new Tile(); 
                tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hills5.png"));
    
                tile[2] = new Tile(); 
                tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hills6.png"));

                tile[3] = new Tile(); 
                tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
    
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }

        public void draw(Graphics2D g2) {

            int cols = c.getWidth() / waterTileSize;
            int rows = c.getHeight() / waterTileSize;

            int startX = (c.getWidth() - waterTileSize * cols) / 2;
            int startY = (c.getHeight() - waterTileSize * rows) / 2;

            // Draw the water tiles
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    int x = waterGridWidth + col * waterTileSize;
                    int y = waterGridHeight + row * waterTileSize;
                    
                    // Draw the water tile with the separate size
                    g2.drawImage(tile[3].image, x, y, waterTileSize, waterTileSize, null);
                    }
                }

            // drawing the left side
            for (int row = 1; row < rows; row += 2) {
                for (int col = 0; col < cols; col += c.getWidth()) {
                    int x = startX + col * waterTileSize;
                    int y = startY + row * waterTileSize;
                    g2.drawImage(tile[0].image, x, y, waterTileSize, waterTileSize, null);
                    }
                }

            // drawing the middle piece 
            for (int row = 1; row < rows; row += 2) {
                for (int col = 1; col < cols; col += c.getWidth()) {
                    int x = startX + col * waterTileSize;
                    int y = startY + row * waterTileSize;
                    g2.drawImage(tile[1].image, x, y, waterTileSize, waterTileSize, null);
                }
            }

            // drawing the right side
            for (int row = 1; row < rows; row += 2) {
                for (int col = 2; col < cols; col += c.getWidth()) {
                    int x = startX + col * waterTileSize;
                    int y = startY + row * waterTileSize;
                    g2.drawImage(tile[2].image, x, y, waterTileSize, waterTileSize, null);
                }
            }
            
        }

    }


