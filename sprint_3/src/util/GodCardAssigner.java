package util;

import actors.Player;
import actors.gods.Artemis;
import actors.gods.Demeter;
import actors.gods.Triton;
import actors.gods.God;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/**
 * The GodCardAssigner class is responsible for assigning
 * a random unique God card to a Player from a predefined list.
 * Once a God is assigned, it is removed from the pool to prevent duplicates.
 */
public class GodCardAssigner {

    /**
     * List of available gods that can be assigned.
     * Each god can only be assigned once.
     */
    private static final List<God> availableGods = new ArrayList<>(List.of(
            new Artemis(),
            new Demeter(),
            new Triton()
    ));

    /**
     * Assigns a random god card to the specified player.
     * Once a god is assigned, it is removed from the available pool.
     * @param player the player to whom a god card will be assigned
     * @throws IllegalStateException if no more gods are available to assign
     */
    public static void assignRandomGod(Player player) {
        if (availableGods.isEmpty()) {
            throw new IllegalStateException("No more god cards available to assign.");
        }
        Random random = new Random();
        God god = availableGods.remove(random.nextInt(availableGods.size()));
        player.setGodCard(god);
    }
}
