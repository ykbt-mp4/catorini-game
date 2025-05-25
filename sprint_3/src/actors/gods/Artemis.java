package actors.gods;

import actions.BuildAction;
import actions.MoveAction;
import main.GamePanel;
import tile.Tile;
import actors.Worker;

public class Artemis extends God {

    public Artemis() {
        super("Artemis",
                "Your Worker may move one additional time, but not back to its initial space.",
                "/godImages/artemis.png");
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
            System.out.println("Artemis: Power action available. Moving again (Can skip).");

            Tile[][] board = gp.getBoard();
            Tile currentTile = board[worker.getRow()][worker.getCol()];

            clearHighlights(board);

            for (int row = 0; row < gp.playTiles; row++) {
                for (int col = 0; col < gp.playTiles; col++) {
                    Tile target = board[row][col];

                    if (getValidMoveTiles(currentTile, target) && !isReturningToPreviousTile(worker, row, col)) {
                        target.setHighlighted(true);
                    }
                }
            }
        }

        @Override
        public boolean onTileClick(int row, int col) {
            Tile targetTile = getTile(row, col);
            Tile currentTile = getTile(worker.getRow(), worker.getCol());

            if (isReturningToPreviousTile(worker, row, col)) {
                System.out.println("Artemis: Cannot move back to the previous tile.");
                return false;
            }

            if (!isMoveActionLegal(currentTile, targetTile)) {
                return false;
            }

            moveWorker(currentTile, targetTile, row, col);
            return true;
        }

        private boolean isReturningToPreviousTile(Worker worker, int targetRow, int targetCol) {
            int lastRow = worker.getLastRow();
            int lastCol = worker.getLastCol();
            return targetRow == lastRow && targetCol == lastCol;
        }
    }
}