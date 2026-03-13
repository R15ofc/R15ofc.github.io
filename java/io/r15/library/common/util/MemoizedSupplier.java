package io.r15.library.common.util;

import java.util.Objects;
import java.util.function.Supplier;

public final class MemoizedSupplier<T> implements Supplier<T> {
    private final Supplier<T> source;
    private volatile boolean initialized;
    private T value;

    public MemoizedSupplier(Supplier<T> source) {
        this.source = Objects.requireNonNull(source, "source");
    }

    @Override
    public T get() {
        if (!initialized) {
            synchronized (this) {
                if (!initialized) {
                    value = source.get();
                    initialized = true;
                }
            }
        }
        return value;
    }
}
