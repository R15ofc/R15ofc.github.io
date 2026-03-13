# Create Toolkit

## Kinetic survey

Use API:

```java
CreateKineticSurvey survey = CreateAddonApi.survey(serverLevel, centerPos, 8);
```

Useful values:

- `survey.kineticBlockEntities()`
- `survey.totalImpact()`
- `survey.totalCapacity()`
- `survey.usageRatio()`
- `survey.worstTier()`

## Activity heuristics

```java
Optional<CreateKineticSnapshot> snapshot = CreateAddonApi.snapshot(blockEntity);
snapshot.ifPresent(value -> {
    boolean active = CreateAddonApi.isLikelyActive(value, 0.01D);
    double score = CreateAddonApi.activityScore(value);
    int recommendedIdle = CreateAddonApi.recommendedIdleInterval(value, 2, 20);
});
```

## Stress planning

```java
int machines = CreateAddonApi.maxMachines(capacity, machineImpact);
boolean unsafe = CreateAddonApi.wouldOverstress(currentImpact, capacity, addedImpact);
```

## Machine profiles

Built-in profiles are registered on bootstrap.

You can add your own:

```java
CreateAddonApi.registerProfile(new CreateMachineProfile(
    "myaddon:crusher",
    12.0,
    96.0,
    "Heavy crushing machine"
));
```

And query recommendation:

```java
int count = CreateAddonApi.recommendedCount("myaddon:crusher", availableCapacity);
```

## In-game commands

- `/r15lib create survey [radius]`
- `/r15lib create plan <capacity> <machineImpact>`
- `/r15lib create profile list`
- `/r15lib create profile calc <profile> <capacity>`
- `/r15lib create activity [minSpeed]`
