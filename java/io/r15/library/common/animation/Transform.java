package io.r15.library.common.animation;

public record Transform(Float3 translation, Float3 rotation, Float3 scale) {
    public static final Transform IDENTITY = new Transform(Float3.ZERO, Float3.ZERO, Float3.ONE);

    public static Transform blend(Transform from, Transform to, float alpha) {
        return new Transform(
            Float3.lerp(from.translation, to.translation, alpha),
            Float3.lerp(from.rotation, to.rotation, alpha),
            Float3.lerp(from.scale, to.scale, alpha)
        );
    }

    public Transform combine(Transform additive) {
        return new Transform(
            translation.add(additive.translation),
            rotation.add(additive.rotation),
            new Float3(
                scale.x() * additive.scale.x(),
                scale.y() * additive.scale.y(),
                scale.z() * additive.scale.z()
            )
        );
    }
}
