package io.r15.library.common.tick;

import java.util.Objects;

public final class ActiveTickProbes {
    private ActiveTickProbes() {
    }

    public static ActiveTickProbe always(boolean value) {
        return gameTick -> value;
    }

    public static ActiveTickProbe not(ActiveTickProbe probe) {
        Objects.requireNonNull(probe, "probe");
        return gameTick -> !probe.isActive(gameTick);
    }

    public static ActiveTickProbe any(ActiveTickProbe... probes) {
        Objects.requireNonNull(probes, "probes");
        if (probes.length == 0) {
            return always(false);
        }
        return gameTick -> {
            for (ActiveTickProbe probe : probes) {
                if (probe != null && probe.isActive(gameTick)) {
                    return true;
                }
            }
            return false;
        };
    }

    public static ActiveTickProbe all(ActiveTickProbe... probes) {
        Objects.requireNonNull(probes, "probes");
        if (probes.length == 0) {
            return always(true);
        }
        return gameTick -> {
            for (ActiveTickProbe probe : probes) {
                if (probe != null && !probe.isActive(gameTick)) {
                    return false;
                }
            }
            return true;
        };
    }
}
