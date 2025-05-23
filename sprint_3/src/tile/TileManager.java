package tile;

import actors.Worker;
import main.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

import actors.Player;
import util.FontLoader;

public final class TileManager {
    private final GamePanel gp;
    private final Tile[] tile;

    private final FontLoader fontLoader;


    int startX = 0;
    int startY = 0;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        this.tile = new Tile[10]; // Store different tile graphics
        getTileImage();
        this.fontLoader = new FontLoader();
    }

    public void getTileImage() {
        try {

            tile[0] = new Tile(0, 0);
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass1.png")));

            tile[1] = new Tile(0, 0);
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass2.png")));

            tile[2] = new Tile(0,0);
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass3.png")));

            tile[3] = new Tile(0,0);
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass4.png")));

            tile[4] = new Tile(0,0);
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass5.png")));

            tile[5] = new Tile(0,0);
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass6.png")));

            tile[6] = new Tile(0,0);
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/hills1.png")));

            tile[7] = new Tile(0,0);
            tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/hills2.png")));

            tile[8] = new Tile(0,0);
            tile[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/hills3.png")));

            tile[9] = new Tile(0,0);
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
            g2.drawImage(tile[7].image, x, y, tileSize, tileSize , null);
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

    public void drawWorkers(Graphics2D g2) {
        g2.setFont(fontLoader.getPixelFont().deriveFont(20f));

        int tileSize = gp.tileSize;

        for (Worker worker : gp.workerPos) {
            int x = startX + tileSize + worker.getCol() * tileSize;
            int y = startY + tileSize + worker.getRow() * tileSize;

            Player player = worker.getPlayerId() == gp.player1.getPlayerId() ? gp.player1 : gp.player2;
            String name = "Player" + player.getPlayerId();
            player.draw(g2, x, y, tileSize, tileSize);
            g2.drawString(name, x, y);
        }
    }

    public void drawValidTiles(Graphics2D g2) {
        Tile[][] board = gp.getBoard();
        int tileSize = gp.tileSize;

        Image tileHighlight = new ImageIcon(getClass().getResource("/uiextra/tilehighlight.png")).getImage();

        for (int row = 0; row < gp.playTiles; row++) {
            for (int col = 0; col < gp.playTiles; col++) {
                if (board[row][col].isHighlighted()) {
                    int x = gp.tileSize + col * tileSize;
                    int y = gp.tileSize + row * tileSize;
                    g2.drawImage(tileHighlight, x, y, tileSize, tileSize, null);
                }
            }
        }
    }

}
