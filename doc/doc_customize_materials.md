# 自定义材料属性 customized materials

通过修改`/config/tiths_materials.json`文件即可自定义材料属性 you can customized materials by editing `/config/tiths_materials.json` file

_⚠ 只能修改**本模组添加的**材料 only materials **added by this mod** can be customized_

> 默认情况下`config/tiths_materials.json`文件并不会自动生成, 需要由您手动创建一个  
> `config/tiths_materials.json` will not be generated automatically and you need to create one by yourself

## 整体格式 format

配置文件遵循`{"material_id1":{ configs }, "material_id2":{ configs }, ...}`格式 the config file follows the format of `{" material_id1 ": {configs}," material_id2 ": {configs}, ...}`

    {
        "material_id":{
            "traits_tool": [ "trait_id" ], /* 工具部件共有特性列表 traits of parts when used on a tool */
            "traits_armor": [ "trait_id" ], /* 护甲部件共有特性列表 traits of parts when used on a armor */
            "head": { /* 头部属性 */
                "durability": 1.23, /* 耐久度 */
                "mining_speed": 1.23, /* 挖掘速度 */
                "attack": 1.23, /* 攻击力 */
                "harvest_level": 1, /* 挖掘等级 */
                "traits": [ "trait_id" ] /* 特性列表 */
            },
            "handle": { /* 手柄属性 */
                "modifier": 1.23, /* 耐久系数 */
                "durability": 123, /* 耐久度 */
                "traits": [ "trait_id" ] /* 特性列表 */
            },
            "extra":{ /* 额外部件属性 */
                "durability": 123, /* 耐久度 */
                "traits": [ "trait_id" ] /* 特性列表 */
            },
            "bow":{ /* 弓臂属性 */
                "draw_speed": 1.23, /* 拉弓速度 */
                "range": 1.23, /* 射程系数 */
                "bonus_damage": 1.23, /* 额外伤害 */
                "traits":[ "trait_id" ] /* 特性列表 */
            },
            "bowstring":{ /* 弓弦属性 */
                "modifier": 1.23, /* 品质系数 */
                "traits":[ "trait_id" ] /* 特性列表 */
            },
            "fletching":{ /* 箭羽属性 */
                "accuracy":1.23, /* 准度系数 */
                "modifier":1.23, /* 品质系数 */
                "traits": [] /* 特性列表 */
            },
            "arrowshaft":{ /* 箭杆属性 */
                "modifier": 1.23, /* 品质系数 */
                "bonus_ammo": 123, /* 额外弹药 */
                "traits": [] /* 特性列表 */
            },
            "core":{ /* 基底属性 */
                "durability": 1.23, /* 耐久系数 */
                "dense": 1.23, /* 防御系数
                "traits": [] /* 特性列表 */
            },
            "trim":{ /* 夹板属性 */
                "durability": 1.0, /* 耐久系数 */
                "traits": [] /* 特性列表 */
            },
            "plate":{ /* 护甲板属性 */
                "modifier": 1.0, /* 品质系数 */
                "durability": 1.0, /* 耐久属性 */
                "toughness": 1.0, /* 韧性系数 */
                "traits": [] /* 特性列表 */
            }
        }
    }

ℹ `"traits_tool"`属性会为*头部*、*手柄*、*额外*部件增加指定特性 traits in `"traits_tool"` will be added to *head*, *handle*, *extra* parts

ℹ `"traits_bow"`属性会为*弓臂*、*弓弦*、*箭羽*、*箭杆*部件增加指定特性 traits in `"traits_bow"` will be added to *bow*, *bowstring*, *fletching*, *shaft* parts

ℹ `"traits_armor"`属性会为*基底*、*护甲板*、*夹板*部件增加指定特性 traits in `"traits_armor"` will be added to *core*, *trim*, *plate* parts

ℹ 提供的值会覆盖掉相应的默认值 given values will override the default values

⚠ 如果将某材料某部件的*特性*设为`"traits":[]`, 将**不会**使用默认特性, 而会变为**无特性** if you specified traits of any parts as `"traits":[]`, it **will not** use default traits list, but **no traits**

## 示例配置文件 example

    {
        "ruby":{
            "traits_tool":[],
            "traits_armor":[],
            "head":{
                "durability":2000,
                "mining_speed":1.0,
                "attack":5.0,
                "harvest_level":1.0,
                "traits":["dichroic"]
            },
            "handle":{
                "modifier":10.0,
                "durability":2000,
                "traits":["gorgeous","dragon_killer"]
            },
            "extra":{
                "durability":1000,
                "traits":["hyper","steamy","stamina_focusing"]
            },
            "bow":{
                "draw_speed":1.0,
                "range":1.0,
                "bonus_damage":0.5,
                "traits":[]
            },
            "bowstring":{
                "modifier":1.0,
                "traits":[]
            },
            "fletching":{
                "accuracy":1.0,
                "modifier":1.0
            },
            "arrowshaft":{
                "modifier":1.0,
                "bonus_ammo":200
            },
            "core":{
                "durability":1.23,
                "dense":1.23,
                "traits":[]
            },
            "trim":{
                "durability":1.0,
                "traits":[]
            },
            "plate":{
                "modifier":1.0,
                "durability":1.0,
                "toughness":1.0,
                "traits":[]
            }
        }
    }
