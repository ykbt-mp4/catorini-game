package actions;

import actors.Worker;
import main.GamePanel;
import main.Tile;

/**
 * Represents the move action in the game.
 * This action allows a worker to move to a valid adjacent tile.
 */
public class MoveAction extends Action {

    /**
     * Executes the move action logic.
     * Highlights all valid move tiles around the worker.
     * @param worker the worker performing the action
     * @param gamePanel the current game panel
     */
    @Override
    public void execute(Worker worker, GamePanel gamePanel) {
        super.execute(worker, gamePanel);
        System.out.println("Move action. Executing...");

        Tile[][] board = gp.getBoard();
        Tile currentTile = board[worker.getRow()][worker.getCol()];

        clearHighlights(board);

        // UI - highlight valid build tiles around the worker
        for (int row = 0; row < gp.playTiles; row++) {
            for (int col = 0; col < gp.playTiles; col++) {
                Tile target = board[row][col];

                if (getValidMoveTiles(currentTile, target)) {
                    target.setHighlighted(true);
                }
            }
        }
    }

    /**
     * Handles a tile click during a move phase.
     * Validates the action and performs the move if legal.
     * @param row the row of the clicked tile
     * @param col the column of the clicked tile
     * @return true if the move is performed, false otherwise
     */
    public boolean onTileClick(int row, int col) {

        Tile targetTile = getTile(row, col);
        Tile currentTile = getTile(worker.getRow(), worker.getCol());

        if (!isMoveActionLegal(currentTile, targetTile)) {
            return false;
        }

        moveWorker(currentTile, targetTile, row, col);
        return true;
    }

    /**
     * Moves the worker to the target tile.
     * Updates the worker's position and clears the old tile.
     * @param currentTile the tile the worker is moving from
     * @param targetTile the tile the worker is moving to
     * @param row the target row
     * @param col the target column
     */
    protected void moveWorker(Tile currentTile, Tile targetTile, int row, int col) {
        int currentRow = worker.getRow();
        int currentCol = worker.getCol();

        currentTile.clearWorker();
        worker.setLastRow(currentRow);
        worker.setLastCol(currentCol);
        worker.setPosition(row, col);
        targetTile.setWorker(worker);

        System.out.println("Moved worker to " + row + "," + col + "\n");
        gp.repaint();
    }
}