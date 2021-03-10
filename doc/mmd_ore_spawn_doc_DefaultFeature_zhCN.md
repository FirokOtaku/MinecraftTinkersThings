# 默认生成器

默认生成器的类型为`default`, 它的生成效果与Minecraft原版的矿物生成类似.

下面是一份示例配置内容

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

在这个例子中, 相关生成的矿物为煤矿石. 其中的若干参数为:

* size - 生成矿物方块的平均数量, 可以由`variation`参数调整范围
* variation - 生成矿物方块的数量会介于`size - variation`到`size + variation`之间
* frequency - 每区块的触发频数或频率. 可以是整数或小数. 整数 (示例中为20) 表示每区块尝试寻找生成点多少次; 小数表示每区块平均触发频率
* minHeight - 最低生成Y坐标
* maxHeight - 最高生成Y坐标

<details>
<summary>原文</summary>
The feature generator named default attempts to mimic, to a degree, standard ore generation by Minecraft. A sample spawn using it, taken from the example included with MMD OreSpawn 3 follows:

```text
(json内容)
```

As can be seen, this spawns coal ore. The parameters are, as follows:

* size - Average size of the spawn, as modified by the variation parameter
* variation - The spawn will be anywhere from size - variation to size + variation
* frequency - How often this spawn will occur. For this generator this can either be an integer (in this case 20) or a floating point value. The integer form says "try to find a starting point this many times", the floating point form is a percentage chance.
* minHeight - The minimum Y-level where a spawn may begin or even exist
* maxHeight - The maximum Y-level where a spawn may begin or exist
</details>
