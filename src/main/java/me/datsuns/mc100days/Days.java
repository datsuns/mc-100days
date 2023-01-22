package me.datsuns.mc100days;

public class Days {
    public final long TIME_PER_DAY = 24000;
    public long n;

    public Days(){
        this.n = 0;
    }

    public boolean tick(long timeOfDay){
        long d = timeOfDay / TIME_PER_DAY;
        if( this.n != d ){
            this.n = d;
            return true;
        }
        return false;
    }

    public String toString(){
        return String.format("days %d", this.n);
    }
}
