# 晶洞生成器

晶洞生成器的类型为`tiths-crystal-cave`, 用于在世界中生成表面带有矿物的椭球型空洞.  
晶洞生成器将使用 MMD Ore Spawn 提供的替换检测器检测寻找一片可以用于生成的空间, 将其中的方块替换为空气或矿物. 如果您不希望晶洞生成器的表面全部生成"矿石"方块, 请在`blocks`内提供一定量的石头或其它方块.

```json
"common_crystal_cave": {
	"retrogen": false,
	"enabled": true,
	"feature": "tiths-crystal-cave",
	"replaces": "default",
	"dimensions": [],
	"biomes": {
		"excludes": []
	},
	"parameters": {
		"minHeight": 20,
		"maxHeight": 80,
		"frequency": 0.035,
		"radiusXZ": 8,
		"radiusY": 10,
		"factorSplit": 0.85,
		"rateOrePillar": 0.55,
		"topDown": 4,
		"bottomUp": 4,
		"maxWaterDepth": 3,
	},
	"blocks": [
		{ "name": "minecraft:stone", "chance": 70 },
		{ "name": "minecraft:block_gold", "chance": 30 }
	]
},
```

晶洞生成器接收如下参数:

* minHeight - 最低生成Y坐标
* maxHeight - 最高生成Y坐标
* frequency - (小数) 每区块的触发频数或频率
* raduisXZ - (整数) 横向半径 5~20
* radiusY - (整数) 纵向半径 5~20
* factorSplit - (小数) 分割因子, 当某坐标的偏移因子 (factorOffset) 小于分割因子时生成空气, 否则生成矿物
* rateOrePillar - (小数) 顶部或底部某坐标随机出现岩柱的几率
* topDown - (整数) 顶部向下延申的岩柱最大高度
* bottomUp - (整数), 底部向上延申的岩柱最大高度
* maxWaterDepth - (整数) 最大积水深度

椭球公式为

$$\frac{x^2}{a^2} + \frac{y^2}{b^2} + \frac{z^2}{c^2} = 1$$

坐标偏移因子的计算公式为

$$factorOffset = \frac{offsetX^2}{raduisXZ^2} + \frac{offsetZ^2}{raduisXZ^2} + \frac{offsetY^2}{radiusY^2}$$

其中, offsetX, offsetY, offsetZ 分别为某坐标距离椭球中心的偏移量
