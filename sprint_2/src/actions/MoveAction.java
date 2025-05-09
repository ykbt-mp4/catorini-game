package actions;

import main.GamePanel;
import actors.Worker;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import buildings.Building;

public class MoveAction{
    private Worker worker;
    public boolean hasOccupant;

    public MoveAction() {
        worker = null;
    }
    // Worker can move to adjacent tiles, if the tile is not occupied by another worker
    // Worker can move to a tile occupied by a building and tile, if the building is lower level than the building the worker is on
    // Worker cannot move to a building that is more than 1 level higher than the building the worker is on
    public boolean canMove(Worker worker, int row, int col, ArrayList<Worker> workers, ArrayList<Building> buildings) {
        int rowDiff = Math.abs(worker.getRow() - row);
        int colDiff = Math.abs(worker.getCol() - col);

        if ((rowDiff == 1 && colDiff == 0) ||  // vertical move
                (rowDiff == 0 && colDiff == 1) ||  // horizontal move
                (rowDiff == 1 && colDiff == 1)) {
            for (Worker w : workers) {
                if (w.getRow() == row && w.getCol() == col) {
                    return false;
                }
            }
            for (Building building : buildings) {
                if (building.getRow() == row && building.getCol() == col) {
                    // Can move to a tile occupied by a building and tile, if the building is lower level than the building the worker is on
                    return building.getLevel().getHeight() < worker.getHeight() || 
                            building.getLevel().getHeight() == worker.getHeight() + 1;
                }
            }
            return true;
        }
        return false;
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
        }
        // Set height if target tile is occupied by a building
        for (Building building : GamePanel.buildings) {
            if (building.getRow() == targetRow && building.getCol() == targetCol) {
                worker.setHeight(building.getLevel().getHeight());
            }
        }
    }
}
