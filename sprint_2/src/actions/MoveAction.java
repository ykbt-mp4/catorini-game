package actions;

import main.GamePanel;
import actors.Worker;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MoveAction{
    private Worker worker;
    public boolean hasOccupant;

    public MoveAction() {
        worker = null;
    }

    public boolean canMove(Worker worker, int row, int col, ArrayList<Worker> workers) {
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
            return true;
        }
        return false;
    }
}
