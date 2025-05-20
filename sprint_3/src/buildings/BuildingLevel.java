package buildings;

public enum BuildingLevel {
    GROUND(0, null),
    LEVEL_ONE(1, "/buildings/block1.png"),
    LEVEL_TWO(2, "/buildings/block2.png"),
    LEVEL_THREE(3, "/buildings/block3.png"),
    DOME(4, "/buildings/dome.png");

    private final int height;
    private final String imagePath;

    /**
     * Constructs a BuildingLevel enum constant.
     *
     * @param height The numerical height value
     * @param imagePath Path to the image resource for this level, or null for ground
     */
    BuildingLevel(int height, String imagePath) {
        this.height = height;
        this.imagePath = imagePath;
    }

    /**
     * Gets the numerical height value of this building level.
     *
     * @return The height value (0 for ground, 1-3 for levels, 4 for dome)
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the path to the image resource for this building level.
     *
     * @return The image resource path, or null for ground level
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Determines if this level can be built upon.
     *
     * @return true if this level can be upgraded (not a dome), false otherwise (dome)
     */
    public boolean isBuildable() {
        return this != DOME;
    }

    /**
     * Gets the next level in the building progression.
     *
     * @return The next BuildingLevel in sequence, or null if this is the maximum level (dome)
     */
    public BuildingLevel getNextLevel() {
        switch (this) {
            case GROUND: return LEVEL_ONE;
            case LEVEL_ONE: return LEVEL_TWO;
            case LEVEL_TWO: return LEVEL_THREE;
            case LEVEL_THREE: return DOME;
            default: return null; // DOME has no next level
        }
    }
}