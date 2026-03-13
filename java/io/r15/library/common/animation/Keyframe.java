package io.r15.library.common.animation;

public record Keyframe(float tick, Transform transform, Easing easing) {
    public Keyframe {
        if (tick < 0.0F) {
            throw new IllegalArgumentException("tick must be >= 0");
        }
        if (transform == null) {
            throw new IllegalArgumentException("transform cannot be null");
        }
        if (easing == null) {
            easing = Easing.LINEAR;
        }
    }
}
