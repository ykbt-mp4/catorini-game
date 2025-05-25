package actors.gods;

import actions.BuildAction;
import actions.MoveAction;
import main.GamePanel;
import tile.Tile;
import actors.Worker;

public class Demeter extends God {

    public Demeter() {
        super("Demeter",
                "Your Worker may build one additional time, but not on the same space.",
                "/godImages/demeter.png");
        actions.add(new MoveAction());
        actions.add(new BuildAction());
        actions.add(new DemeterAction());
    }

    private static class DemeterAction extends BuildAction {
        public DemeterAction() {
            setGodAction(true);
        }

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

        private boolean isPreviousBuilding(Worker worker, int row, int col) {
            int lastBuildRow = worker.getLastBuildRow();
            int lastBuildCol = worker.getLastBuildCol();
            return row == lastBuildRow && col == lastBuildCol;
        }
    }
}
