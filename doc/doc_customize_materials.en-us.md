# Customize Material Stats

You can customize material stats by editing the `/config/tiths_materials.json` file.

_⚠ Only materials **added by this mod** can be customized_

> By default, the `config/tiths_materials.json` file will not be generated automatically and you need to create one by yourself

## Format

The config file follows the format of `{"material_id1":{ configs }, "material_id2":{ configs }, ...}`

    {
        "material_id":{
            "traits_tool": [ "trait_id" ], /* traits of parts when used on a tool */
            "traits_armor": [ "trait_id" ], /* traits of parts when used on a armor */
            "head": { /* head stats */
                "durability": 1.23, /* durability */
                "mining_speed": 1.23, /* mining speed */
                "attack": 1.23, /* attack damage */
                "harvest_level": 1, /* harvest level */
                "traits": [ "trait_id" ] /* traits list */
            },
            "handle": { /* handle stats */
                "modifier": 1.23, /* durability modifier */
                "durability": 123, /* durability */
                "traits": [ "trait_id" ] /* traits list */
            },
            "extra":{ /* extra part stats */
                "durability": 123, /* durability */
                "traits": [ "trait_id" ] /* traits list */
            },
            "bow":{ /* bow limb stats */
                "draw_speed": 1.23, /* draw speed */
                "range": 1.23, /* range factor */
                "bonus_damage": 1.23, /* bonus damage */
                "traits":[ "trait_id" ] /* traits list */
            },
            "bowstring":{ /* bowstring stats */
                "modifier": 1.23, /* quality modifier */
                "traits":[ "trait_id" ] /* traits list */
            },
            "fletching":{ /* fletching stats */
                "accuracy":1.23, /* accuracy factor */
                "modifier":1.23, /* quality modifier */
                "traits": [] /* traits list */
            },
            "arrowshaft":{ /* arrow shaft stats */
                "modifier": 1.23, /* quality modifier */
                "bonus_ammo": 123, /* bonus ammo */
                "traits": [] /* traits list */
            },
            "core":{ /* core stats */
                "durability": 1.23, /* durability modifier */
                "dense": 1.23, /* defense factor */
                "traits": [] /* traits list */
            },
            "trim":{ /* trim stats */
                "durability": 1.0, /* durability modifier */
                "traits": [] /* traits list */
            },
            "plate":{ /* plate stats */
                "modifier": 1.0, /* quality modifier */
                "durability": 1.0, /* durability */
                "toughness": 1.0, /* toughness factor */
                "traits": [] /* traits list */
            }
        }
    }

ℹ traits in `"traits_tool"` will be added to *head*, *handle*, *extra* parts

ℹ traits in `"traits_bow"` will be added to *bow*, *bowstring*, *fletching*, *shaft* parts

ℹ traits in `"traits_armor"` will be added to *core*, *trim*, *plate* parts

ℹ given values will override the default values

⚠ if you specified traits of any parts as `"traits":[]`, it **will not** use default traits list, but **no traits**

## Example config file

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
