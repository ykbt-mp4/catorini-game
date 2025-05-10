package tile;

import main.GamePanel;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public final class TileManager {
    GamePanel gp;
    Tile[] tile;

    int startX = 0;
    int startY = 0;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        getTileImage();
    }

    // load tile images
    public void getTileImage() {
        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass1.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass2.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass3.png")));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass4.png")));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass5.png")));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass6.png")));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/hills1.png")));

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/hills2.png")));

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/hills3.png")));

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void drawWaterTile(Graphics2D g2) {

        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                int x = startX + col * 100;
                int y = startY + row * 100;
                g2.drawImage(tile[9].image, x, y, 100, 100, null);

            }
        }
    }

    // drawing the gameboard
    public void draw(Graphics2D g2) {

        // Left top corner
        g2.drawImage(tile[0].image, startX + gp.tileSize, startY + gp.tileSize, gp.tileSize, gp.tileSize, null);

        // Left bottom corner
        g2.drawImage(tile[6].image, startX + gp.tileSize, gp.boardHeight - 2*gp.tileSize, gp.tileSize, gp.tileSize, null);

        // Right top corner
        g2.drawImage(tile[2].image, gp.boardWidth - 2*gp.tileSize, startY + gp.tileSize, gp.tileSize, gp.tileSize, null);

        // Right bottom corner
        g2.drawImage(tile[8].image, gp.boardWidth - 2*gp.tileSize, gp.boardHeight - 2*gp.tileSize, gp.tileSize, gp.tileSize, null);

        // Draw the top row of tiles
        for (int col = 2; col < gp.tiles - 2; col++) {
            int x = startX + col * gp.tileSize;
            int y = startY + gp.tileSize;
            g2.drawImage(tile[1].image, x, y, gp.tileSize, gp.tileSize, null);
        }

        // draw the bottom row
        for (int col = 2; col < gp.tiles - 2; col++) {
            int x = startX + col * gp.tileSize;
            int y = startY + (gp.tiles - 2) * gp.tileSize;
            g2.drawImage(tile[7].image, x, y, gp.tileSize, gp.tileSize, null);
        }

        // draw the left column
        for (int row = 2; row < gp.tiles - 2; row++) {
            int x = startX + gp.tileSize;
            int y = startY + row * gp.tileSize;
            g2.drawImage(tile[3].image, x, y, gp.tileSize, gp.tileSize, null);
        }

        // draw the right column
        for (int row = 2; row < gp.tiles - 2; row++) {
            int x = startX + (gp.tiles - 2) * gp.tileSize;
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
