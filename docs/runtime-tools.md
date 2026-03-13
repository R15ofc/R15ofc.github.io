# Runtime Tools

## Scheduler

- `ServerTaskScheduler.schedule(owner, delayTicks, task)`
- `ClientTaskScheduler.schedule(delayTicks, task)`

## Active-tick toolkit

- `ActiveTickController`
- `ActiveTickProfile` / `ActiveTickProfiles`
- `ActiveTickMath`
- `ActiveTickApi.hasNeighborSignal(...)`
- `ActiveTickApi.hasPlayersNearby(...)`

Commands:

- `/r15lib active`
- `/r15lib active profile <name>`
- `/r15lib active estimate <profile> <activeTicks> <idleTicks>`
- `/r15lib active interval <profile> <idleCycles>`

Create policy fallback config (Forge common config):

- `compat.alwaysTickCreate=false` by default

## Player cooldown API

- `LibraryApi.setCooldown(player, key, ticks)`
- `LibraryApi.isCooldownReady(player, key)`
- `LibraryApi.cooldown(player, key)`

## Runtime state

- `LibraryApi.setGlobalCounter(...)`
- `LibraryApi.addGlobalCounter(...)`
- `LibraryApi.globalCounter(...)`
- `LibraryApi.setGlobalFlag(...)`
- `LibraryApi.globalFlag(...)`

Commands:

- `/r15lib state counter get/set/add ...`
- `/r15lib state flag get/set ...`

## Registry/world helpers

- `RegistryKit.register(...)`
- `RegistryKit.registerBlockWithItem(...)`
- `WorldSearch.collectBlockEntities(...)`

## Inventory and NBT

- `ItemHandlerUtil.*`
- `NbtDataUtil.*`

## Player messaging

- `PlayerMessenger.chat(...)`
- `PlayerMessenger.actionBar(...)`
- `PlayerMessenger.chatAll(...)`

## CC:Tweaked toolkit

- `CcTweakedApi.registerPeripheral(...)`
- `CcTweakedApi.registerPeripheralFromOwnerClass(...)`
- `@LuaPeripheralMethod`

Commands:

- `/r15lib cc`
- `/r15lib cc bindings`
- `/r15lib cc methods <binding-class>`

## Debug stats

`LibraryDebugStats` tracks packet, animation, scheduler, Create, Copycats and CC bridge counters.

Commands:

- `/r15lib debug stats`
- `/r15lib debug tasks`
