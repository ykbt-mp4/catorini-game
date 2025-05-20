package buildings;

public enum BuildingLevel {
    GROUND(0, null),
    LEVEL_ONE(1, "/buildings/block1.png"),
    LEVEL_TWO(2, "/buildings/block2.png"),
    LEVEL_THREE(3, "/buildings/block3.png"),
    DOME(4, "/buildings/dome.png");

    private final int height;
    private final String imagePath;

    BuildingLevel(int height, String imagePath) {
        this.height = height;
        this.imagePath = imagePath;
    }

    public int getHeight() {
        return height;
    }

    public String getImagePath() {
        return imagePath;
    }

    public BuildingLevel getNextLevel() {
        return switch (this) {
            case GROUND -> LEVEL_ONE;
            case LEVEL_ONE -> LEVEL_TWO;
            case LEVEL_TWO -> LEVEL_THREE;
            case LEVEL_THREE -> DOME;
            case DOME -> DOME; // No change after dome
        };
    }
}
