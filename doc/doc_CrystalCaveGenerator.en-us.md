# Crystal Cave Generator

The crystal cave generator has the type `tiths-crystal-cave` and generates ellipsoid-shaped cavities whose surface contains ores in the world.  
The crystal cave generator uses the replacement detector provided by MMD Ore Spawn to find a space suitable for generation, and replaces the blocks inside it with air or ores. If you do not want the whole surface of the crystal cave to be made of "ore" blocks, please provide some stone or other blocks in `blocks`.

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

The crystal cave generator accepts the following parameters:

* minHeight - (integer) lowest Y coordinate where the generator can generate
* maxHeight - (integer) highest Y coordinate where the generator can generate
* frequency - (decimal) trigger count or frequency per chunk
* radiusXZ - (integer) horizontal radius, 5~20
* radiusY - (integer) vertical radius, 5~20
* factorSplit - (decimal) split factor; air is generated when the offset factor (factorOffset) of a coordinate is smaller than the split factor, otherwise an ore is generated
* rateOrePillar - (decimal) chance that an ore pillar randomly appears at a coordinate on the top or bottom
* topDown - (integer) maximum height of the ore pillar extending downward from the top, 0~8
* bottomUp - (integer), maximum height of the ore pillar extending upward from the bottom, 0~8
* maxWaterDepth - (integer) maximum water depth, 0~5

The ellipsoid formula is

$$\frac{x^2}{a^2} + \frac{y^2}{b^2} + \frac{z^2}{c^2} = 1$$

The formula for the coordinate offset factor is

$$factorOffset = \frac{offsetX^2}{radiusXZ^2} + \frac{offsetZ^2}{radiusXZ^2} + \frac{offsetY^2}{radiusY^2}$$

where offsetX, offsetY and offsetZ are the offsets of a coordinate from the center of the ellipsoid respectively
