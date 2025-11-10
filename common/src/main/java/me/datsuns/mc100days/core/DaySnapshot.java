package me.datsuns.mc100days.core;

/**
 * Immutable view of the current 100-days progress.
 *
 * @param dayNumber the current day number (1-indexed)
 * @param changed   true when the value differs from the previous snapshot
 */
public record DaySnapshot(long dayNumber, boolean changed) {
    private static final String DAY_FORMAT = "days %d";

    public String label() {
        return String.format(DAY_FORMAT, dayNumber);
    }
}
