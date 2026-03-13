package io.r15.library.common.animation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class AnimationPose {
    private final Map<String, Transform> transforms = new HashMap<>();

    public void put(String bone, Transform transform) {
        transforms.put(bone, transform);
    }

    public Transform getOrIdentity(String bone) {
        return transforms.getOrDefault(bone, Transform.IDENTITY);
    }

    public Map<String, Transform> asMap() {
        return Collections.unmodifiableMap(transforms);
    }

    public static AnimationPose blend(AnimationPose a, AnimationPose b, float alpha) {
        AnimationPose pose = new AnimationPose();
        a.transforms.forEach((bone, transform) -> {
            Transform blended = Transform.blend(transform, b.getOrIdentity(bone), alpha);
            pose.put(bone, blended);
        });
        b.transforms.forEach((bone, transform) -> {
            if (!pose.transforms.containsKey(bone)) {
                pose.put(bone, Transform.blend(Transform.IDENTITY, transform, alpha));
            }
        });
        return pose;
    }
}
