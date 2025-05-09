package actions;

import actors.Worker;
import main.GamePanel;
import java.util.ArrayList;
import java.util.Optional;

import buildings.Building;
import buildings.BuildingLevel;

public class BuildAction {

    // Blocks can be built on empty tiles, tiles with a height of 3 or less, or adjacent to the worker's current position.
    public boolean canBuild(Worker worker, int targetRow, int targetCol, ArrayList<Worker> allWorkers, ArrayList<Building> allBuildings) {
        int rowDiff = Math.abs(worker.getRow() - targetRow);
        int colDiff = Math.abs(worker.getCol() - targetCol);

        // Check for adjacency
        if (!((rowDiff == 1 && colDiff == 0) ||  // vertical
              (rowDiff == 0 && colDiff == 1) ||  // horizontal
              (rowDiff == 1 && colDiff == 1))) { // diagonal
            return false; // Not adjacent
        }

        // Check if target tile is occupied by another worker
        for (Worker w : allWorkers) {
            if (w.getRow() == targetRow && w.getCol() == targetCol) {
                return false; // Cannot build on a tile occupied by another worker
            }
        }

        // Check building status on the target tile
        Optional<Building> existingBuildingOpt = allBuildings.stream()
                .filter(b -> b.getRow() == targetRow && b.getCol() == targetCol)
                .findFirst();

        if (existingBuildingOpt.isPresent()) {
            Building existingBuilding = existingBuildingOpt.get();
            // Can build if the existing building is buildable (i.e., not a Dome)
            return existingBuilding.getLevel().isBuildable();
        } else {
            return true; // Can build on an empty tile
        }
    }
    
    public void build(int targetRow, int targetCol) {
        Optional<Building> existingBuildingOpt = GamePanel.buildings.stream()
                .filter(b -> b.getRow() == targetRow && b.getCol() == targetCol)
                .findFirst();

        if (existingBuildingOpt.isPresent()) {
            Building existingBuilding = existingBuildingOpt.get();
            BuildingLevel currentLevel = existingBuilding.getLevel();
            // Check if it's buildable (e.g. not a Dome) and has a next level
            if (currentLevel.isBuildable() && currentLevel.getNextLevel() != null) {
                existingBuilding.setLevel(currentLevel.getNextLevel()); // setLevel loads the new image
            }
        } else {
            // Create a new Building instance, representing a Level 1 block
            // The Building constructor (name, level, row, col) assigns the level field,
            // but setLevel() is needed to load the image.
            Building newBlock = new Building("Block", BuildingLevel.GROUND, targetRow, targetCol);
            newBlock.setLevel(BuildingLevel.LEVEL_ONE); // This sets to LEVEL_ONE and loads the image
            GamePanel.buildings.add(newBlock);
        }
    }
        
}
//public class BuildAction extends Action {
//
//    private List<Block> blocks;
//    private boolean hasDome;
//
//    @Override
//    public String execute(Player player, GamePanel gamePanel) {
//        return null;
//    }
//
//    @Override
//    public String hotkey() {
//        return null;
//    }
//
//    @Override
//    public Action getNextAction() {
//        return null;
//    }
//
//    public int getHeight() {
//        return blocks.get(0).getHeight();
//    }
//
//    public boolean canBuild() {
//        return blocks.stream().allMatch(block -> block.getHeight() < 4) && !hasDome;
//    }
//}
