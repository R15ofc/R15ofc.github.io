package io.r15.library.common.animation;

public enum Easing {
    LINEAR {
        @Override
        public float apply(float t) {
            return clamp01(t);
        }
    },
    EASE_IN {
        @Override
        public float apply(float t) {
            float n = clamp01(t);
            return n * n;
        }
    },
    EASE_OUT {
        @Override
        public float apply(float t) {
            float n = clamp01(t);
            return 1.0F - (1.0F - n) * (1.0F - n);
        }
    },
    EASE_IN_OUT {
        @Override
        public float apply(float t) {
            float n = clamp01(t);
            if (n < 0.5F) {
                return 2.0F * n * n;
            }
            return 1.0F - (float) Math.pow(-2.0F * n + 2.0F, 2.0F) / 2.0F;
        }
    };

    public abstract float apply(float t);

    protected static float clamp01(float t) {
        return Math.max(0.0F, Math.min(1.0F, t));
    }
}
