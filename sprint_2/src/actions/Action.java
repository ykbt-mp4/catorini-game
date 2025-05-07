package src.actions;

import src.actors.Player;
import src.main.GamePanel;

public abstract class Action {
    public abstract String execute(Player player, GamePanel gamePanel);

    public String hotkey() {
        return null; 
    }

    public Action getNextAction() {
        return null; 
    } 
}
