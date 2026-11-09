# Precision Feature

The precision generator attempts to create a spawn or spawns within every chunk it is called for that is within 1% of the total specified size for the spawn. The definitive example follows:

```json
"diamond_ore": {
	"retrogen": false,
	"enabled": true,
	"feature": "precision",
	"replaces": "default",
	"dimensions": [],
	"biomes": {
		"excludes": []
	},
	"parameters": {
		"size": 3,
		"numObjects": 2,
		"minHeight": 0,
		"maxHeight": 16
	},
	"blocks": [
		{
			"name": "minecraft:diamond_ore",
			"chance": 100
		}
	]
},
```

As this shows, it attempts to create 2 spawns of 3 diamond ore each in every chunk.

The parameters are, as follows:

* size - The size of each spawned "node" of the specified block or blocks
* numObjects - How many spawns to try and place per chunk
* minHeight - Minimum height for a spawn
* maxHeight - Maximum height for a spawn
