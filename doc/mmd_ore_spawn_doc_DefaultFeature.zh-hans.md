# 默认生成器

默认生成器的类型为`default`, 它的生成效果与 Minecraft 原版的矿物生成类似.

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
* frequency - 每区块的触发频数或频率. 可以是整数或小数. 整数 (示例中为 20) 表示每区块尝试寻找生成点多少次; 小数表示每区块平均触发频率
* minHeight - 最低生成 Y 坐标
* maxHeight - 最高生成 Y 坐标

> **译注**
> 示例中的`"dimensions": []`表示该生成项在所有维度中生效;  
> 若要限定维度, 可以写成`"dimensions": [ -1 ]` (仅下界) 或`"dimensions": { "includes": [ 1 ] }` (仅末地)  
> `"biomes": { "excludes": [] }`表示不排除任何生物群系,  
> 也可以使用`"includes"`来指定只在这些生物群系中生成
