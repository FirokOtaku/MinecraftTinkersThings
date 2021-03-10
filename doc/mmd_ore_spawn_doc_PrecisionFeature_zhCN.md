# 精准生成器

精准生成器的类型为`precision`, 它会尝试在每个区块生成指定数量1%左右的矿物. 下面是一个例子:

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

相关参数如下:

* size - 每种需要生成的方块的数量
* numObjects - 每区块尝试生成多少次
* minHeight - 最低生成Y坐标
* maxHeight - 最高生成Y坐标

<details>
<summary>原文</summary>
The precision generator attempts to create a spawn or spawns within every chunk it is called for that is within 1% of the total specified size for the spawn. The definitive example follows:

```text
(json内容)
```

As this shows, it attempts to create 2 spawns of 3 diamond ore each in every chunk.

The parameters are, as follows:

* size - The size of each spawned "node" of the specified block or blocks
* numObjects - How many spawns to try and place per chunk
* minHeight - Minimum height for a spawn
* maxHeight - Maximum height for a spawn
</details>
