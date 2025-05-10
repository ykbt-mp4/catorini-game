package actors.gods;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class God {

    protected String name;
    protected BufferedImage godImage;

    public God(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected void loadGodImage(String imagePath) {
        try {
            godImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, int x, int y, int width, int height) {
        if (godImage != null) {
            g2.drawImage(godImage, x, y, width, height, null);

            int godNameWidth = g2.getFontMetrics().stringWidth(name);
//            int labelWidth = g2.getFontMetrics().stringWidth(playerLabel);

//            g2.drawString(playerLabel, x + (width - labelWidth) / 2, y);
            g2.drawString(name, x + (width - godNameWidth) / 2, y);
        }
    }

    // Abstract methods to be implemented by subclasses
    public abstract void build();
    public abstract void move();
}
