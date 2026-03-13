package io.r15.library.common.animation;

public record AnimationLayer(AnimationClip clip, float timeTicks, float weight) {
    public AnimationLayer {
        if (clip == null) {
            throw new IllegalArgumentException("clip cannot be null");
        }
        if (weight < 0.0F || weight > 1.0F) {
            throw new IllegalArgumentException("weight must be in range [0, 1]");
        }
    }
}
