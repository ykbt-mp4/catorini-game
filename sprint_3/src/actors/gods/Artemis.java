package actors.gods;

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

    private static class ArtemisAction extends MoveAction {
        public ArtemisAction() {
            setGodAction(true);
        }

        @Override
        public void execute(Worker worker, GamePanel gamePanel) {
            super.execute(worker, gamePanel);
            System.out.println("Executing artemis move action");
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

        @Override
        public boolean onTileClick(int row, int col) {
            if (isNotValidTile(row, col)) {
                return false;
            }

            Tile targetTile = getTile(row, col);
            Tile currentTile = getTile(worker.getRow(), worker.getCol());

            int lastRow = worker.getLastRow();
            int lastCol = worker.getLastCol();
            if (row == lastRow && col == lastCol) {
                System.out.println("Cannot move back to the previous tile.");
                return false;
            }

            if (!isActionLegal(currentTile, targetTile)) {
                return false;
            }

            moveWorker(currentTile, targetTile, row, col);
            return true;
        }
    }
}