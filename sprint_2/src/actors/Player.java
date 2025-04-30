package actors;

import actors.gods.God;

public class Player {
    private God godCard;

    public void setGodCard(God godCard) {
        this.godCard = godCard;
    }

    public God getGodCard() {
        return godCard;
    }
}