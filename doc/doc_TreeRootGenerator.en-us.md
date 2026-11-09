# Tree Root Generator

The tree root generator has the type `tiths-tree-root` and generates ores below tree trunks.

```json
"common_tree_root": {
	"retrogen": false,
	"enabled": true,
	"feature": "tiths-tree-root",
	"replaces": "default",
	"dimensions": [],
	"biomes": {
		"excludes": []
	},
	"parameters": {
		"frequency": 0.08,
        "depthBase": 3,
        "depthExtra": 3
	},
	"blocks": [
		{ "name": "tiths:tree_root", "chance": 100 }
	]
},
```

The tree root generator accepts the following parameters:

* frequency - (decimal) trigger frequency per chunk
* depthBase - base generation depth
* depthExtra - extra randomly added depth
