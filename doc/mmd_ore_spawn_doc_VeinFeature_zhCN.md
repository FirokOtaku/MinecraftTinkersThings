# 岩脉生成器

岩脉生成器的类型为`vein`, 它会尝试生成一条蜿蜒绵长的"矿脉".

首先, 它会在区块中指定一个起点和一个方向, 然后在指定节点附近生成指定数量的矿物方块, 作为"一步";  
每生成一步, 就会随机指定一个新的方向, 来模拟矿脉的"行走".

目前这个生成器没有典型例子, 下面只列出了这个生成器接收哪些参数:

* minHeight - 最低生成Y坐标
* maxHeight - 最高生成Y坐标
* variation - 每一步生成矿物量和矿脉长度变量
* frequency - how frequently the feature will be generated
* minAttempts - 最小尝试生成次数
* maxAttempts - 最大尝试生成次数
* length - 矿脉长度
* startFacing - (字符串) 起始生成方向
  * `UP`, `DOWN`, `NORTH`, `SOUTH`, `EAST`, `WEST` - 指定方向
  * `RANDOM` - 随机朝向
  * `VERTICAL` - 随机竖直方向
  * `HORIZONTAL` - 随机水平方向
* size - 每一步生成多少矿物方块

<details>
<summary>原文</summary>
The vein feature generator attempts to spawn a long, snaking "vein" of an ore in the world. It does this by choosing a starting point in a chunk, picking a direction to go and "simulating" a walk for the length of the vein, at each step deciding on a somewhat random new direction to move in. It then goes back and at each of those single block steps tries to generate a small node of the specified block(s).

As no canonical example of this generator exists, here are the parameters it takes and an explanation of them:

* minHeight - the minimum Y level a vein can spawn at or reach
* maxHeight - the maximum Y level a vein can spawn at or reach
* variation - the amount of variation in the size of the "node" generated at each "step" of the vein as well as the difference in length of the vein
* frequency - how frequently the feature will be generated
* minAttempts - minimum number of times to try and generate this feature
* maxAttempts - maximum number of times to try and generate this feature
* length - mean length of any given vein
* startFacing - which direction will the vein prefer to head in on spawn ? ("UP", "DOWN", "NORTH", "SOUTH", "EAST", "WEST", "RANDOM" (randomly choose), "VERTICAL" (choose either up or down randomly), "HORIZONTAL" (choose any but "UP" or "DOWN" randomly)
* size - how many blocks will spawn as a "node" of the vein at each "step" along its length ?
</details>