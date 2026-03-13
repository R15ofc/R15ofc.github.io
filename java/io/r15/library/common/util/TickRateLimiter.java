package io.r15.library.common.util;

public final class TickRateLimiter {
    private final int interval;
    private int counter;

    public TickRateLimiter(int interval) {
        if (interval <= 0) {
            throw new IllegalArgumentException("interval must be > 0");
        }
        this.interval = interval;
    }

    public boolean tryAcquire() {
        if (counter <= 0) {
            counter = interval;
            return true;
        }
        counter--;
        return false;
    }

    public void reset() {
        counter = 0;
    }

    public int remainingTicks() {
        return Math.max(0, counter);
    }
}
