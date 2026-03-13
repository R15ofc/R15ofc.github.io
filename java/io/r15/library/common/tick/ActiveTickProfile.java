package io.r15.library.common.tick;

public record ActiveTickProfile(
    int activityGraceTicks,
    int interactionBoostTicks,
    int minIdleInterval,
    int maxIdleInterval,
    int idleIntervalStep,
    int keepAliveInterval
) {
    public ActiveTickProfile {
        if (activityGraceTicks < 0) {
            throw new IllegalArgumentException("activityGraceTicks must be >= 0");
        }
        if (interactionBoostTicks < 0) {
            throw new IllegalArgumentException("interactionBoostTicks must be >= 0");
        }
        if (minIdleInterval < 1) {
            throw new IllegalArgumentException("minIdleInterval must be >= 1");
        }
        if (maxIdleInterval < minIdleInterval) {
            throw new IllegalArgumentException("maxIdleInterval must be >= minIdleInterval");
        }
        if (idleIntervalStep < 1) {
            throw new IllegalArgumentException("idleIntervalStep must be >= 1");
        }
        if (keepAliveInterval < 0) {
            throw new IllegalArgumentException("keepAliveInterval must be >= 0");
        }
    }

    public int nextIdleInterval(int currentInterval) {
        int boundedCurrent = Math.max(minIdleInterval, Math.min(currentInterval, maxIdleInterval));
        int next = boundedCurrent + idleIntervalStep;
        if (next < 0) {
            return maxIdleInterval;
        }
        return Math.min(next, maxIdleInterval);
    }
}
