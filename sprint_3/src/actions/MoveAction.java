package actions;

import actors.Worker;
import main.GamePanel;
import tile.Tile;

public class MoveAction extends Action {


    public void execute(Worker worker, GamePanel gamePanel) {
        super.execute(worker, gamePanel);
    }

    @Override
    public boolean onTileClick(int row, int col) {
        Tile[][] board = gp.getBoard();

        if (row < 0 || row >= gp.playTiles || col < 0 || col >= gp.playTiles) {
            System.out.println("Invalid tile clicked.");
            return false;
        }

        Tile targetTile = board[row][col];
        Tile currentTile = board[worker.getRow()][worker.getCol()];

        if (!currentTile.isAdjacentTo(row, col)) {
            System.out.println("Can only move to adjacent tiles.");
            return false;
        }

        if (!targetTile.isEmpty()) {
            System.out.println("Target tile occupied.");
            return false;
        }

        // Move worker to the new tile
        int currentRow = worker.getRow();
        int currentCol = worker.getCol();

        currentTile.clearWorker();

        worker.setLastRow(currentRow);
        worker.setLastCol(currentCol);
        worker.setPosition(row, col);

        targetTile.setWorker(worker);

        System.out.println("Moved worker to " + row + "," + col);
        gp.repaint();

        return true;
    }
}