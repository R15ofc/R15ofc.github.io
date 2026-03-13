package io.r15.library.common.tick;

import java.util.Objects;

public final class ActiveTickController {
    private final ActiveTickProfile profile;

    private long lastActivityTick = Long.MIN_VALUE;
    private long forcedAwakeUntilTick = Long.MIN_VALUE;
    private long nextIdleTick = Long.MIN_VALUE;
    private long nextKeepAliveTick = Long.MIN_VALUE;
    private int idleInterval;
    private long executedTicks;
    private long skippedTicks;
    private ActiveTickDecision lastDecision;

    public ActiveTickController(ActiveTickProfile profile) {
        this.profile = Objects.requireNonNull(profile, "profile");
        this.idleInterval = profile.minIdleInterval();
        this.lastDecision = new ActiveTickDecision(false, ActiveTickReason.SLEEPING, idleInterval, 0);
    }

    public ActiveTickProfile profile() {
        return profile;
    }

    public long lastActivityTick() {
        return lastActivityTick;
    }

    public long forcedAwakeUntilTick() {
        return forcedAwakeUntilTick;
    }

    public int idleInterval() {
        return idleInterval;
    }

    public long executedTicks() {
        return executedTicks;
    }

    public long skippedTicks() {
        return skippedTicks;
    }

    public ActiveTickDecision lastDecision() {
        return lastDecision;
    }

    public void markActivity(long gameTick) {
        lastActivityTick = Math.max(lastActivityTick, gameTick);
        forcedAwakeUntilTick = Math.max(forcedAwakeUntilTick, saturatingAdd(gameTick, profile.activityGraceTicks()));
    }

    public void markInteraction(long gameTick) {
        forcedAwakeUntilTick = Math.max(forcedAwakeUntilTick, saturatingAdd(gameTick, profile.interactionBoostTicks()));
    }

    public void wakeUpFor(long gameTick, int ticks) {
        if (ticks <= 0) {
            return;
        }
        forcedAwakeUntilTick = Math.max(forcedAwakeUntilTick, saturatingAdd(gameTick, ticks));
    }

    public boolean shouldTick(long gameTick, boolean activeSignal) {
        return shouldTick(gameTick, activeSignal, false);
    }

    public boolean shouldTick(long gameTick, boolean activeSignal, boolean interactionSignal) {
        ActiveTickDecision decision = evaluate(gameTick, activeSignal, interactionSignal);
        lastDecision = decision;
        if (decision.tickNow()) {
            executedTicks++;
        } else {
            skippedTicks++;
        }
        return decision.tickNow();
    }

    public ActiveTickDecision evaluate(long gameTick, boolean activeSignal, boolean interactionSignal) {
        if (activeSignal) {
            markActivity(gameTick);
            resetIdleSchedule(gameTick);
            return new ActiveTickDecision(true, ActiveTickReason.ACTIVE_SIGNAL, 1, 1);
        }

        if (interactionSignal) {
            markInteraction(gameTick);
            resetIdleSchedule(gameTick);
            return new ActiveTickDecision(true, ActiveTickReason.INTERACTION_SIGNAL, 1, 1);
        }

        if (isInsideAwakeWindow(gameTick)) {
            resetIdleSchedule(gameTick);
            ActiveTickReason reason = isInsideActivityWindow(gameTick) ? ActiveTickReason.ACTIVITY_WINDOW : ActiveTickReason.FORCED_WAKEUP;
            return new ActiveTickDecision(true, reason, 1, 1);
        }

        initializeIdleSchedule(gameTick);

        if (profile.keepAliveInterval() > 0 && gameTick >= nextKeepAliveTick) {
            nextKeepAliveTick = saturatingAdd(gameTick, profile.keepAliveInterval());
            return new ActiveTickDecision(true, ActiveTickReason.PERIODIC_KEEP_ALIVE, idleInterval, ticksUntilNext(gameTick));
        }

        if (gameTick >= nextIdleTick) {
            int intervalNow = idleInterval;
            nextIdleTick = saturatingAdd(gameTick, intervalNow);
            idleInterval = profile.nextIdleInterval(intervalNow);
            return new ActiveTickDecision(true, ActiveTickReason.IDLE_BACKOFF, intervalNow, intervalNow);
        }

        return new ActiveTickDecision(false, ActiveTickReason.SLEEPING, idleInterval, ticksUntilNext(gameTick));
    }

