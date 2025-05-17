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

        @Override
        public void execute(Worker worker, GamePanel gamePanel) {
            System.out.println("Executing demeter build action");
        }

        @Override
        public void onTileClick(int row, int col) {
            System.out.println("done");
        }
    }
}