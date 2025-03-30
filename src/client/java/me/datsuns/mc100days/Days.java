package me.datsuns.mc100days;

public class Days {
    public final long TIME_PER_DAY = 24000;
    public final int origin = 1;
    public long n;

    public Days() {
        this.n = origin;
    }

    public boolean tick(long timeOfDay) {
        long d = (timeOfDay / TIME_PER_DAY) + origin;
        if (this.n != d) {
            this.n = d;
            return true;
        }
        return false;
    }

    public String toString() {
        return String.format("days %d", this.n);
    }
}
