package actions;

import actors.Worker;
import main.GamePanel;
import tile.Tile;

public class MoveAction extends Action {

    @Override
    public void execute(Worker worker, GamePanel gamePanel) {
        super.execute(worker, gamePanel);

        Tile[][] board = gp.getBoard();
        Tile currentTile = board[worker.getRow()][worker.getCol()];

        clearHighlights(board);

        for (int row = 0; row < gp.playTiles; row++) {
            for (int col = 0; col < gp.playTiles; col++) {
                Tile target = board[row][col];
                if (currentTile.isAdjacentTo(row, col) && isActionLegal(currentTile, target)) {
                    target.setHighlighted(true);
                }
            }
        }
    }

    public boolean onTileClick(int row, int col) {
        if (isNotValidTile(row, col)) {
            return false;
        }

        Tile targetTile = getTile(row, col);
        Tile currentTile = getTile(worker.getRow(), worker.getCol());

        if (!isActionLegal(currentTile, targetTile)) {
            return false;
        }

        moveWorker(currentTile, targetTile, row, col);
        return true;
    }

    protected void moveWorker(Tile currentTile, Tile targetTile, int row, int col) {
        int currentRow = worker.getRow();
        int currentCol = worker.getCol();

        currentTile.clearWorker();
        worker.setLastRow(currentRow);
        worker.setLastCol(currentCol);
        worker.setPosition(row, col);
        targetTile.setWorker(worker);

        System.out.println("Moved worker to " + row + "," + col);
        gp.repaint();
    }
}
