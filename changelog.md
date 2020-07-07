# 变更日志 Changelog

> All Time Stamps: GMT+8

## 0.3.9.0 _2020-07-08 00:30_

* 增加物品 added items
  * 地平腰带 leveling belt item
* 移除方块和物品 removed blocks and items
  * 秘银矿 mithril ore block
  * 精金矿 adamantine ore block
* 重命名材料 renamed materials
  * 精金 -> 伽钢 adamantine -> magiga
  * 秘银 -> 坚金 mithril -> solita
* 增加特性 added traits
  * 洞藏(护甲) cavious(armor)
  * 金乌坠落(投射物) stellar falling(projectile)
  * 崩裂(投射物) cracking(projectile)
* 调整特性 modified traits
  * 生命激发和生命激发(护甲)现在变为消耗经验 life inspiring and life inspiring(armor) now cost exp

## 0.3.8.0 _2020-07-04 12:00_

* 增加物品 added items
  * 荧光凝胶 fluorescent gel
* 增加特性 added traits
  * 透蚀 infiltrating
* 错误修复 bug fixes
  * 现在可以正常在游戏内调整配置项 now configurations can be modified correctly

## 0.3.7.0 _2020-07-03 11:30_

* 增加物品 added items
  * 回溯纹章 lapsing charm item
  * 叶绿提灯 chloroplast light item

## 0.3.6.0 _2020-06-23 01:00_

* 增加方块和物品 added blocks and items
  * 氧气面罩 oxygen mask item
  * 浮漂 buoy block
  * 浮灯 buoyant light block
  * 通道 channel
  * 沉重腰带 heavy belt
  * 冻灯 ice light
  * 液体收集器 fluid collector
  * 液体球 fluid ball
  * 云砖 cloud brick block
* 移除方块和物品 removed blocks and items
  * 木梯管道料包 channel pack wood
  * 石梯管道料包 channel pack stone
* 增加液体 added fluids
  * 闪光凝胶 shining gel
* 增加特性 added traits
  * 缭绕(护甲) lingering(armor)
  * 绿意 green
* 增加状态效果 added potion effects
  * 云雾 bruming
* 材料重命名 renamed materials
  * 东陵石 -> 云玉 aventurine -> brume jade
* 错误修复 bug fixes
  * 部分特性不再会对无视护甲的伤害做出反应 now some of armor traits will not react to damage sources which bypass armor
  * 不再使用直接反射读写MC内部类字段 we no longer use direct reflection to read or write values of classes of Minecraft

## 0.3.5.5 _2020-06-14 17:00_

* 调整矿物世界生成 modified ore generations
* 修改部分材质 modified some textures
* 增加方块和物品 added blocks and items
  * 远洋腰带 belt oceanic item
  * 灯明冻玉矿 icelit ore block
  * 灯明冻玉 icelit item
  * 灯明冻玉块 icelit block
  * 深流钢块 decurrium block
  * 深流钢锭 decurrium ingot item
  * 深流钢粒 decurrium nugget item
* 增加液体 added fluids
  * 熔融深流钢 molten decurrium
* 增加材料 added materials
  * 深流钢 decurrium
  * 灯明冻玉 icelit
* 增加特性 added traits
  * 尊重 respecting
  * 蔽叶(护甲) leaves hiding(armor)
  * 浪涌(护甲) surging(armor)
  * 灯明 ablaze
  * 速冻(护甲) quick freezing(armor)
* 增加状态效果 added potion effects
  * 蔽叶 leaves hiding
  * 灯明 ablaze

## 0.3.4.0 _2020-06-09 12:30_

* 增加方块和物品 added blocks and items
  * 塔拉特妮姆尘埃 tanatonium dust block
  * 塔拉特妮姆粒 tanatonium nugget item
  * 塔拉特妮姆锭 tanatonium ingot item
  * 塔拉特妮姆块 tanatonium block
  * 拟素矿 imitatium ore block
  * 拟素粒 imitatium nugget item
  * 拟素锭 imitatium ingot item
  * 拟素块 imitatium block
  * 扰动护符 perturbance amulet
* 增加液体 added fluids
  * 熔融塔拉特妮姆 molten tanatonium
