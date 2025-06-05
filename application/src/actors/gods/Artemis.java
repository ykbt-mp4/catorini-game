package actors.gods;

import actions.BuildAction;
import actions.MoveAction;
import main.GamePanel;
import main.Tile;
import actors.Worker;

/**
 * Represents the God Artemis, who grants the ability for a worker
 * to move an additional time but not back to its initial space.
 */
public class Artemis extends God {

    /**
     * Constructs an Artemis god with predefined actions:
     * a regular move, the Artemis special move, and build action.
     */
    public Artemis() {
        super("Artemis",
                "Your Worker may move one additional time, but not back to its initial space.",
                "/godImages/artemis.png");
        actions.add(new MoveAction());
        actions.add(new ArtemisAction());
        actions.add(new BuildAction());
    }

    /**
     * ArtemisAction is a specialized MoveAction that allows
     * moving again but prevents moving back to the initial tile.
     */
    private static class ArtemisAction extends MoveAction {
        /**
         * Constructs the Artemis special move action.
         * Marks it as a God action.
         */
        public ArtemisAction() {
            setGodAction(true);
        }

        /**
         * Executes the Artemis special move, highlighting valid tiles,
         * excluding the previous position.
         * @param worker the worker performing the move
         * @param gamePanel the game panel context
         */
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

        /**
         * Handles the tile click event. Prevents moving back to the previous tile.
         * @param row the target row
         * @param col the target column
         * @return true if a move is successful; false otherwise
         */
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


        /**
         * Checks if the target tile is the worker's previous tile.
         * @param worker the worker moving
         * @param targetRow target row
         * @param targetCol target column
         * @return true if target tile equals last tile; false otherwise
         */
        private boolean isReturningToPreviousTile(Worker worker, int targetRow, int targetCol) {
            int lastRow = worker.getLastRow();
            int lastCol = worker.getLastCol();
            return targetRow == lastRow && targetCol == lastCol;
        }
    }
}