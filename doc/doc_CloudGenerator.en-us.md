# Cloud Generator

The cloud generator has the type `tiths-cloud` and generates cloud layers containing ores in the world. The cloud generator checks for and replaces air blocks.

```json
"common_cloud": {
	"retrogen": false,
	"enabled": true,
	"feature": "tiths-cloud",
	"replaces": "default",
	"dimensions": [],
	"biomes": {
		"excludes": []
	},
	"parameters": {
		"minHeight": 20,
		"maxHeight": 80,
		"frequency": 0.0035,
		"radiusX": 8,
        "radiusZ": 9,
		"radiusY": 10,
	},
	"blocks": [
		{ "name": "tiths:brume_jade", "chance": 5 },
		{ "name": "tiths:cloud", "chance": 95 }
	]
},
```

The cloud generator accepts the following parameters:

* minHeight - (integer) lowest Y coordinate where the generator can generate
* maxHeight - (integer) highest Y coordinate where the generator can generate
* frequency - (decimal) trigger frequency per chunk
* radiusX - (integer) horizontal X axis length, 5~15
* variationX - (integer) X axis length variation, 0~5
* radiusY - (integer) vertical Y axis height, 5~15
* variationY - (integer) Y axis length variation, 0~5
* radiusZ - (integer) horizontal Z axis length, 5~15
* variationZ - (integer) Z axis length variation, 0~5
