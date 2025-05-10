package buildings;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Represents a building on the game board, with a specific level and position.
 * Handles the visual representation of buildings and the transition between levels.
 */
public class Building {
    
    private String name;
    public BufferedImage buildingImage;
    private BuildingLevel level;
    public int row;
    public int col;

    /**
     * Constructs a building with specified attributes.
     *
     * @param name The name/type of the building (Block or Dome)
     * @param level The initial level of the building
     * @param row The row position on the game board
     * @param col The column position on the game board
     */
    public Building(String name, BuildingLevel level, int row, int col) {
        this.name = name;
        this.level = level;
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the name of the building.
     *
     * @return The name of the building
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the image associated with the building.
     *
     * @return The BufferedImage containing the sprite for the building
     */
    public BufferedImage getBuildingImage() {
        return buildingImage;
    }

    /**
     * Gets the current level of the building.
     *
     * @return The current level of the building
     */
    public BuildingLevel getLevel() {
        return level;
        // To get next level call the getNextLevel() method from BuildingLevel enum
    }

    /**
     * Sets the level of the building and updates the image associated with the building based on level.
     *
     * @param level The new BuildingLevel to be set
     */
    public void setLevel(BuildingLevel level) {
        this.level = level;
        loadBuildingImage(level.getImagePath());
    }

    /**
     * Gets the row position of the building.
     *
     * @return The row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column position of the building.
     *
     * @return The column index
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the row position of the building.
     *
     * @param row The new row index
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Sets the column position of the building.
     *
     * @param col The new column index
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Loads the sprite of the building from resources.
     *
     * @param imagePath The path to the image resource
     * @throws RuntimeException if the image fails to load
     */
    public void loadBuildingImage(String imagePath) {
        try {
            buildingImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
