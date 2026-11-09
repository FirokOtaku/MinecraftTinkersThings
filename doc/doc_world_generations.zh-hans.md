# 匠魂扩增 世界生成文档

要阅读此文档, 您**可能需要**对 Minecraft 游戏目录结构有一定了解, 且**需要**对 JSON 格式有一定了解

* [JSON - 菜鸟教程](https://www.runoob.com/json/json-tutorial.html)
* [JSON 标准](https://www.json.org/json-zh.html)

> 如果您不了解 JSON 格式,  
> 在编辑各种配置文件时大概率会遇到各种错误

## 目录

如果您希望自定义任何一种世界生成, 请先参阅 **MMD Ore Spawn** 的[上游文档](https://github.com/MinecraftModDevelopmentMods/OreSpawn/wiki).
我们在这里只提供我们自己提供的生成器的文档.

### OreSpawn 3 文档

> 以下页面为中文翻译. 我们同时在本仓库内保留了对应的英文页面, 但它们是由这些中文翻译回译整理的, 仅供参考;
> 权威来源仍是[上游 Wiki](https://github.com/MinecraftModDevelopmentMods/OreSpawn/wiki).

[OreSpawn 3 JSON 配置文档 (翻译)](mmd_ore_spawn_doc_JSONDocumentation.zh-hans.md)

[OreSpawn 3 方块定义文档 (翻译)](mmd_ore_spawn_doc_BlockDefinition.zh-hans.md)

[OreSpawn 3 生成器文件格式 (面向开发者) (翻译)](mmd_ore_spawn_doc_FeaturesFileFormat.zh-hans.md)

[OreSpawn 3 默认生成器 (翻译)](mmd_ore_spawn_doc_DefaultFeature.zh-hans.md)

[OreSpawn 3 精确生成器 (翻译)](mmd_ore_spawn_doc_PrecisionFeature.zh-hans.md)

[OreSpawn 3 普通云生成器和集束生成器 (翻译)](mmd_ore_spawn_doc_NormalCloudAndClustersFeature.zh-hans.md)

[OreSpawn 3 岩脉生成器 (翻译)](mmd_ore_spawn_doc_VeinFeature.zh-hans.md)

### 我们提供的生成器

[晶洞生成器](doc_CrystalCaveGenerator.zh-hans.md)

[云层生成器](doc_CloudGenerator.zh-hans.md)

[海床生成器](doc_SeabedGenerator.zh-hans.md)

[树根生成器](doc_TreeRootGenerator.zh-hans.md)

> 如果您在寻找更早版本 (0.2.x) 中通过 Forge 配置系统调整世界生成的说明,  
> 请参阅[世界生成配置相关文档](customize_world_generations.zh-hans.md)

## 翻译声明

本文档包含对 **MMD Ore Spawn** [模组文档](https://github.com/MinecraftModDevelopmentMods/OreSpawn/wiki)的中文翻译

本人 (译者 Firok) :  
1. 不负责对文档内容进行解释
2. 不对由于阅读本文档可能导致的问题负责
3. 不负责代写/调整/改正配置文件

> 对原文档有问题的人,  
> 请自行联系 MMD Ore Spawn 的开发者或相关社区;  
> 如果对我们提供的生成器有问题,  
> 请入群提问或提交 Issue

本人语文和翻译水平有限.  
如需对照英文原文, 可查阅同名的 `.en-us.md` 文档, 或直接阅读 [MMD Ore Spawn 上游 Wiki](https://github.com/MinecraftModDevelopmentMods/OreSpawn/wiki).

> **示例译注**
> 这是一段示例译注

## 翻译对照

以下是对原文中某些名词的解释

原文 **feature generator** 译文 **生成器**

一种生成器表现为一种生成矿物的方式,  
比如"像原版矿物一样将矿物生成到地下",  
或是"将矿物像云朵一样生成在天空中"等

原文 **end user** 译文 **终端用户**

终端用户基本就是指玩家或者制作整合包的人了
