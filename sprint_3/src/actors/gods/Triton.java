package actors.gods;

import actions.BuildAction;
import actions.MoveAction;
import tile.Tile;
import actors.Worker;
import main.GamePanel;

public class Triton extends God {

    public Triton() {
        super("Triton",
                "Each time your Worker moves into a perimeter space, it may immediately move again.");
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
            boolean isOnPerimeter =
                    worker.getRow() == 0 || worker.getRow() == gp.playTiles - 1 ||
                            worker.getCol() == 0 || worker.getCol() == gp.playTiles - 1;
            if (!isOnPerimeter) {
                System.out.println("Triton: Worker not on perimeter. Skipping optional move.");
                gp.skipCurrentAction();
            } else {
                System.out.println("Triton: Worker is on perimeter. Awaiting optional move.");
            }
        }

        @Override
        public boolean onTileClick(int row, int col) {
            Tile targetTile = getTile(row, col);
            Tile currentTile = getTile(worker.getRow(), worker.getCol());

            if (isNotValidTile(row, col)) {
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
