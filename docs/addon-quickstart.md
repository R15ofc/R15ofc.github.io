# Addon Quickstart (Use R15 Library)

## 1. Add dependency

In your addon mod, depend on R15 Library and make it required at runtime.

## 2. Use active-tick gate in your machine

```java
private final ActiveTickController gate =
    ActiveTickApi.controller(ActiveTickProfiles.byName("create_kinetic").orElse(ActiveTickProfiles.balanced()));

public static void tick(ServerLevel level, BlockPos pos, MyMachineBlockEntity be) {
    long gameTick = level.getGameTime();
    boolean hasWork = be.hasPendingWork();
    boolean redstone = ActiveTickApi.hasNeighborSignal(level, pos);
    boolean playerNearby = ActiveTickApi.hasPlayersNearby(level, pos, 10.0D);

    if (!gate.shouldTick(gameTick, hasWork || redstone, playerNearby)) {
        return;
    }

    be.serverTickBody();
}
```

## 3. Create-specific heuristic

```java
Optional<CreateKineticSnapshot> snapshot = CreateAddonApi.snapshot(be);
boolean createActive = snapshot.map(s -> CreateAddonApi.isLikelyActive(s, 0.01D)).orElse(false);
```

## 4. Where to store `always_tick_create`

Best practice: keep this flag in your addon mod config.
Use library config (`alwaysTickCreate`) only as a global fallback/shared toggle.
