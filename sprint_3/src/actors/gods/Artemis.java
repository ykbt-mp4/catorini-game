package actors.gods;

import actions.Action;
import actions.BuildAction;
import actions.MoveAction;
import main.GamePanel;
import tile.Tile;
import actors.Worker;

public class Artemis extends God
{
    public Artemis() {
        super("Artemis",
                "Your Worker may move one additional time, but not back to its initial space.");
        actions.add(new MoveAction());
        actions.add(new ArtemisAction());
        actions.add(new BuildAction());
    }

    private static class ArtemisAction extends Action {
        @Override
        public void execute(Worker worker, GamePanel gamePanel) {
            System.out.println("Executing artemis move action");
        }

        @Override
        public void onTileClick(int row, int col) {
            System.out.println("done");
        }
    }

}