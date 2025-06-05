package buildings;

/**
 * Enum representing the building levels from ground to dome,
 * each with a height value and an optional image path.
 */
public enum BuildingLevel {
    GROUND(0, null),
    LEVEL_ONE(1, "/buildings/building1.png"),
    LEVEL_TWO(2, "/buildings/building2.png"),
    LEVEL_THREE(3, "/buildings/building3.png"),
    DOME(4, "/buildings/flowerdome.png");

    private final int height;
    private final String imagePath;

    /**
     * Constructs a BuildingLevel with a height and image path.
     * @param height the height level of the building
     * @param imagePath the path to the image resource for this level
     */
    BuildingLevel(int height, String imagePath) {
        this.height = height;
        this.imagePath = imagePath;
    }

    /**
     * Returns the height of this building level.
     * @return the height as an integer
     */
    public int getHeight() {
        return height;
    }


    /**
     * Returns the image path for this building level.
     * @return the image path as a String, or null if none
     */
    public String getImagePath() {
        return imagePath;
    }


    /**
     * Returns the next building level after this one.
     * If already at the highest level (DOME), returns DOME.
     * @return the next BuildingLevel
     */
    public BuildingLevel getNextLevel() {
        return switch (this) {
            case GROUND -> LEVEL_ONE;
            case LEVEL_ONE -> LEVEL_TWO;
            case LEVEL_TWO -> LEVEL_THREE;
            case LEVEL_THREE -> DOME;
            case DOME -> DOME;
        };
    }
}
