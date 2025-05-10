package actions;

import actors.Worker;
import buildings.Building;
import java.util.ArrayList;

/**
 * An abstract base class for game actions that provides common functionality for move and build operations.
 * Maintains references to the game state, including buildings and workers.
 */
public abstract class Action {

    protected ArrayList<Building> gameBuildings;
    protected ArrayList<Worker> gameWorkers;

    /**
     * Constructs an Action that references the game state.
     *
     * @param buildings The list of all buildings in the game currently
     * @param workers The list of all workers in the game, with positions
     */
    public Action(ArrayList<Building> buildings, ArrayList<Worker> workers) {
        this.gameBuildings = buildings;
        this.gameWorkers = workers;
    }

    /**
     * Checks to see if a target position is adjacent to the selected worker's current position
     * Adjacent positions include the surrounding 8 tiles (vertical, horizontal, or diagonal)
     *
     * @param worker The worker whose position to check from
     * @param targetRow The row index of the chosen tile position
     * @param targetCol The column index of the chosen tile position
     * @return true if chosen tile position is adjacent to worker, false otherwise
     */
    protected static boolean isAdjacent(Worker worker, int targetRow, int targetCol) {
        int rowDiff = Math.abs(worker.getRow() - targetRow);
        int colDiff = Math.abs(worker.getCol() - targetCol);

        // Check for adjacency (vertical, horizontal, or diagonal)
        return (rowDiff == 1 && colDiff == 0) ||  // vertical
               (rowDiff == 0 && colDiff == 1) ||  // horizontal
               (rowDiff == 1 && colDiff == 1);    // diagonal
    }

    /**
     * Checks if a tile is occupied by a worker
     *
     * @param targetRow The row index of the chosen grid position
     * @param targetCol The column index of the chosen grid position
     * @return true if a worker occupies the specific tile, false otherwise
     */
    protected boolean isTileOccupiedByWorker(int targetRow, int targetCol) {
        for (Worker w : this.gameWorkers) {
            if (w.getRow() == targetRow && w.getCol() == targetCol) {
                return true;
            }
        }
        return false;
    }
}