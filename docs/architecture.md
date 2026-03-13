# Architecture

## Modules

- `common`: pure Java systems and reusable primitives.
- `forge-shared`: Forge-facing API used by Forge lines.
- `forge-1_20_1`: Forge runtime target for MC 1.20.1.
- `forge-1_20_6`: Forge runtime target for MC 1.20.6.
- `neoforge-1_21_1`: NeoForge runtime scaffold for MC 1.21.1.
- `fabric-1_20_1`: Fabric runtime scaffold for MC 1.20.1.

## Core capabilities

- Animation runtime:
  - static + data-driven clip registration
  - synchronized playback and transition blending
- Create ecosystem:
  - Create line/version detection
  - stress/speed/overstress reflection probes
  - kinetic survey and planning math
  - machine profile model for balancing
- Copycats+:
  - compat detection
  - material extraction hooks
  - area surveys for copycat-heavy builds
- CC:Tweaked:
  - reflective dynamic peripheral bridge
  - annotation-driven peripheral method exposure
  - auto capability attach from a registry
- Runtime infrastructure:
  - server/client schedulers
  - global state counters/flags
  - debug metrics and command surfaces
  - inventory/NBT/player utility helpers

## Design direction

- Keep shared logic in `common` and toolkit layers.
- Isolate platform-specific bootstraps in target modules.
- Grow NeoForge/Fabric parity incrementally while preserving stable high-level APIs.
