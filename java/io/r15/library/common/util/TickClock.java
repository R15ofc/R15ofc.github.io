package io.r15.library.common.util;

public final class TickClock {
    private float ageTicks;
    private float speed = 1.0F;

    public float ageTicks() {
        return ageTicks;
    }

    public float speed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void reset() {
        this.ageTicks = 0.0F;
    }

    public void tick() {
        this.ageTicks += speed;
    }

    public float sample(float partialTicks) {
        return ageTicks + partialTicks * speed;
    }
}
