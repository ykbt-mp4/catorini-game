package actors.gods;

import actions.Action;
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

    private static class DemeterAction extends Action {
        public DemeterAction() {
            setGodAction(true);
        }

        @Override
        public void execute(Worker worker, GamePanel gamePanel) {
            super.execute(worker, gamePanel);
            System.out.println("Executing demeter build action");
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

            if (!currentTile.isAdjacentTo(row, col)) {
                System.out.println("Can only build on adjacent tiles.");
                return false;
            }

            if (!targetTile.isEmpty()) {
                System.out.println("Cannot build here: either occupied or has dome.");
                return false;
            }

            int lastBuildRow = worker.getLastBuildRow();
            int lastBuildCol = worker.getLastBuildCol();
            boolean previousBuilding = (row == lastBuildRow && col == lastBuildCol);

            if (previousBuilding) {
                System.out.println("Cannot build on the same tile twice in one turn.");
                return false;
            }

            targetTile.build();
            worker.setLastBuildPosition(row, col);
            System.out.println("Built on tile " + row + "," + col + " | Level now: " + targetTile.getLevel() + (targetTile.hasDome() ? " with Dome" : ""));
            gp.repaint();
            return true;
        }
    }
}
