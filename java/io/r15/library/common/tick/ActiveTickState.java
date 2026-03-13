package io.r15.library.common.tick;

public record ActiveTickState(
    long lastActivityTick,
    long forcedAwakeUntilTick,
    long nextIdleTick,
    long nextKeepAliveTick,
    int idleInterval,
    long executedTicks,
    long skippedTicks
) {
    public ActiveTickState {
        if (idleInterval < 1) {
            throw new IllegalArgumentException("idleInterval must be >= 1");
        }
        if (executedTicks < 0) {
            throw new IllegalArgumentException("executedTicks must be >= 0");
        }
        if (skippedTicks < 0) {
            throw new IllegalArgumentException("skippedTicks must be >= 0");
        }
    }
}
