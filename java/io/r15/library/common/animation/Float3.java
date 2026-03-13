package io.r15.library.common.animation;

public record Float3(float x, float y, float z) {
    public static final Float3 ZERO = new Float3(0.0F, 0.0F, 0.0F);
    public static final Float3 ONE = new Float3(1.0F, 1.0F, 1.0F);

    public Float3 add(Float3 other) {
        return new Float3(x + other.x, y + other.y, z + other.z);
    }

    public Float3 scale(float factor) {
        return new Float3(x * factor, y * factor, z * factor);
    }

    public static Float3 lerp(Float3 a, Float3 b, float alpha) {
        float t = Math.max(0.0F, Math.min(1.0F, alpha));
        return new Float3(
            a.x + (b.x - a.x) * t,
            a.y + (b.y - a.y) * t,
            a.z + (b.z - a.z) * t
        );
    }
}
