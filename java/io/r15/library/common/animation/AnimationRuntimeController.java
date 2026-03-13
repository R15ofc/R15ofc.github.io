package io.r15.library.common.animation;

public final class AnimationRuntimeController {
    private AnimationClip current;
    private AnimationClip previous;

    private float currentTime;
    private float previousTime;

    private int transitionDuration;
    private int transitionRemaining;

    public AnimationClip currentClip() {
        return current;
    }

    public boolean isPlaying() {
        return current != null;
    }

    public boolean isTransitioning() {
        return transitionRemaining > 0;
    }

    public void stop() {
        current = null;
        previous = null;
        currentTime = 0.0F;
        previousTime = 0.0F;
        transitionDuration = 0;
        transitionRemaining = 0;
    }

    public void play(AnimationClip clip) {
        play(clip, 0);
    }

    public void play(AnimationClip clip, int transitionTicks) {
        if (clip == null) {
            throw new IllegalArgumentException("clip cannot be null");
        }

        if (current == null || transitionTicks <= 0) {
            previous = null;
            current = clip;
            currentTime = 0.0F;
            previousTime = 0.0F;
            transitionDuration = 0;
            transitionRemaining = 0;
            return;
        }

        previous = current;
        previousTime = currentTime;
        current = clip;
        currentTime = 0.0F;
        transitionDuration = transitionTicks;
        transitionRemaining = transitionTicks;
    }

    public void tick() {
        if (current == null) {
            return;
        }

        currentTime += 1.0F;

        if (previous != null) {
            previousTime += 1.0F;
        }

        if (transitionRemaining > 0) {
            transitionRemaining--;
            if (transitionRemaining == 0) {
                previous = null;
                previousTime = 0.0F;
                transitionDuration = 0;
            }
        }
    }

    public AnimationPose sample(float partialTicks) {
        if (current == null) {
            return new AnimationPose();
        }

        AnimationPose currentPose = AnimationSampler.sample(current, currentTime + partialTicks);
        if (previous == null || transitionDuration <= 0 || transitionRemaining <= 0) {
            return currentPose;
        }

        AnimationPose previousPose = AnimationSampler.sample(previous, previousTime + partialTicks);
        float progress = 1.0F - (transitionRemaining / (float) transitionDuration);
        return AnimationPose.blend(previousPose, currentPose, progress);
    }
}
