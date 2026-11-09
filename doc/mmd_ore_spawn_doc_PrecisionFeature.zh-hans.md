# 精准生成器

精准生成器的类型为`precision`, 它会尝试在每个区块生成指定数量 1% 左右的矿物. 下面是一个例子:

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

如上例所示, 它会在每个区块尝试生成 2 次, 每次生成 3 个钻石矿石.

相关参数如下:

* size - 每种需要生成的方块的数量
* numObjects - 每区块尝试生成多少次
* minHeight - 最低生成Y坐标
* maxHeight - 最高生成Y坐标
