# Active Tick Toolkit

`tick only active` helpers live in `io.r15.library.common.tick` and `io.r15.library.forge.api.ActiveTickApi`.

## Core pieces

- `ActiveTickProfile`: throttling profile (grace windows, min/max idle interval, keep-alive).
- `ActiveTickController`: per-object state machine that decides if logic should tick now.
- `ActiveTickProfiles`: ready presets:
  - `balanced`
  - `aggressive`
  - `ultra_idle`
  - `create_kinetic`
  - `create_contraption`
- `ActiveTickMath`: estimate tick savings and backoff intervals.
- `ActiveTickProbe` / `ActiveTickProbes`: compose active predicates.

## Quick block entity pattern

```java
public class MyMachineBlockEntity extends BlockEntity {
    private final ActiveTickController tickGate =
        ActiveTickApi.controller(ActiveTickProfiles.byName("create_kinetic").orElse(ActiveTickProfiles.balanced()));

    public static void tick(Level level, BlockPos pos, BlockState state, MyMachineBlockEntity be) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        long gameTick = serverLevel.getGameTime();
        boolean redstone = ActiveTickApi.hasNeighborSignal(serverLevel, pos);
        boolean playersNear = ActiveTickApi.hasPlayersNearby(serverLevel, pos, 10.0D);
        boolean hasWork = be.hasPendingWork();

        if (!be.tickGate.shouldTick(gameTick, hasWork || redstone, playersNear)) {
            return;
        }

        be.serverTickBody();
    }
}
```

## Create kinetic helpers

```java
Optional<CreateKineticSnapshot> snapshot = CreateAddonApi.snapshot(blockEntity);
snapshot.ifPresent(value -> {
    boolean active = CreateAddonApi.isLikelyActive(value, 0.01D);
    int idleInterval = CreateAddonApi.recommendedIdleInterval(value, 2, 20);
});
```

## Command helpers

- `/r15lib active`
- `/r15lib active profile <name>`
- `/r15lib active estimate <profile> <activeTicks> <idleTicks>`
- `/r15lib active interval <profile> <idleCycles>`
- `/r15lib create activity [minSpeed]`
