package net.scandicraft.utils;

public class MathUtils {

    public static int TICK_SECONDS = 20;    //1 tick = 20 seconds

    public static int convertSecondsToTicks(int seconds) {
        return TICK_SECONDS * seconds;
    }

    public static int convertMinutesToTicks(int minutes) {
        return TICK_SECONDS * 60 * minutes;
    }

}
