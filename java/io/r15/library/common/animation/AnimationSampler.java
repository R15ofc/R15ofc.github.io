package io.r15.library.common.animation;

public final class AnimationSampler {
    private AnimationSampler() {
    }

    public static AnimationPose sample(AnimationClip clip, float timeTicks) {
        AnimationPose pose = new AnimationPose();
        clip.tracks().forEach((bone, track) -> pose.put(bone, track.sample(timeTicks, clip.lengthTicks(), clip.looping())));
        return pose;
    }
}
