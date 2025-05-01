package util;
import actors.gods.Artemis;
import actors.gods.Demeter;
import actors.gods.God;
import actors.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class GodCardAssigner {

    private static final List<God> availableGods = new ArrayList<>(List.of(
            new Artemis(),
            new Demeter()
    ));

    public static void assignRandomGod(Player player) {
        if (availableGods.isEmpty()) {
            throw new IllegalStateException("No more god cards available to assign.");
        }
        Random random = new Random();
        God god = availableGods.remove(random.nextInt(availableGods.size()));
        // player.setGodCard(god);
    }
}
