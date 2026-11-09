# Seabed Generator

The seabed generator has the type `tiths-seabed` and finds water blocks in the world and replaces them with ore blocks.

```json
"common_seabed": {
	"retrogen": false,
	"enabled": true,
	"feature": "tiths-seabed",
	"replaces": "default",
	"dimensions": [],
	"biomes": {
		"excludes": []
	},
	"parameters": {
		"frequency": 0.08,
	},
	"blocks": [
		{ "name": "tiths:brume_jade", "chance": 100 }
	]
},
```

The seabed generator accepts the following parameters:

* frequency - (decimal) trigger frequency per chunk
