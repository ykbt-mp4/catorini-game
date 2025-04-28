package sprint_2.src.tile;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import sprint_2.src.GamePanel;

public final class TileManager {
    GamePanel gp;
    Tile[] tile;
    private final int waterTileSize = 107;  // these numbers are fixed so the background stays the same at all times
    private final int waterGridWidth = 7;  
    private final int waterGridHeight = 7;
     
    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        getTileImage();

    }

    // load tile images
    public void getTileImage() {
        try {
    
            tile[0] = new Tile(); 
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass1.png"));

            tile[1] = new Tile(); 
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass2.png"));

            tile[2] = new Tile(); 
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass3.png"));

            tile[3] = new Tile(); 
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass4.png"));

            tile[4] = new Tile(); 
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass5.png"));

            tile[5] = new Tile(); 
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass6.png"));

            tile[6] = new Tile(); 
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hills1.png"));

            tile[7] = new Tile(); 
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hills2.png"));

            tile[8] = new Tile(); 
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hills3.png"));

            tile[9] = new Tile(); 
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
    
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    // drawing the gameboard
    public void draw(Graphics2D g2){
        
        int startX = (gp.getWidth() - gp.tileSize * gp.totalTiles) / 2; 
        int startY = (gp.getHeight() - gp.tileSize * gp.totalTiles) / 2;

        // draw the water tiles
        for (int row = 0; row < waterGridWidth; row++) {
            for (int col = 0; col < waterGridHeight; col++) {
                int x = waterGridWidth + col * waterTileSize;
                int y = waterGridHeight + row * waterTileSize;
                
                // Draw the water tile with the separate size
                g2.drawImage(tile[9].image, x, y, waterTileSize, waterTileSize, null);
            }
        }

        // Left top corner
        g2.drawImage(tile[0].image, startX + 1 * gp.tileSize, startY + 1 * gp.tileSize, gp.tileSize, gp.tileSize, null);

        // Left bottom corner
        g2.drawImage(tile[6].image, startX + 1 * gp.tileSize, startY + (gp.totalTiles - 2) * gp.tileSize, gp.tileSize, gp.tileSize, null);

        // Right top corner
        g2.drawImage(tile[2].image, startX + (gp.totalTiles - 2) * gp.tileSize, startY + 1 * gp.tileSize, gp.tileSize, gp.tileSize, null);

        // Right bottom corner
        g2.drawImage(tile[8].image, startX + (gp.totalTiles - 2) * gp.tileSize, startY + (gp.totalTiles - 2) * gp.tileSize, gp.tileSize, gp.tileSize, null);

        // Draw the top row of tiles
        for (int col = 2; col < gp.totalTiles - 2; col++) {
            int x = startX + col * gp.tileSize; 
            int y = startY + 1 * gp.tileSize; 
            g2.drawImage(tile[1].image, x, y, gp.tileSize, gp.tileSize, null);
        }

        // draw the bottom row
        for (int col = 2; col < gp.totalTiles - 2; col++) {
            int x = startX + col * gp.tileSize;
            int y = startY + (gp.totalTiles - 2) * gp.tileSize;
            g2.drawImage(tile[7].image, x, y, gp.tileSize, gp.tileSize, null);
        }

        // draw the left column
        for (int row = 2; row < gp.totalTiles - 2; row++) {
            int x = startX + 1 * gp.tileSize;
            int y = startY + row * gp.tileSize;
            g2.drawImage(tile[3].image, x, y, gp.tileSize, gp.tileSize, null);
        }

        // draw the right column
        for (int row = 2; row < gp.totalTiles - 2; row++) {
            int x = startX + (gp.totalTiles - 2) * gp.tileSize;
            int y = startY + row * gp.tileSize; 
            g2.drawImage(tile[5].image, x, y, gp.tileSize, gp.tileSize, null);
        }

        // draw the middle tiles
        for (int row = 2; row < gp.playTiles; row++) {
            for (int col = 2; col < gp.playTiles; col++) {
                int x = startX + col * gp.tileSize;
                int y = startY + row * gp.tileSize;
                g2.drawImage(tile[4].image, x, y, gp.tileSize, gp.tileSize, null);
            }
        }   
    }
}
