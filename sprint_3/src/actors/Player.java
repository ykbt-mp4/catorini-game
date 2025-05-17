package actors;

import java.util.ArrayList;
import java.util.List;

import actors.gods.God;

public class Player {
    private final int playerId;
    private final List<Worker> workers;
    private God godCard;

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

    public God getGodCard() {
        return godCard;
    }

    public void setGodCard(God godCard) {
        this.godCard = godCard;
    }
}
