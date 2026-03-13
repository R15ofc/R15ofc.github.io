# Animation API

## Registering clip in code

```java
AnimationClip clip = AnimationClip.builder("myaddon:drill_pose", 20.0F)
    .looping(true)
    .track(new TimelineTrack("right_arm", List.of(
        new Keyframe(0.0F, Transform.IDENTITY, Easing.LINEAR),
        new Keyframe(10.0F, new Transform(Float3.ZERO, new Float3(-0.5F, 0.0F, 0.0F), Float3.ONE), Easing.EASE_IN_OUT),
        new Keyframe(20.0F, Transform.IDENTITY, Easing.EASE_IN_OUT)
    )))
    .build();

LibraryApi.registerAnimation(new ResourceLocation("myaddon", "drill_pose"), clip);
```

## Playing on server

```java
LibraryApi.playAnimation(serverPlayer, new ResourceLocation("myaddon", "drill_pose"), 1.0F, true);
```

## Triggering from client

```java
LibraryApi.requestSelfAnimation(new ResourceLocation("myaddon", "drill_pose"), 1.0F, true);
```

This sends a client->server packet and is controlled by config (`allowClientAnimationTriggerPacket`).

## Data-driven clips

Drop JSON into `data/<namespace>/r15lib_animations/<name>.json`.

Example shape:

```json
{
  "length": 12.0,
  "looping": false,
  "tracks": {
    "right_arm": [
      { "tick": 0.0, "rotation": [0.0, 0.0, 0.0], "easing": "LINEAR" },
      { "tick": 4.0, "rotation": [-0.95, 0.1, 0.0], "easing": "EASE_OUT" },
      { "tick": 12.0, "rotation": [0.0, 0.0, 0.0], "easing": "EASE_IN_OUT" }
    ]
  }
}
```

## Runtime testing commands

- `/r15lib play <targets> <clip> [looping] [speed]`
- `/r15lib stop <targets>`
- `/r15lib clips`
