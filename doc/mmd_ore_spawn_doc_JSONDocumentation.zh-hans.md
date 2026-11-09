# OreSpawn 3 JSON 配置文档

OreSpawn 3 为 JSON 配置系统增加了数个新功能与可扩展性.

"版本 1"系统时我们引入了两个特殊文件: `_features.json`和`_replacements.json`.

最新的"2.0"系统中, 所有的配置文件和共享数据都被移至`sysconf`文件夹, 之前的`_features.json`和`_replacements.json`现在变为`sysconf/features-default.json`和`sysconf/replacements-default.json`; 新增一个配置文件`sysconf/known-configs.json`用于追踪哪些模组的默认的自定义生成配置文件已被导出, 这样由终端用户自定义的内容就不会被覆盖掉; 另外还新增了`sysconf/presets.json`(这个文件并不一定总是存在)用于定义在所有配置文件间共享的预设变量.

> **译注**
> 这些文件在`游戏目录/config/orespawn3`文件夹里面能找到

`features-default`文件定义了由 MMD Ore Spawn(3.2) 提供的默认生成器, 这些生成器应始终对终端用户可用.

如果您希望提供一种自定义生成器, 则您需要:

1. 创建一个实现了`com.mcmoddev.orespawn.api.IFeature`接口的类
2. (可选地) 使这个类继承自基类`com.mcmoddev.orespawn.api.FeatureBase`
3. 提供一个`feature.json`文件, 用于告知 MMD Ore Spawn 这些自定义生成器的名称和类完整限定名 (相关文件格式 [如下](mmd_ore_spawn_doc_FeaturesFileFormat.zh-hans.md "生成器文件格式"))

> **译注**
> 这段是开发者用的,  
> 如果您只是作为终端用户希望修改部分生成则不需要看这段

`replacements-default`文件定义了 MMD Ore Spawn 可以替换世界中的哪些方块进行世界生成. 这个文件通常是空的. 默认情况下, 在主世界内可以替换石头, 末地中可以替换末地石, 下界中可以替换地狱岩.

当然, 你可以自定义一份替换文件, 但是相关文件格式没有文档, 因为此功能在当下还没有完成, 并可能随着 MMD Ore Spawn 的更新发生变化.

> **译注**
> 默认情况下 MMD Ore Spawn 自带的生成器只能替换那 3 种方块  
> 我们的生成器也会尽量使用 MMD Ore Spawn 提供的替换检测器进行生成,  
> 但这不是一定的, 部分生成器将会完全使用内置的替换检测器

最后, `presets`文件的格式相对宽松, 只要求它遵循 JSON 格式. 其中, 所有的对象名都会变成变量名, 使用`$...`来引用相关变量.

通过这种方式, 您可以将大量重复数据定义为变量, 使得编写自定义配置文件更加容易.  
相关文件格式详见 MMD Ore Spawn 上游文档的 "Presets File" 页面 (本仓库暂未收录该页面的翻译).

> **译注**
> 这个文件默认不会自动生成,  
> 需要的话得由终端用户手动创建

> **译注**
> 以下两段内容在原中文翻译中缺失 (原文直接保留了英文), 此处为补译

在"系统配置"目录 (`sysconf`) 之外, 才是真正用于定义 OreSpawn 应当如何生成事物的文件所存放的位置. 这些文件使用的是一种相对复杂的格式, 包含一个`version`键, 一个`spawns`键, 以及 (可选的) 一个`presets`键. 对于本文档所描述的格式而言, `version`键的值是`2.0`; 可选的`presets`键应当是一个与`presets`文件格式相同的对象 —— 在某个配置文件的`presets`对象内给出的定义, 只对该文件生效. 最后是`spawns`键, 它是一个对象, 其中的每个键代表一个生成项 (spawn) 的名称, 而该键对应的对象则描述了生成项本身.

因此, 一个 OreSpawn 3 配置文件的最低要求如下:

```json
{
    "version": "2.0",
    "spawns": {
        "<<<NAME>>>": {
            "retrogen": false,
            "enabled": true,
            "feature": "default",
            "replaces": "default",
            "dimensions": [],
            "biomes": { "excludes": [] },
            "parameters": {
                "size": 8,
                "variation": 4,
                "frequency": 5,
                "minHeight": 0,
                "maxHeight": 128
            },
            "blocks": [ { "name": "<<<block 'registry name' here>>>", "chance": 100 } ]
        }
    }
}
```

> **译注**
> 这些位于`sysconf`之外的配置文件应当直接放在`游戏目录/config/orespawn3`文件夹下,  
> 例如 MMD Ore Spawn 自带的`orespawn.json`和我们模组的`tiths.json`便是如此  
> 上面示例中`<<<NAME>>>`处填写生成项的名称 (只在该配置文件内起作用),  
> `<<<block 'registry name' here>>>`处填写方块的注册名, 例如`minecraft:coal_ore`
