package actors;

import actors.Worker;
import actors.gods.God;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game, containing their workers, God Card, and worker image.
 * Each player has a unique ID and manages their own game pieces and abilities.
 */
public class Player {
    private final int playerId;
    private final List<Worker> workers;
    protected BufferedImage playerImage;
    private God godCard;

    /**
     * Constructs a new Player with a specific ID.
     * Initializes the worker list and loads the worker image for that specific player
     *
     * @param playerId The unique identifier for this player (1 or 2)
     */
    public Player(int playerId) {
        this.playerId = playerId;
        this.workers = new ArrayList<>();
        loadPlayerImage();

    }

    /**
     * Gets the unique identifier for this player.
     *
     * @return The player's ID number
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets the list of workers and their positions on the board that belong to the player.
     *
     * @return A list of the player's Worker objects
     */
    public List<Worker> getWorkers() {
        return workers;
    }

    /**
     * Assigns a god card to this player.
     *
     * @param godCard The God card that will be assigned to this player
     */
    public void setGodCard(God godCard) {
        this.godCard = godCard;
    }

    /**
     * Gets the god card assigned to this player.
     *
     * @return The player's God card, or null if not assigned
     */
    public God getGodCard() {
        return godCard;
    }

    /**
     * Loads the image that represents a specific player based on their ID.
     */
    protected void loadPlayerImage() {
        String imagePath = String.format("/worker_p%d.png", playerId);
        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the player's worker image at a specified position with a specified size.
     *
     * @param g2 The Graphics2D context to draw with
     * @param x The x coordinate (top-left corner)
     * @param y The y coordinate (top-left corner)
     * @param width The width of the image
     * @param height The height of the image
     */
    public void draw(Graphics2D g2, int x, int y, int width, int height) {
        if (playerImage != null) {
            g2.drawImage(playerImage, x, y, width, height, null);
        }
    }
}
