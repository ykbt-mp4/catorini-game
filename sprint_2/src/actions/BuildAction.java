package actions;

import actors.Worker;
import buildings.Building;
import buildings.BuildingLevel;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Handles building actions in the game, allowing workers to construct building on adjacent tiles according to game rules.
 */
public class BuildAction extends Action {

    /**
     * Constructs a BuildAction that references the game state.
     *
     * @param buildings The list of all buildings in the game currently
     * @param workers The list of all workers in the game
     */
    public BuildAction(ArrayList<Building> buildings, ArrayList<Worker> workers) {
        super(buildings, workers);
    }

    /**
     * Determines if a worker can build on a chosen tile.
     * A building can be built on a tile if:
     * - It is adjacent to the specified worker
     * - Not occupied by another worker
     * - Tile is empty
     * - Contains a tile with a height of 3 or less
     *
     * @param worker The selected worker that will build
     * @param targetRow The row index of the chosen tile position
     * @param targetCol The column index of the chosen tile position
     * @return true if a building is able to be built on the chosen tile position, false otherwise
     */
    public boolean canBuild(Worker worker, int targetRow, int targetCol) { 
        if (!isAdjacent(worker, targetRow, targetCol)) {
            return false;
        }

        // // Check if the target tile is occupied by another worker
        // if (isTileOccupiedByWorker(targetRow, targetCol)) { 
        //     return false;
        // }
        for (Worker w : this.gameWorkers) {
            if (w.getRow() == targetRow && w.getCol() == targetCol) {
                // Target tile is occupied by a worker
                return false; // Cannot build
            }
        }

        // Check building status on the target tile using this.gameBuildings
        Optional<Building> existingBuildingOpt = this.gameBuildings.stream()  
                .filter(b -> b.getRow() == targetRow && b.getCol() == targetCol)
                .findFirst();

        if (existingBuildingOpt.isPresent()) {
            Building existingBuilding = existingBuildingOpt.get();
            return existingBuilding.getLevel().isBuildable(); // Can build if the existing building is not a Dome
        } else {
            return true; // Can build on empty tile
        }
    }

    /**
     * Executes the build action on a chosen tile position
     *
     * @param targetRow The row index of the chosen tile position
     * @param targetCol The column index of the chosen tile position
     */
    
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