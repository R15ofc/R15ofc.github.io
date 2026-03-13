# CC:Tweaked Peripheral Toolkit

## Goal

Make peripheral creation fast without hard compile-time coupling to CC APIs.

## Key pieces

- `@LuaPeripheralMethod` annotation for exposed methods.
- `CcTweakedApi.registerPeripheral(...)` to bind a `BlockEntity` class to a peripheral type.
- Reflection bridge creates an `IDynamicPeripheral` proxy when CC is installed.
- Auto capability attach via Forge event hooks.

## Quick example

```java
public class CrusherPeripheralOwner {
    private final MyCrusherBlockEntity blockEntity;

    public CrusherPeripheralOwner(MyCrusherBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    @LuaPeripheralMethod("get_progress")
    public int getProgress() {
        return blockEntity.getProgress();
    }

    @LuaPeripheralMethod("set_enabled")
    public void setEnabled(boolean enabled) {
        blockEntity.setEnabled(enabled);
    }
}

CcTweakedApi.registerPeripheralFromOwnerClass(
    MyCrusherBlockEntity.class,
    "myaddon_crusher",
    CrusherPeripheralOwner::new,
    CrusherPeripheralOwner.class
);
```

## Commands

- `/r15lib cc`
- `/r15lib cc bindings`
- `/r15lib cc methods <binding-class>`

## Notes

- Bridge is guarded by config (`enableCcPeripheralBridge`).
- Commands are gated by config (`enableCcCommands`).
- If CC internals change, bridge fails safely and logs diagnostics.
