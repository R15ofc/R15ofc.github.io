package io.r15.library.common.tick;

@FunctionalInterface
public interface ActiveTickProbe {
    boolean isActive(long gameTick);
}
