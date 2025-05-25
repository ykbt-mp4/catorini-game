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

    protected void loadPlayerImage() {
        String imagePath = "/actors/character.png";
        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, int x, int y, int width, int height) {
        if (playerImage != null) {
            g2.drawImage(playerImage, x, y, width, height, null);
        }
    }
}