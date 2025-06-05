package actors;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import actors.gods.God;

import javax.imageio.ImageIO;

/**
 * Represents a player in the game.
 * Each player has a unique ID, name, a set of workers, an optional god card, and a player image.
 */
public class Player {
    private final int playerId;
    private final String playerName;
    private final List<Worker> workers;
    private God godCard;

    protected BufferedImage playerImage;


    /**
     * Constructs a Player with the specified ID and name.
     * @param playerId   the unique identifier for the player
     * @param playerName the name of the player
     */
    public Player(int playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.workers = new ArrayList<>();
        loadPlayerImage();

    }

    /**
     * Returns the player's name.
     * @return the name of the player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Returns the player's ID.
     * @return the ID of the player
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Returns the list of the player's workers.
     * @return the list of workers
     */
    public List<Worker> getWorkers() {
        return workers;
    }

    /**
     * Returns the player's god card.
     * @return the God associated with the player, or null if none is set
     */
    public God getGodCard() {
        return godCard;
    }

    /**
     * Sets the god card for the player.
     * @param godCard the God to associate with the player
     */
    public void setGodCard(God godCard) {
        this.godCard = godCard;
    }

    /**
     * Loads the player's image from the resource path.
     */
    protected void loadPlayerImage() {
        String imagePath = "/actors/character.png";
        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the player's image at the specified location.
     * @param g2     the Graphics2D context
     * @param x      the x-coordinate of the image
     * @param y      the y-coordinate of the image
     * @param width  the width to draw the image
     * @param height the height to draw the image
     */
    public void draw(Graphics2D g2, int x, int y, int width, int height) {
        if (playerImage != null) {
            g2.drawImage(playerImage, x, y, width, height, null);
        }
    }
}