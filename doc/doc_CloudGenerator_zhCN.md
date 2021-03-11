# 云层生成器

云层生成器的类型为`tiths-cloud`, 用于在世界中生成带有矿物的云层. 云层生成器将检查并替换空气方块.

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

云层生成器接收如下参数:

* minHeight - 最低生成Y坐标
* maxHeight - 最高生成Y坐标
* frequency - (小数) 每区块的触发频率
* raduisX - (整数) 横向X轴长度 5~15
* variationX - (整数) X轴长度变量 0~5
* radiusY - (整数) 纵向Y轴高度 5~15
* variationY - (整数) Y轴长度变量 0~5
* radiusZ - (整数) 横向Z轴长度 5~15
* variationZ - (整数) Z轴长度变量 0~5
