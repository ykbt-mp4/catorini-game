package src.actions;

import java.util.List;

import src.actors.Player;
import src.buildings.Block;
import src.main.GamePanel;

public class BuildAction extends Action {

    private List<Block> blocks;
    private boolean hasDome;

    @Override
    public String execute(Player player, GamePanel gamePanel) {
        return null;
    }

    @Override
    public String hotkey() {
        return null;
    }

    @Override
    public Action getNextAction() {
        return null;
    }

    public int getHeight() {
        return blocks.get(0).getHeight();
    }
    
    public boolean canBuild() {
        return blocks.stream().allMatch(block -> block.getHeight() < 4) && !hasDome;
    }
}
