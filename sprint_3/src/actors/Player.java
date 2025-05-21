package actors;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import actors.gods.God;

import javax.imageio.ImageIO;

public class Player {
    private final int playerId;
    private final List<Worker> workers;
    private God godCard;

    protected BufferedImage playerImage;

    public Player(int playerId) {
        this.playerId = playerId;
        this.workers = new ArrayList<>();
        loadPlayerImage();

    }

    public int getPlayerId() {
        return playerId;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public God getGodCard() {
        return godCard;
    }

    public void setGodCard(God godCard) {
        this.godCard = godCard;
    }

    /**
     * Loads the image that represents a specific player based on their ID.
     */
    protected void loadPlayerImage() {
        String imagePath = String.format("/actors/worker%d.png", playerId);
        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the player's worker image at a specified position with a specified size.
     *
     * @param g2     The Graphics2D context to draw with
     * @param x      The x coordinate (top-left corner)
     * @param y      The y coordinate (top-left corner)
     * @param width  The width of the image
     * @param height The height of the image
     */
    public void draw(Graphics2D g2, int x, int y, int width, int height) {
        if (playerImage != null) {
            g2.drawImage(playerImage, x, y, width, height, null);
        }
    }
}