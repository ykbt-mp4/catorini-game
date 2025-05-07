package actions;

import actors.Player;
import main.GamePanel;

public class BuildAction extends Action {

    @Override
    public String execute(Player player, GamePanel gamePanel) {
        // Perform build logic here
        System.out.println("Build action executed by Player " + player.getPlayerId());

        // Increment the tick
        tick();

        return "Build action completed";
    }

    @Override
    public String hotkey() {
        return "B"; // Example hotkey for build action
    }
}