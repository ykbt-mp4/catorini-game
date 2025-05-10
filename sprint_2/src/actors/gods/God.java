package actors.gods;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Abstract class representing a God card with a unique name and image.
 * Provides shared methods for loading and drawing the God image,
 * as well as abstract methods to be implemented by each specific God.
 */
public abstract class God {
    /** The name of the God. */
    protected String name;
    /** The image representing the God. */
    protected BufferedImage godImage;

    /**
     * Constructs a God with the specified name.
     *
     * @param name the name of the God
     */
    public God(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the God.
     *
     * @return the name of the God
     */
    public String getName() {
        return name;
    }

    /**
     * Loads the image for the God from the given resource path.
     *
     * @param imagePath the resource path of the image
     */
    protected void loadGodImage(String imagePath) {
        try {
            godImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the God's image and name on the provided graphics context.
     *
     * @param g2     the graphics context
     * @param x      the x-coordinate of the image
     * @param y      the y-coordinate of the image
     * @param width  the width of the image
     * @param height the height of the image
     */
    public void draw(Graphics2D g2, int x, int y, int width, int height) {
        if (godImage != null) {
            g2.drawImage(godImage, x, y, width, height, null);
            int godNameWidth = g2.getFontMetrics().stringWidth(name);
            g2.drawString(name, x + (width - godNameWidth) / 2, y);
        }
    }

    /**
     * Performs the build action defined by the specific God.
     */
    public abstract void build();

    /**
     * Performs the move action defined by the specific God.
     */
    public abstract void move();
}
