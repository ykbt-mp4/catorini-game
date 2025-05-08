package buildings;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public abstract class Building {
    
    private String name;
    public BufferedImage buildingImage;
    
    public Building(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getBuildingImage() {
        return buildingImage;
    }

    public void loadBuildingImage(String imagePath) {
        try {
            buildingImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
