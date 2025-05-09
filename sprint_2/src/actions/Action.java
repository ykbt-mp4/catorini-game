package actions;

import actors.Worker;
import buildings.Building;
import java.util.ArrayList;

public abstract class Action {

    protected ArrayList<Building> gameBuildings;

    public Action(ArrayList<Building> buildings) {
        this.gameBuildings = buildings;
    }

    protected static boolean isAdjacent(Worker worker, int targetRow, int targetCol) {
        int rowDiff = Math.abs(worker.getRow() - targetRow);
        int colDiff = Math.abs(worker.getCol() - targetCol);

        // Check for adjacency (vertical, horizontal, or diagonal)
        return (rowDiff == 1 && colDiff == 0) ||  // vertical
               (rowDiff == 0 && colDiff == 1) ||  // horizontal
               (rowDiff == 1 && colDiff == 1);    // diagonal
    }

    protected static boolean isTileOccupiedByWorker(int targetRow, int targetCol, ArrayList<Worker> allWorkers) {
        for (Worker w : allWorkers) {
            if (w.getRow() == targetRow && w.getCol() == targetCol) {
                return true;
            }
        }
        return false;
    }
}