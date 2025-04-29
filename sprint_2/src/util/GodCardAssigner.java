package util;
import actors.Artemis;
import actors.Demeter;
import actors.God;
import actors.Player;

import java.util.Random;
import java.util.List;
import java.util.Arrays;

public class GodCardAssigner {

        private static final List<God> availableGods = Arrays.asList(
                new Artemis(),
                new Demeter()
        );

        public static void assignRandomGod(Player player) {
            Random random = new Random();
            God god = availableGods.get(random.nextInt(availableGods.size()));
            player.setGodCard(god);
        }
    }

