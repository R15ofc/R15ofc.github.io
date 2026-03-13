package io.r15.library.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class WeightedPicker<T> {
    private final List<Entry<T>> entries = new ArrayList<>();
    private double totalWeight;

    public WeightedPicker<T> add(T value, double weight) {
        Objects.requireNonNull(value, "value");
        if (weight <= 0.0D) {
            throw new IllegalArgumentException("weight must be > 0");
        }
        entries.add(new Entry<>(value, weight));
        totalWeight += weight;
        return this;
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public int size() {
        return entries.size();
    }

    public T pick(Random random) {
        Objects.requireNonNull(random, "random");
        if (entries.isEmpty()) {
            throw new IllegalStateException("Cannot pick from an empty WeightedPicker");
        }

        double target = random.nextDouble() * totalWeight;
        double seen = 0.0D;

        for (Entry<T> entry : entries) {
            seen += entry.weight;
            if (target <= seen) {
                return entry.value;
            }
        }

        return entries.get(entries.size() - 1).value;
    }

    public List<T> values() {
        List<T> out = new ArrayList<>(entries.size());
        for (Entry<T> entry : entries) {
            out.add(entry.value);
        }
        return out;
    }

    private record Entry<T>(T value, double weight) {
    }
}
