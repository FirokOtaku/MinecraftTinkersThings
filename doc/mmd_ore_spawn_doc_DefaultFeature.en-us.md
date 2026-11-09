# Default Feature

The feature generator named `default` attempts to mimic, to a degree, standard ore generation by Minecraft. A sample spawn using it, taken from the example included with MMD OreSpawn 3, follows:

```json
"coal_ore": {
	"retrogen": false,
	"enabled": true,
	"feature": "default",
	"replaces": "default",
	"dimensions": [],
	"biomes": {
		"excludes": []
	},
	"parameters": {
		"size": 25,
		"variation": 12,
		"frequency": 20,
		"minHeight": 0,
		"maxHeight": 128
	},
	"blocks": [
		{
			"name": "minecraft:coal_ore",
			"chance": 100
		}
	]
},
```

As can be seen, this spawns coal ore. The parameters are, as follows:

* size - Average size of the spawn, as modified by the variation parameter
* variation - The spawn will be anywhere from `size - variation` to `size + variation`
* frequency - How often this spawn will occur. For this generator this can either be an integer (in this case 20) or a floating point value. The integer form says "try to find a starting point this many times", the floating point form is a percentage chance.
* minHeight - The minimum Y-level where a spawn may begin or even exist
* maxHeight - The maximum Y-level where a spawn may begin or exist

## Note (Tinkers' Things)

* `"dimensions": []` makes the spawn apply to every dimension. To restrict it, use e.g. `"dimensions": [ -1 ]` (Nether only) or `"dimensions": { "includes": [ 1 ] }` (End only).
* `"biomes": { "excludes": [] }` excludes no biome; you can also use `"includes"` to list the biomes the spawn is limited to.
