package actors.gods;

import actions.BuildAction;
import actions.MoveAction;
import main.GamePanel;
import tile.Tile;
import actors.Worker;

public class Demeter extends God {

    public Demeter() {
        super("Demeter",
                "Your Worker may build one additional time, but not on the same space.");
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
            System.out.println("Executing demeter build action");

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

            int lastBuildRow = worker.getLastBuildRow();
            int lastBuildCol = worker.getLastBuildCol();
            boolean previousBuilding = (row == lastBuildRow && col == lastBuildCol);

            if (previousBuilding) {
                System.out.println("Cannot build on the same tile twice in one turn.");
                return false;
            }

            if (!isActionLegal(currentTile, targetTile)) {
                return false;
            }

            placeBuilding(targetTile, row, col);
            return true;
        }
    }
}
