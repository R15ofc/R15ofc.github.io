package io.r15.library.common.animation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class AnimationClip {
    private final String id;
    private final float lengthTicks;
    private final boolean looping;
    private final Map<String, TimelineTrack> tracks;

    private AnimationClip(String id, float lengthTicks, boolean looping, Map<String, TimelineTrack> tracks) {
        this.id = Objects.requireNonNull(id, "id");
        this.lengthTicks = lengthTicks;
        this.looping = looping;
        this.tracks = Collections.unmodifiableMap(new HashMap<>(tracks));
    }

    public String id() {
        return id;
    }

    public float lengthTicks() {
        return lengthTicks;
    }

    public boolean looping() {
        return looping;
    }

    public Map<String, TimelineTrack> tracks() {
        return tracks;
    }

    public static Builder builder(String id, float lengthTicks) {
        return new Builder(id, lengthTicks);
    }

    public static final class Builder {
        private final String id;
        private final float lengthTicks;
        private boolean looping = true;
        private final Map<String, TimelineTrack> tracks = new HashMap<>();

        private Builder(String id, float lengthTicks) {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("id cannot be blank");
            }
            if (lengthTicks <= 0.0F) {
                throw new IllegalArgumentException("lengthTicks must be > 0");
            }
            this.id = id;
            this.lengthTicks = lengthTicks;
        }

        public Builder looping(boolean looping) {
            this.looping = looping;
            return this;
        }

        public Builder track(TimelineTrack track) {
            tracks.put(track.bone(), track);
            return this;
        }

        public AnimationClip build() {
            if (tracks.isEmpty()) {
                throw new IllegalStateException("Animation clip must contain at least one track");
            }
            return new AnimationClip(id, lengthTicks, looping, tracks);
        }
    }
}
