package actors.gods;

import actions.BuildAction;
import actions.MoveAction;
import main.Tile;
import actors.Worker;
import main.GamePanel;

/**
 * Represents the God Triton, whose Worker may move again
 * immediately after moving into a perimeter space.
 */
public class Triton extends God {

    /**
     * Constructs the Triton god card with its special actions.
     */
    public Triton() {
        super("Triton",
                "Each time your Worker moves into a perimeter space, it may immediately move again.",
                "/godImages/triton.png");
        actions.add(new MoveAction());
        actions.add(new TritonAction());
        actions.add(new BuildAction());
    }


    /**
     * Special move action for Triton allowing an extra move
     * if the worker lands on a perimeter tile.
     */
    private static class TritonAction extends MoveAction {

        /**
         * Constructs the Triton special move action and marks it as a God action.
         */
        public TritonAction() {
            setGodAction(true);
        }

        /**
         * Executes the Triton special move action, highlighting valid tiles.
         * If the worker is not on a perimeter tile, skips the power action.
         * @param worker the worker performing the move
         * @param gamePanel the game panel context
         */
        @Override
        public void execute(Worker worker, GamePanel gamePanel) {
            super.execute(worker, gamePanel);

            Tile[][] board = gp.getBoard();
            Tile currentTile = board[worker.getRow()][worker.getCol()];

            clearHighlights(board);

            for (int row = 0; row < gp.playTiles; row++) {
                for (int col = 0; col < gp.playTiles; col++) {
                    Tile target = board[row][col];
                    if (getValidMoveTiles(currentTile, target)) {
                        target.setHighlighted(true);
                    }
                }
            }

            if (!isOnPerimeter(worker)) {
                System.out.println("Triton: Power action not available.");
                gp.turnManager.skipCurrentAction();
            } else {
                System.out.println("Triton: Power action available. Move again (Can skip).");
            }
        }

        /**
         * Handles a tile click event to move the worker.
         * If the worker is on perimeter, the action index is adjusted to allow an immediate move again.
         * @param row the target row
         * @param col the target column
         * @return true if the move is successful; false otherwise
         */
        @Override
        public boolean onTileClick(int row, int col) {
            Tile targetTile = getTile(row, col);
            Tile currentTile = getTile(worker.getRow(), worker.getCol());

            if (!isMoveActionLegal(currentTile, targetTile)) {
                return false;
            }

            if (isOnPerimeter(worker)) {
                moveWorker(currentTile, targetTile, row, col);
                gp.turnManager.currentActionIndex -= 1;
            }
            return true;
        }

        /**
         * Checks if the worker is currently on a perimeter tile.
         * @param worker the worker to check
         * @return true if on perimeter; false otherwise
         */
        private boolean isOnPerimeter(Worker worker) {
            int row = worker.getRow();
            int col = worker.getCol();
            return row == 0 || row == gp.playTiles - 1 || col == 0 || col == gp.playTiles - 1;
        }
    }
}
