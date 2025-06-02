package actions;

import actors.Worker;
import main.GamePanel;
import main.Tile;

/**
 * Represents the build action in the game.
 * This action allows a worker to construct a building on a valid adjacent tile.
 */
public class BuildAction extends Action {

    /**
     * Executes the build action logic.
     * Highlights all valid build tiles around the worker.
     * @param worker the worker performing the action
     * @param gamePanel the current game panel
     */
    @Override
    public void execute(Worker worker, GamePanel gamePanel) {
        super.execute(worker, gamePanel);
        System.out.println("Build action. Executing...");

        Tile[][] board = gp.getBoard();
        Tile currentTile = board[worker.getRow()][worker.getCol()];

        clearHighlights(board);

        // UI - highlight valid build tiles around the worker
        for (int row = 0; row < gp.playTiles; row++) {
            for (int col = 0; col < gp.playTiles; col++) {
                Tile target = board[row][col];

                if (getValidBuildTiles(currentTile, target)) {
                    target.setHighlighted(true);
                }
            }
        }
    }

    /**
     * Handles a tile click during a build phase.
     * Validates the action and performs the build if legal.
     * @param row the row of the clicked tile
     * @param col the column of the clicked tile
     * @return true if the build is performed, false otherwise
     */
    public boolean onTileClick(int row, int col) {
        Tile targetTile = getTile(row, col);
        Tile currentTile = getTile(worker.getRow(), worker.getCol());

        if (!isBuildActionLegal(currentTile, targetTile)) {
            return false;
        }

        placeBuilding(targetTile, row, col);
        return true;
    }

    /**
     * Builds on the specified tile and updates worker state.
     * @param targetTile the tile where the building is placed
     * @param row the row of the tile
     * @param col the column of the tile
     */
    protected void placeBuilding(Tile targetTile, int row, int col){
        targetTile.build();
        worker.setLastBuildPosition(row, col);
        System.out.println("Built on tile " + row + "," + col + ". Level now: " + targetTile.getLevel() + (targetTile.hasDome() ? " with Dome" : " without Dome \n"));
        gp.repaint();
    }
}