    public ActiveTickState saveState() {
        return new ActiveTickState(
            lastActivityTick,
            forcedAwakeUntilTick,
            nextIdleTick,
            nextKeepAliveTick,
            idleInterval,
            executedTicks,
            skippedTicks
        );
    }

    public void loadState(ActiveTickState state) {
        Objects.requireNonNull(state, "state");
        lastActivityTick = state.lastActivityTick();
        forcedAwakeUntilTick = state.forcedAwakeUntilTick();
        nextIdleTick = state.nextIdleTick();
        nextKeepAliveTick = state.nextKeepAliveTick();
        idleInterval = Math.max(profile.minIdleInterval(), Math.min(state.idleInterval(), profile.maxIdleInterval()));
        executedTicks = state.executedTicks();
        skippedTicks = state.skippedTicks();
        lastDecision = new ActiveTickDecision(false, ActiveTickReason.SLEEPING, idleInterval, 0);
    }

    public void reset() {
        lastActivityTick = Long.MIN_VALUE;
        forcedAwakeUntilTick = Long.MIN_VALUE;
        nextIdleTick = Long.MIN_VALUE;
        nextKeepAliveTick = Long.MIN_VALUE;
        idleInterval = profile.minIdleInterval();
        executedTicks = 0L;
        skippedTicks = 0L;
        lastDecision = new ActiveTickDecision(false, ActiveTickReason.SLEEPING, idleInterval, 0);
    }

    private void initializeIdleSchedule(long gameTick) {
        if (nextIdleTick == Long.MIN_VALUE) {
            nextIdleTick = gameTick;
        }
        if (profile.keepAliveInterval() > 0 && nextKeepAliveTick == Long.MIN_VALUE) {
            nextKeepAliveTick = saturatingAdd(gameTick, profile.keepAliveInterval());
        }
    }

    private void resetIdleSchedule(long gameTick) {
        idleInterval = profile.minIdleInterval();
        nextIdleTick = saturatingAdd(gameTick, 1);
        if (profile.keepAliveInterval() > 0) {
            nextKeepAliveTick = saturatingAdd(gameTick, profile.keepAliveInterval());
        } else {
            nextKeepAliveTick = Long.MIN_VALUE;
        }
    }

    private boolean isInsideAwakeWindow(long gameTick) {
        return gameTick <= forcedAwakeUntilTick;
    }

    private boolean isInsideActivityWindow(long gameTick) {
        if (lastActivityTick == Long.MIN_VALUE) {
            return false;
        }
        long diff = gameTick - lastActivityTick;
        return diff >= 0 && diff <= profile.activityGraceTicks();
    }

    private int ticksUntilNext(long gameTick) {
        int untilIdle = deltaTicks(nextIdleTick, gameTick);
        if (profile.keepAliveInterval() <= 0 || nextKeepAliveTick == Long.MIN_VALUE) {
            return untilIdle;
        }
        int untilKeepAlive = deltaTicks(nextKeepAliveTick, gameTick);
        if (untilIdle == 0) {
            return untilKeepAlive;
        }
        if (untilKeepAlive == 0) {
            return untilIdle;
        }
        return Math.min(untilIdle, untilKeepAlive);
    }

    private static int deltaTicks(long targetTick, long currentTick) {
        if (targetTick == Long.MIN_VALUE || targetTick <= currentTick) {
            return 1;
        }
        long diff = targetTick - currentTick;
        return (int) Math.min(Integer.MAX_VALUE, diff);
    }

    private static long saturatingAdd(long value, int delta) {
        if (delta <= 0) {
            return value;
        }
        long out = value + delta;
        if (out < value) {
            return Long.MAX_VALUE;
        }
        return out;
    }
}
