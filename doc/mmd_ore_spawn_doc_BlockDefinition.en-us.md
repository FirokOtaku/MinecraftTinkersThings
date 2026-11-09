# Block Definition Format

To allow for multiple blocks in a single spawn, use of the OreDictionary and the sheer desire to have data properly encapsulated, it was decided to have a simple json-object format defined for providing the data needed for MMD OreSpawn to be able to get a copy of the block instance and place it in the world.

A basic (but not complete) sample of the format is:

```json
{ "name": "minecraft:wool", "metaData": 0, "chance": 100 }
{ "name": "chisel:limestone2", "state": "variant=7", "chance": 100 }
{ "name": "ore:silver_ore", "chance": 100 }
```

In the three examples above we can see all the options demonstrated — for the first, classic white wool; for the second, one of the variants of limestone from the mod "chisel"; and lastly a use of the OreDictionary.

Truthfully the only part of the block definition that needs to exist is the `name`, though it is a good habit to include the `chance` that defines a raw probability that any given block in a block-list (which is what the `blocks` part of a spawn entry is) will be selected.

The `metaData` member will remain supported for Minecraft 1.12 and prior, but with the removal of metadata from the game entirely in 1.13 it is impossible to retain.

## Note (Tinkers' Things)

* In an actual config file these three objects are elements of the `blocks` array of a spawn entry, separated by commas; they are written on separate lines above only to show the three forms side by side.
* `state` is the block-state string used from Minecraft 1.13 onwards; it replaces `metaData`.
