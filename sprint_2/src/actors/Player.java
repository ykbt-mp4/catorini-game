package actors;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int playerId;
    private final List<Worker> workers;

    public Player(int playerId) {
        this.playerId = playerId;
        this.workers = new ArrayList<>();
    }

    public int getPlayerId() {
        return playerId;
    }

    public List<Worker> getWorkers() {
        return workers;
    }
}
