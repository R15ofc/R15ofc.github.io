package io.r15.library.common.tick;

public record ActiveTickDecision(boolean tickNow, ActiveTickReason reason, int intervalUsed, int ticksUntilNextTick) {
    public ActiveTickDecision {
        if (reason == null) {
            throw new IllegalArgumentException("reason cannot be null");
        }
        if (intervalUsed < 1) {
            throw new IllegalArgumentException("intervalUsed must be >= 1");
        }
        if (ticksUntilNextTick < 0) {
            throw new IllegalArgumentException("ticksUntilNextTick must be >= 0");
        }
    }
}
