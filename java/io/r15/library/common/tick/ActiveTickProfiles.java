package io.r15.library.common.tick;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public final class ActiveTickProfiles {
    private static final Map<String, ActiveTickProfile> PRESETS;

    static {
        Map<String, ActiveTickProfile> presets = new LinkedHashMap<>();
        presets.put("balanced", new ActiveTickProfile(20, 60, 2, 20, 2, 80));
        presets.put("aggressive", new ActiveTickProfile(10, 40, 3, 40, 3, 80));
        presets.put("ultra_idle", new ActiveTickProfile(6, 24, 5, 80, 5, 120));
        presets.put("create_kinetic", new ActiveTickProfile(30, 80, 2, 12, 1, 40));
        presets.put("create_contraption", new ActiveTickProfile(25, 100, 2, 16, 2, 40));
        PRESETS = Collections.unmodifiableMap(presets);
    }

    private ActiveTickProfiles() {
    }

    public static ActiveTickProfile balanced() {
        return PRESETS.get("balanced");
    }

    public static Collection<String> names() {
        return PRESETS.keySet();
    }

    public static Optional<ActiveTickProfile> byName(String name) {
        if (name == null || name.isBlank()) {
            return Optional.empty();
        }
        return Optional.ofNullable(PRESETS.get(name.toLowerCase(Locale.ROOT)));
    }
}
