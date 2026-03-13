package io.r15.library.common.util;

import java.util.Arrays;

public final class CircularFloatBuffer {
    private final float[] values;
    private int size;
    private int index;

    public CircularFloatBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be > 0");
        }
        this.values = new float[capacity];
    }

    public void add(float value) {
        values[index] = value;
        index = (index + 1) % values.length;
        size = Math.min(size + 1, values.length);
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return values.length;
    }

    public float average() {
        if (size == 0) {
            return 0.0F;
        }
        float sum = 0.0F;
        for (int i = 0; i < size; i++) {
            sum += values[i];
        }
        return sum / size;
    }

    public float max() {
        if (size == 0) {
            return 0.0F;
        }
        float max = Float.NEGATIVE_INFINITY;
        for (int i = 0; i < size; i++) {
            max = Math.max(max, values[i]);
        }
        return max;
    }

    public float min() {
        if (size == 0) {
            return 0.0F;
        }
        float min = Float.POSITIVE_INFINITY;
        for (int i = 0; i < size; i++) {
            min = Math.min(min, values[i]);
        }
        return min;
    }

    public void clear() {
        Arrays.fill(values, 0.0F);
        size = 0;
        index = 0;
    }
}
