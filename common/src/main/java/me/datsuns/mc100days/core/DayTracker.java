package me.datsuns.mc100days.core;

/**
 * Loader-agnostic algorithm that derives the day number from the Minecraft time of day.
 */
public class DayTracker {
    public static final long TICKS_PER_DAY = 24000;
    private static final int FIRST_DAY = 1;

    private long currentDay = FIRST_DAY;

    public DaySnapshot tick(long timeOfDay) {
        long nextDay = (timeOfDay / TICKS_PER_DAY) + FIRST_DAY;
        boolean changed = nextDay != this.currentDay;
        if (changed) {
            this.currentDay = nextDay;
        }
        return new DaySnapshot(this.currentDay, changed);
    }

    public DaySnapshot snapshot() {
        return new DaySnapshot(this.currentDay, false);
    }
}
