# [TITHS] 匠魂扩增 Tinkers' Things

一个匠魂的附属模组. An addon of Tinkers' Construct.

* 变更日志 change log:  
  [changelog.md](changelog.md)
* 贡献者列表 contributors:  
  [contributors.md](contributors.md)
* 如何自定义材料属性  
  how to customize material stats:  
  [customize_materials.md](customize_materials.md)
* 如何自定义世界生成
  how to customize world generations:  
  [MMD Ore Spawn GitHub Wiki](https://github.com/MinecraftModDevelopmentMods/OreSpawn/wiki)

> 0.3.25.0版本之后的世界生成功能基于**MMD Ore Spawn**,  
> 如果需要自定义世界生成, 请查阅此模组提供的文档  
> (原文档为英文)
  
> **MMD Ore Spawn**会在首次启动时, 将模组文件内置的世界生成配置输出至`.minecraft/config/orespawn/`目录  
> 此后, 当模组内世界生成配置文件发生变化时 (这常发生在更新模组版本时) 并**不会**再次导出  
> 大部分情况下, 您可以通过删除`.minecraft/config/orespawn/`目录然后重启游戏以触发重新导出

> All world generations depend on **MMD Ore Spawn** after 0.3.25.0  
> If you want to customize generations, please check the docs provided by this mod

> **MMD Ore Spawn** will export the generation-configs to `.minecraft/config/orespawn/`  
> and it **will not** re-export unless deleting those files manully
> When updating this or some other mods, you may need to delete those files and make **MMD Ore Spawn** export configs again, or the new generations (if there is some) **may not** take effect

## 最近更新 latest update

### 0.3.25.1 _2021-02-22 16:00_

* 修改部分材质 modified some textures

### 0.3.25.0 _2021-02-19 14:30_

* 现在世界生成依赖于[MMD Ore Spawn](https://github.com/MinecraftModDevelopmentMods/OreSpawn)  
  now world generations depend on [MMD Ore Spawn](https://github.com/MinecraftModDevelopmentMods/OreSpawn)

## 版本间差异 differences between versions

版本|致命问题|一般问题|游戏性问题|用于服务器|用于个人
-|-|-|-|-|-
alpha|无已知|包含|包含|不推荐|仅预览
beta|无已知|无已知|包含|不推荐|可用于生存模式

* **致命问题** 会导致游戏崩溃等
* **一般问题** 效果数值错误等
* **游戏性问题** 贴图缺失; 平衡性问题等

> "无已知" 表示 "没有已知的问题", 但是"可能含有潜在的问题"

> beta版本虽然可用于个人用途的生存模式, 但是内含的内容本身并不确定, 其中的内容在将来的更新中可能会出现增、删、改

<br>

Version|Fatal problem|General problem|Gameplay problem|For server use|For personal use
-|-|-|-|-|-
alpha|No known|Included|Included|Not recommended|Preview only
beta|No known|No known|Included|Not recommended|Can be used for survival mode

* **Fatal problem** will cause the game to crash, etc.
* **General problem** Wrong values, etc.
* **Gameplay problem** Textures missing, balance issues, etc.

> "No known" means "no known problems", but "may contain potential problems"

> Although the beta version can be used for personal use survival mode, the content itself is not sure, and the content may be added, deleted, or changed in future updates
