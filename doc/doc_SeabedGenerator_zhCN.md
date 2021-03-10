# 海床生成器

海床生成器的类型为`tiths-seabed`, 用于在世界中寻找水方块并替换为矿物方块.

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

海床生成器接收如下参数:

* frequency - (小数) 每区块的触发频率
