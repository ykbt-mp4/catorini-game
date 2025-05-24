package actions;

import actors.Worker;
import main.GamePanel;
import tile.Tile;

public class BuildAction extends Action {

    @Override
    public void execute(Worker worker, GamePanel gamePanel) {
        super.execute(worker, gamePanel);
        System.out.println("Executing build");

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

        placeBuilding(targetTile, row, col);
        return true;
    }

    protected void placeBuilding(Tile targetTile, int row, int col){
        targetTile.build();
        worker.setLastBuildPosition(row, col);
        System.out.println("Built on tile " + row + "," + col + " | Level now: " + targetTile.getLevel() + (targetTile.hasDome() ? " with Dome" : ""));
        gp.repaint();
    }
}


