package io.r15.library.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class SimpleStateMachine {
    private final Map<String, State> states = new HashMap<>();
    private String currentId;

    public SimpleStateMachine addState(String id, State state) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id cannot be blank");
        }
        states.put(id, Objects.requireNonNull(state, "state"));
        if (currentId == null) {
            currentId = id;
            state.onEnter();
        }
        return this;
    }

    public String currentId() {
        return currentId;
    }

    public State current() {
        if (currentId == null) {
            return State.EMPTY;
        }
        return states.getOrDefault(currentId, State.EMPTY);
    }

    public boolean transitionTo(String id) {
        if (id == null || id.isBlank() || id.equals(currentId)) {
            return false;
        }
        State next = states.get(id);
        if (next == null) {
            return false;
        }

        State old = current();
        old.onExit();
        currentId = id;
        next.onEnter();
        return true;
    }

    public void tick() {
        current().tick();
    }

    public interface State {
        State EMPTY = new State() {
        };

        default void onEnter() {
        }

        default void onExit() {
        }

        default void tick() {
        }
    }
}
