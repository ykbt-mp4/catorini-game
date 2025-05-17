package actors.gods;

import actions.Action;
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

    private static class TritonAction extends Action {

        @Override
        public void execute(Worker worker, GamePanel gamePanel) {
            System.out.println("Executing triton move action");
        }

        @Override
        public void onTileClick(int row, int col) {
            System.out.println("done");
        }
    }

}
