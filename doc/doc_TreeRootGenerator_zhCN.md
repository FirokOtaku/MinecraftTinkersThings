# 树根生成器

树根生成器的类型为`tiths-tree-root`, 用于在树干下方生成矿物.

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

海床生成器接收如下参数:

* frequency - (小数) 每区块的触发频率
* depthBase - 基础生成深度
* depthExtra - 额外随机增加深度
