package actions;

import actors.Worker;
import buildings.Building;
import java.util.ArrayList;

public abstract class Action {

    protected ArrayList<Building> gameBuildings;
    protected ArrayList<Worker> gameWorkers;

    public Action(ArrayList<Building> buildings, ArrayList<Worker> workers) {
        this.gameBuildings = buildings;
        this.gameWorkers = workers;
    }

    protected static boolean isAdjacent(Worker worker, int targetRow, int targetCol) {
        int rowDiff = Math.abs(worker.getRow() - targetRow);
        int colDiff = Math.abs(worker.getCol() - targetCol);

        // Check for adjacency (vertical, horizontal, or diagonal)
        return (rowDiff == 1 && colDiff == 0) ||  // vertical
               (rowDiff == 0 && colDiff == 1) ||  // horizontal
               (rowDiff == 1 && colDiff == 1);    // diagonal
    }

    protected boolean isTileOccupiedByWorker(int targetRow, int targetCol) {
        for (Worker w : this.gameWorkers) {
            if (w.getRow() == targetRow && w.getCol() == targetCol) {
                return true;
            }
        }
        return false;
    }
}