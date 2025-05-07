package actions;

import actors.Player;
import main.GamePanel;
import util.TickManager;

public abstract class Action {
    public abstract String execute(Player player, GamePanel gamePanel);

    public String hotkey() {
        return null; 
    }

    public Action getNextAction() {
        return null; 
    }

    protected void tick() {
        TickManager.incrementTick();
    }
}