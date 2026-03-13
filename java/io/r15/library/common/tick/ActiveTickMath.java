package io.r15.library.common.tick;

public final class ActiveTickMath {
    private ActiveTickMath() {
    }

    public static int intervalAfterCycles(ActiveTickProfile profile, int idleCycles) {
        if (idleCycles <= 0) {
            return profile.minIdleInterval();
        }
        int interval = profile.minIdleInterval();
        for (int i = 0; i < idleCycles; i++) {
            interval = profile.nextIdleInterval(interval);
        }
        return interval;
    }

    public static long estimateIdleExecutions(ActiveTickProfile profile, long idleTicks) {
        if (idleTicks <= 0) {
            return 0L;
        }

        long elapsed = 0L;
        long executions = 0L;
        int interval = profile.minIdleInterval();
        while (elapsed < idleTicks) {
            executions++;
            elapsed += Math.max(1, interval);
            interval = profile.nextIdleInterval(interval);
        }

        if (profile.keepAliveInterval() > 0) {
            long keepAliveExecutions = (long) Math.ceil(idleTicks / (double) profile.keepAliveInterval());
            executions = Math.max(executions, keepAliveExecutions);
        }

        return executions;
    }

    public static long estimateExecutedTicks(ActiveTickProfile profile, long activeTicks, long idleTicks) {
        long boundedActive = Math.max(0L, activeTicks);
        long boundedIdle = Math.max(0L, idleTicks);
        return boundedActive + estimateIdleExecutions(profile, boundedIdle);
    }

    public static double estimateSavedPercent(ActiveTickProfile profile, long activeTicks, long idleTicks) {
        long boundedActive = Math.max(0L, activeTicks);
        long boundedIdle = Math.max(0L, idleTicks);
        long baseline = boundedActive + boundedIdle;
        if (baseline <= 0L) {
            return 0.0D;
        }

        long executed = estimateExecutedTicks(profile, boundedActive, boundedIdle);
        double ratio = 1.0D - (executed / (double) baseline);
        return Math.max(0.0D, ratio * 100.0D);
    }
}
