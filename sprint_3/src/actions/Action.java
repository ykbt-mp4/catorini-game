package actions;

import actors.Worker;
import main.GamePanel;
import tile.Tile;

public abstract class Action {

    protected Worker worker;
    protected GamePanel gp;
    protected boolean isGodAction = false; // default is false

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

    protected boolean isNotValidTile(int row, int col) {
        return (row < 0 || row >= gp.playTiles || col < 0 || col >= gp.playTiles);
    }

    protected boolean isActionLegal(Tile currentTile, Tile targetTile) {
        if (!currentTile.isAdjacentTo(targetTile.getRow(), targetTile.getCol())) {
            System.out.println("Can only move to adjacent tiles.");
            return false;
        }

        if (!targetTile.isEmpty()) {
            System.out.println("Target tile occupied.");
            return false;
        }

        int levelDiff = targetTile.getLevel() - currentTile.getLevel();
        if (levelDiff > 1) {
            System.out.println("Cannot move up more than 1 level.");
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
