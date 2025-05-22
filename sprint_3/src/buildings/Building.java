package buildings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;

public class Building {

    private BuildingLevel level;
    private final EnumMap<BuildingLevel, BufferedImage> levelImages;

    public Building() {
        this.level = BuildingLevel.GROUND;
        this.levelImages = new EnumMap<>(BuildingLevel.class);
        loadBuildingImages();
    }

    public boolean canBuild() {
        return level != BuildingLevel.DOME;
    }

    public void build() {
        if (canBuild()) {
            level = level.getNextLevel();
        } else {
            System.out.println("Cannot build further. Already at dome.");
        }
    }

    public boolean hasDome() {
        return level == BuildingLevel.DOME;
    }

    public int getHeight() {
        return level.getHeight();
    }

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

    public void draw(Graphics2D g2, int x, int y, int width, int height) {
        for (BuildingLevel lvl : BuildingLevel.values()) {
            if (lvl.getHeight() > level.getHeight()) {
                break; // Only draw up to current level
            }

            BufferedImage img = levelImages.get(lvl);
            if (img != null) {
                g2.drawImage(img, x, y, width, height, null);
            }
        }
    }

}
