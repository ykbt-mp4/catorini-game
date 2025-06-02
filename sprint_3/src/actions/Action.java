package actions;

import actors.Worker;
import main.GamePanel;
import main.Tile;

/**
 * Abstract base class representing a generic game action.
 * Provides shared functionality for all move and build actions.
 */
public abstract class Action {

    protected Worker worker;
    protected GamePanel gp;
    protected boolean isGodAction = false;

    /**
     * Initialises the action with a worker and the game panel.
     * @param worker the worker performing the action
     * @param gamePanel the current game panel
     */
    public void execute(Worker worker, GamePanel gamePanel) {
        this.worker = worker;
        this.gp = gamePanel;
    }

    /**
     * Sets whether this action is a god power action.
     * @param godAction true if it is a god action, false otherwise
     */
    public void setGodAction(boolean godAction) {
        this.isGodAction = godAction;
    }

    /**
     * Checks if this is a god power action.
     * @return true if it is a god action, false otherwise
     */
    public boolean isGodAction() {
        return isGodAction;
    }

    /**
     * Retrieves the tile at the given board coordinates.
     * @param row the row of the tile
     * @param col the column of the tile
     * @return the tile at the specified location
     */
    protected Tile getTile(int row, int col) {
        return gp.getBoard()[row][col];
    }

    /**
     * Validates a move from the current tile to a target tile.
     *
     * @param currentTile the tile the worker is currently on
     * @param targetTile the tile to move to
     * @return true if the move is valid, false otherwise
     */
    public boolean getValidMoveTiles(Tile currentTile, Tile targetTile){
        if (!currentTile.isAdjacentTo(targetTile.getRow(), targetTile.getCol())) return false;
        if (targetTile.getLevel() - currentTile.getLevel() > 1) return false;
        if (targetTile.isOccupiedByWorker()) return false;
        if (targetTile.hasDome()) return false;
        return true;
    }


    /**
     * Validates whether a build action is allowed on the target tile.
     * @param currentTile the tile the worker is currently on
     * @param targetTile the tile to build on
     * @return true if the build is valid, false otherwise
     */
    protected boolean getValidBuildTiles(Tile currentTile, Tile targetTile){
        if (!currentTile.isAdjacentTo(targetTile.getRow(), targetTile.getCol())) return false;
        if (targetTile.isOccupiedByWorker()) return false;
        if (targetTile.hasDome()) return false;
        return true;
    }

    /**
     * Checks whether a move action is legal and logs a message if it is not.
     * @param currentTile the current tile
     * @param targetTile the target tile
     * @return true if the move is legal, false otherwise
     */
    protected boolean isMoveActionLegal(Tile currentTile, Tile targetTile) {
        if (!currentTile.isAdjacentTo(targetTile.getRow(), targetTile.getCol())) {
            System.out.println("Can only move to adjacent tiles.");
            return false;
        }

        int levelDiff = targetTile.getLevel() - currentTile.getLevel();
        if (levelDiff > 1) {
            System.out.println("Cannot move up more than 1 level.");
            return false;
        }

        if (targetTile.isOccupiedByWorker()) {
            System.out.println("Target tile is occupied by worker.");
            return false;
        }

        if (targetTile.hasDome()) {
            System.out.println("Target tile has a dome.");
            return false;
        }
        return true;
    }

    /**
     * Checks whether a build action is legal and logs a message if it is not.
     * @param currentTile the current tile
     * @param targetTile the target tile
     * @return true if the build is legal, false otherwise
     */
    protected boolean isBuildActionLegal(Tile currentTile, Tile targetTile) {
        if (!currentTile.isAdjacentTo(targetTile.getRow(), targetTile.getCol())) {
            System.out.println("Can only build on adjacent tiles.");
            return false;
        }

        if (targetTile.isOccupiedByWorker()) {
            System.out.println("Target tile is occupied by worker.");
            return false;
        }

        if (targetTile.hasDome()) {
            System.out.println("Target tile has a dome.");
            return false;
        }
        return true;
    }

    /**
     * Clears highlighting on all tiles in the game board for UI purposes.
     * @param board the game board
     */
    protected void clearHighlights(Tile[][] board) {
        for (Tile[] tiles : board) {
            for (Tile tile : tiles) {
                tile.setHighlighted(false);
            }
        }
    }

    /**
     * Executes an action when a tile is clicked. Must be implemented by subclasses.
     * @param row the row of the clicked tile
     * @param col the column of the clicked tile
     * @return true if the action was successful, false otherwise
     */
    public abstract boolean onTileClick(int row, int col);
}
