package turn;

import actors.Player;
import actors.Worker;

public class StandardTurn extends TurnTemplate {
    private final Player player;
    private final TileManager tileManager;

    private Worker selectedWorker;

    public StandardTurn(Player player, TileManager tileManager) {
        this.player = player;
        this.tileManager = tileManager;
    }

    @Override
    protected void selectWorker() {
        // Placeholder — real version should use mouse click or input
        selectedWorker= player.getWorkerA(); // or based on some selection logic
        System.out.println(player.getName() + " selected a worker.");
    }

    @Override
    protected boolean move() {
        // Placeholder — should use tileManager and actual move logic
        System.out.println(player.getName() + " moved.");
        return true;
    }

    @Override
    protected boolean build() {
        // Placeholder — should use tileManager to build next to worker
        System.out.println(player.getName() + " built.");
        return true;
    }

    @Override
    protected boolean checkWin() {
        // Placeholder — check if worker is on level 3
        return false;
    }

    @Override
    protected boolean hasAvailableActions() {
        // Placeholder — check if either worker can move and build
        return true;
    }
}