* 增加材料 added materials
  * 塔拉特妮姆 tanatonium
  * 拟素 imitatium
* 增加特性 added traits
  * 侵蚀 eroding
  * 湮灭 annihilating
  * 简并 degenerating
  * 重组 recombining
  * 水生 aquatic
* 增加状态效果 added potion effects
  * 侵蚀 eroded

## 0.3.3.0 _2020-06-08 18:00_

* 现在需要穿戴相关饰品才能看到掉落的灵魂物品 now you need to wear related baubles to see the fallen soul items
* 调整强化 modified modifiers
  * 打孔 drilled
    * 现在每级降低更多耐久上限(-650 -> -850) more durability limitation are reduced per level now(-650 -> -850)
    * 现在不能与耐久、致密、钻石、绿宝石强化共存 cannot coexist with modifiers duritos, dense, diamond, emerald now
* 调整矿物世界生成 modified ore generations
  * 东陵石 @云层 aventurine @cloud
  * 岩浆水晶 @下界(-地狱岩) lava crystal @nether(-netherrack)
  * 丽辰石 @下界(-地狱岩) lizanite @nether(-netherrack)
  * 振晶 @末地(-末地石) vibrating crystal @end(-endstone)
* 增加方块和物品 added blocks and items
  * 虫苔 creep soil block
* 增加特性 added traits
  * 旋流 eddying
* 增加状态效果 added potion effects
  * 旋流 eddying

## 0.3.2.0 _2020-05-31 17:00_

* 现在需要饰品模组作为前置 now we need Baubles as dependence
* 增加灵魂收集和储存系统 added soul gathering and storing system
* 现在部分方块可用于信标基座 now some blocks can be used as beacon base
* 移除方块 removed blocks
  * 焦黑通风孔 searing vent block
* 移除特性 removed traits
  * 石之相变 stone phasing
* 加入方块和物品 added blocks and items
  * 灵魂 soul
  * 灵魂信标 soul beacon
  * 铁护身符 iron amulet
  * 灵魂石护身符 soul stone amulet
  * 无限灵魂石护身符(仅创造模式) infinite soul stone amulet(creative mode only)
  * 皮质腰带 leather belt
  * 石之相变腰带 stone phasing belt
  * 叶绿敷料 chloroplast dressing
  * 石粉 stone dust item
  * 石-煤混合粉末 stone-coal mixed dust item
* 加入特性 added traits
  * 光合作用(护甲) photosynthetic(armor)
* 现在下列特性需要消耗灵魂 now the following traits need to consume soul
  * 生命激发 life inspiring
  * 生命激发(护甲) life inspiring(armor)

## 0.3.1.0 _2020-05-27 22:30_

* 加入材料 added materials
  * 激流水晶 torrential crystal
* 加入特性 added traits
  * 扰动(护甲) turbulent(armor)

## 0.3.0.2 _2020-05-26 17:00_

* 加入方块和物品 added blocks and items
  * 硬实泥土矿 solid dirt ore block
  * 硬实沙矿 solid sand ore block
  * 硬实石头矿 solid stone ore block
  * 彩色玻璃块 colorful glass block
  * 激流推进器 torrential thruster
  * 导管 tube item
  * 一次性空气罐 disposable air tank item
  * 空气罐 air tank item
  * 空气泵 air pump block
  * 空气弹 air bomb item
* 加入特性 added traits
  * 导管升级(护甲) tube upgraded(armor)
  * 激流 torrential
  * 水合(护甲) hydrating(armor)
* 加入状态效果 added potion effects
  * 发泡 bubbling
  * 乱流 turbulent

## 0.3.0.1 _2020-05-20 18:00_

* 重写世界生成 rewrote world generation
* 加入物品 added items
  * 网 mesh item
  * 浮筒 buoy item
  * 激流水晶 torrential crystal item
  * 激流水晶矿 torrential crystal ore block
  * 激流水晶块 torrential crystal block
  * 黑石粉 blackrock dust item
* 加入特性 added traits
  * 筛网(护甲) meshing(armor)
  * 浮力(护甲) buoyant(armor)
  * 君临 sovereign
* 更新材料的渲染信息 updated rendering information of materials
* 修改部分材质 modified some textures

## 0.3.0.0 _2020-05-05 19:00_
