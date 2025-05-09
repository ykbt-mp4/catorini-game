package buildings;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Building {
    
    private String name;
    public BufferedImage buildingImage;
    private BuildingLevel level;
    public int row;
    public int col;

    
    public Building(String name, BuildingLevel level, int row, int col) {
        this.name = name;
        this.level = level;
        this.row = row;
        this.col = col;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getBuildingImage() {
        return buildingImage;
    }

    public BuildingLevel getLevel() {
        return level;
        // To get next level call the getNextLevel() method from BuildingLevel enum
    }

    public void setLevel(BuildingLevel level) {
        this.level = level;
        loadBuildingImage(level.getImagePath());
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void loadBuildingImage(String imagePath) {
        try {
            buildingImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
