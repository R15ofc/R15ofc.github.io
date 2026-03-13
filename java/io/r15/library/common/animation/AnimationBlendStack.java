package io.r15.library.common.animation;

import java.util.ArrayList;
import java.util.List;

public final class AnimationBlendStack {
    private final List<AnimationLayer> layers = new ArrayList<>();

    public AnimationBlendStack addLayer(AnimationLayer layer) {
        layers.add(layer);
        return this;
    }

    public AnimationBlendStack clear() {
        layers.clear();
        return this;
    }

    public int size() {
        return layers.size();
    }

    public AnimationPose sample() {
        if (layers.isEmpty()) {
            return new AnimationPose();
        }

        AnimationPose result = null;
        float totalWeight = 0.0F;

        for (AnimationLayer layer : layers) {
            if (layer.weight() <= 0.0F) {
                continue;
            }

            AnimationPose sampled = AnimationSampler.sample(layer.clip(), layer.timeTicks());
            if (result == null) {
                result = sampled;
                totalWeight = Math.max(0.0001F, layer.weight());
                continue;
            }

            float alpha = layer.weight() / (totalWeight + layer.weight());
            result = AnimationPose.blend(result, sampled, alpha);
            totalWeight += layer.weight();
        }

        return result == null ? new AnimationPose() : result;
    }
}
