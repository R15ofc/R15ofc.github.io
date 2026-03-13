package io.r15.library.common.tick;

public enum ActiveTickReason {
    ACTIVE_SIGNAL,
    INTERACTION_SIGNAL,
    ACTIVITY_WINDOW,
    FORCED_WAKEUP,
    PERIODIC_KEEP_ALIVE,
    IDLE_BACKOFF,
    SLEEPING
}
