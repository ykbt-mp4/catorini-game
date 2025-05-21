package buildings;

public class Building {

    private BuildingLevel level;

    public Building() {
        this.level = BuildingLevel.GROUND;
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
}
