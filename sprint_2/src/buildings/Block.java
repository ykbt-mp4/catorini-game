package buildings;

public class Block extends Building {
    private BuildingLevel level;

    public Block(BuildingLevel level) {
        super("Block");
        this.level = level;
        loadBuildingImage(level.getImagePath());

    }

    public BuildingLevel getLevel() {
        return level;
    }

    public boolean isBuildable() {
        return level.isBuildable();
    }

    public Block getNextBlock() {
        BuildingLevel nextLevel = level.getNextLevel();
        return nextLevel != null ? new Block(nextLevel) : null;
    }

}
