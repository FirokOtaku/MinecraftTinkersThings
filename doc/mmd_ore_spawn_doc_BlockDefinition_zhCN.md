# 方块定义方式

您可以通过调整方块定义, 在一个生成器内定义生成多种方块/使用矿物辞典/指定方块数据.

一份基础 (但不完整) 的例子如下:

```json
{ "name": "minecraft:wool", "metaData": 0, "chance": 100 }
{ "name": "chisel:limestone2", "state": "variant=7", "chance": 100 }
{ "name": "ore:silver_ore", "chance": 100" }
```

上面的3个例子分别代表:  

1. Minecraft原版的白色羊毛;
2. 来自模组"凿子"的石灰石方块变种之一;
3. 使用矿物辞典指定的银矿石.

此外, 您可以为每一条方块定义指定一个生成几率.

"metaData"字段支持Minecraft 1.12及更低版本,  
但metadata系统已经在Minecraft 1.13中完全移除.

<details>
<summary>原文</summary>
To allow for multiple blocks in a single spawn, use of the OreDictionary and the sheer desire to have data properly encapsulated it was decided to have a simple json-object format defined for providing the data needed for MMD OreSpawn to be able to get a copy of the block instance and place it in the world.

A basic (but complete) sample of the format is:

```text
(json内容)
```

In the three examples above we can see all the options demonstrated - for the first, classic white wool, for the second, one of the variants of limestone from the mod "chisel" and lastly a use of the OreDictionary. Truthfully the only part of the block definition that needs to exist is the name, though it is a good habit to include the "chance" that defines a raw probability that any given block in a block-list (which is what the "blocks" part of a spawn entry is) will be selected.

The "metaData" member will remain supported for Minecraft 1.12 and prior, but with the removal of metadata from the game entirely in 1.13 it is impossible to retain.
</details> <br>