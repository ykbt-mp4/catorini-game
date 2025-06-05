package actors.gods;

import actions.BuildAction;
import actions.MoveAction;
import main.GamePanel;
import main.Tile;
import actors.Worker;

/**
 * Represents the God Demeter, which allows a Worker to build twice,
 * but not on the same space.
 */
public class Demeter extends God {

    /**
     * Constructs a Demeter god card with its special actions.
     */
    public Demeter() {
        super("Demeter",
                "Your Worker may build one additional time, but not on the same space.",
                "/godImages/demeter.png");
        actions.add(new MoveAction());
        actions.add(new BuildAction());
        actions.add(new DemeterAction());
    }

    /**
     * Special build action for Demeter that allows building twice,
     * but not on the same tile twice in the same turn.
     */
    private static class DemeterAction extends BuildAction {
        /**
         * Constructs the Demeter special build action and marks it as a God action.
         */
        public DemeterAction() {
            setGodAction(true);
        }

        /**
         * Executes the Demeter special build action, highlighting valid tiles
         * excluding the one where the last build happened.
         * @param worker the worker performing the build
         * @param gamePanel the game panel context
         */
        @Override
        public void execute(Worker worker, GamePanel gamePanel) {
            super.execute(worker, gamePanel);
            System.out.println("Demeter: power action available. Build again (Can skip).");

            Tile[][] board = gp.getBoard();
            Tile currentTile = board[worker.getRow()][worker.getCol()];

            clearHighlights(board);

            for (int row = 0; row < gp.playTiles; row++) {
                for (int col = 0; col < gp.playTiles; col++) {
                    Tile target = board[row][col];

                    if (getValidBuildTiles(currentTile, target) && !isPreviousBuilding(worker, row, col)) {
                        target.setHighlighted(true);
                    }
                }
            }
        }

        /**
         * Handles a tile click event to place a building, rejecting attempts
         * to build on the same tile twice in a turn.
         * @param row the target row to build on
         * @param col the target column to build on
         * @return true if the build was successful; false otherwise
         */
        @Override
        public boolean onTileClick(int row, int col) {
            Tile targetTile = getTile(row, col);
            Tile currentTile = getTile(worker.getRow(), worker.getCol());

            if (isPreviousBuilding(worker, row, col)) {
                System.out.println("Demeter: Cannot build on the same tile twice in one turn.");
                return false;
            }

            if (!isBuildActionLegal(currentTile, targetTile)) {
                return false;
            }

            placeBuilding(targetTile, row, col);
            return true;
        }

        /**
         * Checks if the target build location is the same as the worker's last build location.
         * @param worker the worker performing the build
         * @param row the row to check
         * @param col the column to check
         * @return true if the position matches the last build location; false otherwise
         */
        private boolean isPreviousBuilding(Worker worker, int row, int col) {
            int lastBuildRow = worker.getLastBuildRow();
            int lastBuildCol = worker.getLastBuildCol();
            return row == lastBuildRow && col == lastBuildCol;
        }
    }
}
