package actions;

import actors.Worker;
import buildings.Building;
import java.util.ArrayList;

public class MoveAction extends Action {
    private Worker worker;
    public boolean hasOccupant;

    public MoveAction(ArrayList<Building> buildings) {
        super(buildings);
        this.worker = null;
    }
    // Worker can move to adjacent tiles, if the tile is not occupied by another worker
    // Worker can move to a tile occupied by a building and tile, if the building is lower level than the building the worker is on
    // Worker cannot move to a building that is more than 1 level higher than the building the worker is on
    public boolean canMove(Worker worker, int targetRow, int targetCol, ArrayList<Worker> allWorkers, ArrayList<Building> allBuildings) {
        if (!isAdjacent(worker, targetRow, targetCol)) {
            return false; // Not adjacent
        }

        if (isTileOccupiedByWorker(targetRow, targetCol, allWorkers)) {
            return false; // Cannot move to a tile occupied by another worker
        }

        // Check building height conditions
        for (Building building : allBuildings) {
            if (building.getRow() == targetRow && building.getCol() == targetCol) {
                // Can move to a tile occupied by a building if the building is lower, same level, or 1 level higher
                return building.getLevel().getHeight() <= worker.getHeight() || 
                       building.getLevel().getHeight() == worker.getHeight() + 1;
            }
        }
        return true; // Can move to an empty tile
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public void clearSelection() {
        this.worker = null;
    }

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