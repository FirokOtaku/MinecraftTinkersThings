# 岩脉生成器

## 概述

岩脉生成器的类型为`vein`, 它会尝试生成一条蜿蜒绵长的"矿脉".

首先, 它会在区块中指定一个起点和一个方向, 然后在指定节点附近生成指定数量的矿物方块, 作为"一步";  
每生成一步, 就会随机指定一个新的方向, 来模拟矿脉的"行走".

目前这个生成器没有典型例子, 下面只列出了这个生成器接收哪些参数:

### 参数

* minHeight - 最低生成Y坐标
* maxHeight - 最高生成Y坐标
* variation - 每一步生成矿物量和矿脉长度变量
* frequency - 生成频率
* minAttempts - 最小尝试生成次数
* maxAttempts - 最大尝试生成次数
* length - 矿脉长度
* startFacing - (字符串) 起始生成方向
  * `UP`, `DOWN`, `NORTH`, `SOUTH`, `EAST`, `WEST` - 指定方向
  * `RANDOM` - 随机朝向
  * `VERTICAL` - 随机竖直方向
  * `HORIZONTAL` - 随机水平方向
* size - 每一步生成多少矿物方块
