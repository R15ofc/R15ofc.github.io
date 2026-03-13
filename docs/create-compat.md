# Create Compat

`CreateCompatService` auto-detects Create and exposes:

- `installed()`
- `line()` -> `V0_5` / `V0_6` / `UNKNOWN`
- `version()`
- `isKineticBlockEntity(BlockEntity)`
- reflection reads:
  - `readStressImpact(...)`
  - `readStressCapacity(...)`
  - `readSpeed(...)`
  - `isOverStressed(...)`

## API usage

`CreateAddonApi.snapshot(blockEntity)` returns a `CreateKineticSnapshot` with:

- impact
- capacity
- usage ratio
- speed
- overstressed
- stress tier (`IDLE`, `LIGHT`, `MEDIUM`, `HEAVY`, `CRITICAL`)

`CreateAddonApi.survey(level, center, radius)` aggregates a local kinetic network area.

## Commands

- `/r15lib create`
- `/r15lib create inspect`
- `/r15lib create survey [radius]`
- `/r15lib create plan <capacity> <machineImpact>`
- `/r15lib create profile list`
- `/r15lib create profile calc <profile> <capacity>`

## Notes

Reflection probes are best-effort because Create internals differ by build.
Guard addon behavior around optional values.
