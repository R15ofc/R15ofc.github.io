package io.r15.library.common.animation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class TimelineTrack {
    private final String bone;
    private final List<Keyframe> keyframes;

    public TimelineTrack(String bone, List<Keyframe> keyframes) {
        if (bone == null || bone.isBlank()) {
            throw new IllegalArgumentException("bone cannot be blank");
        }
        if (keyframes == null || keyframes.isEmpty()) {
            throw new IllegalArgumentException("keyframes cannot be empty");
        }
        this.bone = bone;
        this.keyframes = new ArrayList<>(keyframes);
        this.keyframes.sort(Comparator.comparing(Keyframe::tick));
    }

    public String bone() {
        return bone;
    }

    public Transform sample(float time, float clipLength, boolean looping) {
        if (keyframes.size() == 1) {
            return keyframes.get(0).transform();
        }

        float localTime = normalizeTime(time, clipLength, looping);

        Keyframe previous = keyframes.get(0);
        Keyframe next = keyframes.get(keyframes.size() - 1);

        for (int i = 0; i < keyframes.size() - 1; i++) {
            Keyframe current = keyframes.get(i);
            Keyframe candidate = keyframes.get(i + 1);
            if (localTime >= current.tick() && localTime <= candidate.tick()) {
                previous = current;
                next = candidate;
                break;
            }
        }

        if (localTime < keyframes.get(0).tick()) {
            return keyframes.get(0).transform();
        }
        if (localTime > keyframes.get(keyframes.size() - 1).tick()) {
            return keyframes.get(keyframes.size() - 1).transform();
        }

        float span = next.tick() - previous.tick();
        if (span <= 0.0001F) {
            return next.transform();
        }

        float progress = (localTime - previous.tick()) / span;
        float eased = next.easing().apply(progress);
        return Transform.blend(previous.transform(), next.transform(), eased);
    }

    private static float normalizeTime(float time, float clipLength, boolean looping) {
        if (!looping || clipLength <= 0.0F) {
            return Math.max(0.0F, time);
        }
        float mod = time % clipLength;
        return mod < 0.0F ? mod + clipLength : mod;
    }
}
