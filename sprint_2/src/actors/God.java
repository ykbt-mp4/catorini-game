package actors;

import javax.imageio.ImageIO;
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

    public BufferedImage getGodImage() {
        return godImage;
    }

    protected void loadGodImage(String imagePath) {
        try {
            godImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Abstract methods to be implemented by subclasses
    public abstract void build();
    public abstract void move();
}
