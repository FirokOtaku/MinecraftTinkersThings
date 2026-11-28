# [TITHS] 匠魂扩增 Tinkers' Things

一个匠魂的附属模组.

[English version](readme.en-us.md)

* 变更日志:
  [链接](changelog.zh-hans.md)
* 贡献者列表:
  [链接](doc/contributors.zh-hans.md)
* 如何自定义材料属性:
  [链接](doc/doc_customize_materials.zh-hans.md)
* 如何自定义世界生成:
  [链接](doc/doc_world_generations.zh-hans.md)

> 0.3.25.0版本之后的世界生成功能基于**MMD Ore Spawn**,
> 如果需要自定义世界生成, 请查阅此模组提供的文档
> (原文档为英文)

> 当前版本需要 **MMD OreSpawn 4.1.6.112021** 或更新的 Minecraft 1.12.2 Forge 版本。新版直接加载模组内置的矿物规则。`config/tiths-orespawn.json` 中的整合包配置优先于内置规则。
> 从 OreSpawn 3 升级时，模组会把识别到的旧默认 `config/orespawn3/tiths.json` 移到 `config/orespawn-migration/tiths-os3-default.json`。用户自定义的旧配置由 OreSpawn 迁移。若迁移结果含有 `tiths:legacy/test`，可移除该旧云层规则，雾玉云层现由兼容插件单独生成。
> 新建世界会使用当前规则。已有世界保存了自己的规则，可在备份后调整 `<世界目录>/serverconfig/orespawn-worldgen.json` 中的矿物配置。默认生成仅作用于新生成的区块。

## 最近更新

跳转至 [变更日志](changelog.zh-hans.md)

## 版本间差异

版本|致命问题|一般问题|游戏性问题|用于服务器|用于个人
-|-|-|-|-|-
alpha|无已知|包含|包含|不推荐|仅预览
beta|无已知|无已知|包含|不推荐|可用于生存模式

* **致命问题** 会导致游戏崩溃等
* **一般问题** 效果数值错误等
* **游戏性问题** 贴图缺失; 平衡性问题等

> "无已知" 表示 "没有已知的问题", 但是"可能含有潜在的问题"

> beta版本虽然可用于个人用途的生存模式, 但是内含的内容本身并不确定, 其中的内容在将来的更新中可能会出现增、删、改
