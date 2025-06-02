package buildings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;

/**
 * Represents a building with multiple levels, each having
 * associated images and heights.
 */
public class Building {

    private BuildingLevel level;
    private final EnumMap<BuildingLevel, BufferedImage> levelImages;

    /**
     * Constructs a Building starting at the ground level
     * and loads the images for all building levels.
     */
    public Building() {
        this.level = BuildingLevel.GROUND;
        this.levelImages = new EnumMap<>(BuildingLevel.class);
        loadBuildingImages();
    }

    /**
     * Checks if the building can be further built upon
     * (i.e. has not reached the dome level).
     * @return true if buildable, false if at dome level
     */
    public boolean canBuild() {
        return level != BuildingLevel.DOME;
    }

    /**
     * Builds the next level of the building if possible.
     * Prints a message if the building is already at the dome.
     */
    public void build() {
        if (canBuild()) {
            level = level.getNextLevel();
        } else {
            System.out.println("Cannot build further. Already at dome.");
        }
    }

    /**
     * Checks if the building currently has a dome.
     * @return true if the current level is dome, false otherwise
     */
    public boolean hasDome() {
        return level == BuildingLevel.DOME;
    }

    /**
     * Gets the height value of the current building level.
     * @return the height of the current level
     */
    public int getHeight() {
        return level.getHeight();
    }

    /**
     * Loads images for all building levels from their respective
     * resource paths into an EnumMap.
     */
    private void loadBuildingImages() {
        for (BuildingLevel lvl : BuildingLevel.values()) {
            String path = lvl.getImagePath();
            if (path != null) {
                try {
                    levelImages.put(lvl, ImageIO.read(getClass().getResourceAsStream(path)));
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Draws the building up to its current level.
     * Draws images for all levels from ground up to the current level.
     * @param g2 the graphics context
     * @param x the x position to draw
     * @param y the y position to draw
     * @param width the width to draw the image
     * @param height the height to draw the image
     */
    public void draw(Graphics2D g2, int x, int y, int width, int height) {
        for (BuildingLevel lvl : BuildingLevel.values()) {
            if (lvl.getHeight() > level.getHeight()) {
                break;
            }

            BufferedImage img = levelImages.get(lvl);
            if (img != null) {
                g2.drawImage(img, x, y, width, height, null);
            }
        }
    }
}
