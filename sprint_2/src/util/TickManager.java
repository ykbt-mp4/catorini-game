package util;

public class TickManager {
    private static int tickCount = 0;

    public static void incrementTick() {
        tickCount++;
        System.out.println("Tick: " + tickCount);
    }

    public static int getTickCount() {
        return tickCount;
    }
}