package actions;

import actors.Worker;
import buildings.Building;
import buildings.BuildingLevel;
import java.util.ArrayList;
import java.util.Optional;

public class BuildAction extends Action {

    // Constructor to accept the list of buildings
    public BuildAction(ArrayList<Building> buildings) {
        super(buildings);
    }

    // Blocks can be built on empty tiles, tiles with a height of 3 or less, or adjacent to the worker's current position.
    public boolean canBuild(Worker worker, int targetRow, int targetCol, ArrayList<Worker> allWorkers, ArrayList<Building> allBuildings) {
        if (!isAdjacent(worker, targetRow, targetCol)) {
            return false;
        }

        if (isTileOccupiedByWorker(targetRow, targetCol, allWorkers)) {
            return false; // Cannot build on a tile occupied by another worker
        }

        // Check building status on the target tile
        Optional<Building> existingBuildingOpt = allBuildings.stream() 
                .filter(b -> b.getRow() == targetRow && b.getCol() == targetCol)
                .findFirst();

        if (existingBuildingOpt.isPresent()) {
            Building existingBuilding = existingBuildingOpt.get();
            return existingBuilding.getLevel().isBuildable(); // Can build if the existing building is not a Dome
        } else {
            return true; // Can build on empty tile
        }
    }
    
    public void build(int targetRow, int targetCol) {
        Optional<Building> existingBuildingOpt = this.gameBuildings.stream()
                .filter(b -> b.getRow() == targetRow && b.getCol() == targetCol)
                .findFirst();

        if (existingBuildingOpt.isPresent()) {
            Building existingBuilding = existingBuildingOpt.get();
            BuildingLevel currentLevel = existingBuilding.getLevel();
            if (currentLevel.isBuildable() && currentLevel.getNextLevel() != null) {
                existingBuilding.setLevel(currentLevel.getNextLevel());
            }
        } else {
            Building newBlock = new Building("Block", BuildingLevel.GROUND, targetRow, targetCol);
            newBlock.setLevel(BuildingLevel.LEVEL_ONE);
            this.gameBuildings.add(newBlock);
        }
    }
        
}