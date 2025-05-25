package actions;

import actors.Worker;
import main.GamePanel;
import tile.Tile;

public abstract class Action {

    protected Worker worker;
    protected GamePanel gp;
    protected boolean isGodAction = false;

    public void execute(Worker worker, GamePanel gamePanel) {
        this.worker = worker;
        this.gp = gamePanel;
    }

    public void setGodAction(boolean godAction) {
        this.isGodAction = godAction;
    }

    public boolean isGodAction() {
        return isGodAction;
    }

    protected Tile getTile(int row, int col) {
        return gp.getBoard()[row][col];
    }

    protected boolean getValidMoveTiles(Tile currentTile, Tile targetTile){
            if (!currentTile.isAdjacentTo(targetTile.getRow(), targetTile.getCol())) return false;
            if (targetTile.getLevel() - currentTile.getLevel() > 1) return false;
            if (targetTile.isOccupiedByWorker()) return false;
            if (targetTile.hasDome()) return false;
            return true;
    }

    protected boolean getValidBuildTiles(Tile currentTile, Tile targetTile){
        if (!currentTile.isAdjacentTo(targetTile.getRow(), targetTile.getCol())) return false;
        if (targetTile.isOccupiedByWorker()) return false;
        if (targetTile.hasDome()) return false;
        return true;
    }

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

    protected void clearHighlights(Tile[][] board) {
        for (Tile[] tiles : board) {
            for (Tile tile : tiles) {
                tile.setHighlighted(false);
            }
        }
    }

    public abstract boolean onTileClick(int row, int col);
}
