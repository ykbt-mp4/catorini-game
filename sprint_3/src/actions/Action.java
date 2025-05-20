package actions;

import actors.Worker;
import main.GamePanel;

public abstract class Action {

    protected Worker worker;
    protected GamePanel gp;

    public void execute(Worker worker, GamePanel gamePanel) {
        this.worker = worker;
        this.gp = gamePanel;
    }

    public Action getNextAction() {
        return null;
    }

    public abstract boolean onTileClick(int row, int col);
}
