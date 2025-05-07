package actors;

import actors.Worker;
import actors.gods.God;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int playerId;
    private final List<Worker> workers;
    protected BufferedImage playerImage;
    private God godCard;

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

    public void setGodCard(God godCard) {
        this.godCard = godCard;
    }

    public God getGodCard() {
        return godCard;
    }

    protected void loadPlayerImage() {
        String imagePath = String.format("/worker_p%d.png", playerId);
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
