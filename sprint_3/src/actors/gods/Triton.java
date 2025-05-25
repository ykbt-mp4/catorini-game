package actors.gods;

import actions.BuildAction;
import actions.MoveAction;
import tile.Tile;
import actors.Worker;
import main.GamePanel;

public class Triton extends God {

    public Triton() {
        super("Triton",
                "Each time your Worker moves into a perimeter space, it may immediately move again.",
                "/godImages/triton.png");
        actions.add(new MoveAction());
        actions.add(new TritonAction());
        actions.add(new BuildAction());
    }

    private static class TritonAction extends MoveAction {

        public TritonAction() {
            setGodAction(true);
        }

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
                gp.skipCurrentAction();
            } else {
                System.out.println("Triton: Power action available. Move again (Can skip).");
            }
        }

        @Override
        public boolean onTileClick(int row, int col) {
            Tile targetTile = getTile(row, col);
            Tile currentTile = getTile(worker.getRow(), worker.getCol());

            if (!isMoveActionLegal(currentTile, targetTile)) {
                return false;
            }

            if (isOnPerimeter(worker)) {
                moveWorker(currentTile, targetTile, row, col);
                gp.currentActionIndex -= 1;
            }
            return true;
        }

        private boolean isOnPerimeter(Worker worker) {
            int row = worker.getRow();
            int col = worker.getCol();
            return row == 0 || row == gp.playTiles - 1 || col == 0 || col == gp.playTiles - 1;
        }
    }
}
