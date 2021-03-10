# OreSpawn 3 JSON 配置文档

OreSpawn 3为JSON配置系统增加了数个新功能与可扩展性.

"版本1"系统时我们引入了两个特殊文件: `_features.json`和`_replacements.json`.

最新的"2.0"系统中, 所有的配置文件和共享数据都被移至`sysconf`文件夹, 之前的`_features.json`和`_replacements.json`现在变为`sysconf/features-default.json`和`sysconf/replacements-default.json`; 新增一个配置文件`sysconf/known-configs.json`用于追踪哪些模组的默认的自定义生成配置文件已被导出, 这样由终端用户自定义的内容就不会被覆盖掉; 另外还新增了`sysconf/presets.json`(这个文件并不一定总是存在)用于定义在所有配置文件间共享的预设变量.

> **译注**
> 这些文件在`游戏目录/config/orespawn3`文件夹里面能找到

<details>
<summary>原文</summary>  
In OreSpawn 3 the JSON system has been expanded to include several new features and be extensible. There are, with "version 1" of this new system two special files introduced - _features.json and _replacements.json. With the latest format - tagged in the file as "2.0" - all system configuration or shared data is moved to the "sysconf" folder and _features.json and _replacements.json are now "sysconf/features-default.json" and "sysconf/replacements-default.json", which are joined by "sysconf/known-configs.json" (which tracks what mods have had their default configs extracted, so that your hard work isn't overwritten) and "sysconf/presets.json" (not always present, but allows for defining preset variables shared among all your config files).
</details> <br>

`features-default`文件定义了由 MMD Ore Spawn(3.2) 提供的默认生成器, 这些生成器应始终对终端用户可用.

如果您希望提供一种自定义生成器, 则您需要:

1. 创建一个实现了`com.mcmoddev.orespawn.api.IFeature`接口的类
2. (可选地) 使这个类继承自基类`com.mcmoddev.orespawn.api.FeatureBase`
3. 提供一个`feature.json`文件, 用于告知 MMD Ore Spawn 这些自定义生成器的名称和类完整限定名 (相关文件格式 [如下](#生成器文件格式 "生成器文件格式"))

> **译注**
> 这段是开发者用的,  
> 如果您只是作为终端用户希望修改部分生成则不需要看这段

<details>
<summary>原文</summary>
The "features-default" file defines the "feature generators" provided by MMD OreSpawn (3.2) itself which should always be available to the end user - if you have a compatible feature generator (ie: one that implements "com.mcmoddev.orespawn.api.IFeature" and optionally extends "com.mcmoddev.orespawn.api.FeatureBase") you can provide a "feature" json of your own that tells MMD OreSpawn the preferred name of the feature generator and its fully qualified class name so it can be instantiated and utilized. The format of a "features" file is covered on the "features format" page.
</details> <br>

`replacements-default`文件定义了 MMD Ore Spawn 可以替换世界中的哪些方块进行世界生成. 这个文件通常是空的. 默认情况下, 在主世界内可以替换石头, 末地中可以替换末地石, 下界中可以替换地狱岩.

当然, 你可以自定义一份替换文件, 但是相关文件格式没有文档, 因为此功能在当下还没有完成, 并可能随着 MMD Ore Spawn 的更新发生变化.

> **译注**
> 默认情况下 MMD Ore Spawn 自带的生成器只能替换那3种方块  
> 我们的生成器也会尽量使用 MMD Ore Spawn 提供的替换检测器进行生成,  
> 但这不是一定的, 部分生成器将会完全使用内置的替换检测器

<details>
<summary>原文</summary>
For the "replacements-default.json" it is, nominally, empty - the default action is for any "Stone" in overworld dimensions, "Netherrack" in the Nether and "Endstone" in the End. You can, of course, provide a "replacements" file of your own, but this feature is, at this time, incomplete and the file format has not been documented because it is expected to change as this part of OreSpawn matures.
</details> <br>

最后, `presets`文件的格式相对宽松, 只要求它遵循JSON格式. 其中, 所有的对象名都会变成变量名, 使用`$...`来引用相关变量.

通过这种方式, 您可以将大量重复数据定义为变量, 使得编写自定义配置文件更加容易. 相关文件格式 [如下](#预设文件格式 "预设文件格式").

> **译注**
> 这个文件默认不会自动生成,  
> 需要的话得由终端用户手动创建

<details>
<summary>原文</summary>
Lastly, the "presets" file has a somewhat open format, only requiring that it be a JSON Object. Each of the names of objects in it actually becomes part of a final variable name, denoted in use as the json-path-like format $.... - in this way you can define large chunks of repeated data to make writing your custom configurations easier. See the "Presets File" page for more details.
</details> <br>

Outside of the "system configuration" directory is where the actual files defining how OreSpawn should spawn things are stored. These are a somewhat complex format, containing a "version" key, a "spawns" key and, optionally, a "presets" key. The "version" key, for the format described in this document, is "2.0" and the optional "presets" key should be an object formatted like the "presets" file - the definitions given in the object in a given config file, apply only to that file. Lastly is the "spawns" key, which is an object where each key represents the name of a spawn and the attached object to that key describes the spawn itself.

The bare minimum required for an OreSpawn 3 config file is, then, as follows:

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