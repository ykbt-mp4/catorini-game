package actions;

import actors.Player;
import main.GamePanel;

public abstract class Action {
    public abstract String execute(Player player, GamePanel gamePanel);

    public String hotkey() {
        return null; 
    }

    public Action getNextAction() {
        return null; 
    } 
}
