package actions;

import actors.Worker;
import buildings.Building;
import java.util.ArrayList;

/**
 * Handles movement actions in the game, allowing workers to move to adjacent tiles according to movement rules.
 */
public class MoveAction extends Action {
    private Worker worker;
    public boolean hasOccupant;

    /**
     * Constructs a MoveAction that references the game state.
     *
     * @param buildings The list of all buildings in the game currently
     * @param workers The list of all workers in the game
     */
    public MoveAction(ArrayList<Building> buildings, ArrayList<Worker> workers) {
        super(buildings, workers);
        this.worker = null;
    }

    /**
     * Determines if a worker can move to a chosen tile position.
     * A worker can move onto a tile if:
     * - It is adjacent to the specified worker
     * - Not occupied by another worker
     * - Tile is empty
     * - If the level of the tile the worker is on has a difference of 1 to the chosen tile position
     *
     * @param worker The selected worker that will move
     * @param targetRow The row index of the chosen tile position
     * @param targetCol The column index of the chosen tile position
     * @return true if a worker is able to move to the chose tile position, false otherwise
     */
    public boolean canMove(Worker worker, int targetRow, int targetCol) { 
        if (!isAdjacent(worker, targetRow, targetCol)) {
            return false; // Not adjacent
        }

        if (isTileOccupiedByWorker(targetRow, targetCol)) { 
            return false; // Cannot move to a tile occupied by another worker
        }

        // Check building height conditions
        for (Building building : this.gameBuildings) { 
            if (building.getRow() == targetRow && building.getCol() == targetCol) {
                // Can move to a tile occupied by a building if the building is lower, same level, or 1 level higher
                return building.getLevel().getHeight() <= worker.getHeight() || 
                       building.getLevel().getHeight() == worker.getHeight() + 1;
            }
        }
        return true; // Can move to an empty tile
    }

    /**
     * Gets the currently selected worker for movement.
     *
     * @return The currently selected worker object, or null if no worker has been selected
     */
    public Worker getWorker() {
        return worker;
    }

    /**
     * Sets the worker that will be moved.
     *
     * @param worker The worker to select for movement
     */
    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    /**
     * Clears the currently selected worker.
     */
    public void clearSelection() {
        this.worker = null;
    }

    /**
     * Moves the selected worker to the target position and updates their height.
     * If the target tile position has a building, set the worker's height to the building's level.
     * If the target tile position is empty, set the worker's height to 0
     *
     * @param targetRow The row index of the chosen tile position
     * @param targetCol The column index of the chosen tile position
     */
    public void moveWorker(int targetRow, int targetCol) {
        if (worker != null) {
            worker.setRow(targetRow);
            worker.setCol(targetCol);

            // Assume worker moves to ground level if no building is present
            boolean onBuilding = false;
            for (Building building : this.gameBuildings) {
                if (building.getRow() == targetRow && building.getCol() == targetCol) {
                    worker.setHeight(building.getLevel().getHeight());
                    onBuilding = true;
                    break;
                }
            }
            if (!onBuilding) {
                worker.setHeight(0); // Set to ground height if no building
            }
        }
    }
}