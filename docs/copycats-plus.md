# Create: Copycats Plus Toolkit

## What is included

- Compat detection with multiple candidate mod ids.
- Copycat block/block-entity detection helpers.
- Material reflection probes (`getMaterial`-style methods).
- Area survey service returning:
  - scanned block count
  - copycat block count
  - copycat block-entity count
  - materialized entry count
  - dominant material id

## API

- `CopycatsApi.isLoaded()`
- `CopycatsApi.isCopycat(state)`
- `CopycatsApi.isCopycat(blockEntity)`
- `CopycatsApi.material(blockEntity)`
- `CopycatsApi.survey(level, center, radius)`

## Commands

- `/r15lib copycats`
- `/r15lib copycats inspect`
- `/r15lib copycats survey [radius]`

## Config

- `enableCopycatsReflection`
- `maxCopycatsSurveyRadius`
