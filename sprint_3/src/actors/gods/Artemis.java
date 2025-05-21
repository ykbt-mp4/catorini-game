package actors.gods;

import actions.Action;
import actions.BuildAction;
import actions.MoveAction;
import main.GamePanel;
import tile.Tile;
import actors.Worker;

public class Artemis extends God {

    public Artemis() {
        super("Artemis",
                "Your Worker may move one additional time, but not back to its initial space.");
        actions.add(new MoveAction());
        actions.add(new ArtemisAction());
        actions.add(new BuildAction());
    }

    private static class ArtemisAction extends Action {
        @Override
        public void execute(Worker worker, GamePanel gamePanel) {
            super.execute(worker, gamePanel);
            System.out.println("Executing artemis move action");
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

            int lastRow = worker.getLastRow();
            int lastCol = worker.getLastCol();
            boolean isBackToPrevious = (row == lastRow && col == lastCol);

            if (!currentTile.isAdjacentTo(row, col)) {
                System.out.println("Can only move to adjacent tiles.");
                return false;
            }

            if (!targetTile.isEmpty()) {
                System.out.println("Target tile occupied.");
                return false;
            }

            if (isBackToPrevious) {
                System.out.println("Cannot move back to the previous tile.");
                return false;
            }

            int currentLevel = currentTile.getLevel();
            int targetLevel = targetTile.getLevel();

            if (targetLevel - currentLevel > 1) {
                System.out.println("Cannot move up more than 1 level.");
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
}