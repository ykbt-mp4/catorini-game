package actions;

import actors.Worker;
import main.GamePanel;
import tile.Tile;

public class BuildAction extends Action {

    @Override
    public void execute(Worker worker, GamePanel gamePanel) {
        super.execute(worker, gamePanel);
        System.out.println("Executing build");

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
            System.out.println("Can only build on adjacent tiles.");
            return false;
        }

        if (!targetTile.isEmpty()) {
            System.out.println("Cannot build here: either occupied or has dome.");
            return false;
        }

        targetTile.build();
        worker.setLastBuildPosition(row, col);
        System.out.println("Built on tile " + row + "," + col + " | Level now: " + targetTile.getLevel() + (targetTile.hasDome() ? " with Dome" : ""));
        gp.repaint();
        return true;
    }
}


