package net.scandicraft.capacities;

import java.util.concurrent.TimeUnit;

public abstract class BaseCapacity implements ICapacity {

    private String getWaitingTime(long cooldown) {
        StringBuilder timeWaiting = new StringBuilder();

        long minutes = TimeUnit.SECONDS.toMinutes(cooldown);
        long secondes = TimeUnit.SECONDS.toSeconds(cooldown);

        if (minutes > 0) {
            timeWaiting.append(String.format("%d %s", (int) minutes, minutes <= 1 ? "minute " : "minutes "));
            secondes = cooldown % 60;
        }

        timeWaiting.append(String.format("%d %s", (int) secondes, secondes <= 1 ? "seconde" : "secondes"));

        return timeWaiting.toString();
    }

}
