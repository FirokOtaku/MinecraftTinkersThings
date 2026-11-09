# Tinkers' Things World Generation Config Documentation

In the newer versions of Tinkers' Things, we have merged the world generation configuration from json files into the Forge config system.

If you want to change world generation, please read this document carefully.

## Adjusting

You can modify config entries inside the Forge config system (that is, the mod config screen you see in the game's main menu) to adjust world generation.

After you save the world generation config, we will reload all world generators of this mod.

> Reloading world generators may cause a slight stutter

> In the old (0.2.x) version of Tinkers' Things, we allowed adding generation for certain ores through config files; in the new (0.3.x) version of Tinkers' Things this feature has been removed

## Global Settings

| Category | Name | Type | Effect |
| --- | --- | --- | --- |
| General Settings | `enable_world_generations` | boolean | If false, all world generation is disabled |
| General Settings | `blacklist_dim_generation` | integer array | Enter several dimension ids here; we will not perform any world generation in those dimensions |
| General Settings | `blacklist_biome_generation` | string array | Enter several biome names here; we will not perform any world generation in those biomes |

The global world generation config entries **take precedence over** all other custom configs, regardless of their settings.

> For example, suppose dimension 0 is blacklisted in the global config; even if some custom entry specifies that an ore generates only in dimension 0, we will not generate anything

## Per-Entry Settings

You can customize the parameters of the generator for each ore individually.

Parameters use the `key=value` format, separated from each other by an **English semicolon**.

### Common Parameters

| Key | Allowed Values | Default | Meaning |
| --- | --- | --- | --- |
| `enable` | `true`\|`false` | `true` | Whether this generator is enabled |
| `rate_chunk` | `[0,1]` (decimal) | `1` | Chance of a successful generation per chunk |
| `time_chunk` | `[0,)` (integer) | `1` | Number of attempts per chunk |
| `rate_single` | `[0,1]` (decimal) | `1` | Chance of success per attempt |
| `strategy_dim` | ([Generation Strategy](#generation-strategy)) | `NONE_BLACKLIST` | Dimension strategy |
| `strategy_biome` | ([Generation Strategy](#generation-strategy)) | `NONE_BLACKLIST` | Biome strategy |
| `dims` | integer array, separated by **commas** | `[]` | List of dimension ids |
| `biomes` | string array, separated by **commas** | `[]` | List of biome ids |
| `type` | string | - | Generator type |

Depending on the `type` attribute you provide, we decide which kind of generator to create, and therefore which type-specific parameters to read.

> If a parameter has no default value, then it is required; the same applies below

> Some generators are relatively inefficient; please do not adjust generation frequency and similar parameters excessively

Note that every correctly configured entry creates one generator. So if you set two ores to use the cloud generator with per-chunk success chances of 0.01 and 0.03, you will ultimately have a 0.01+0.03=0.04 chance to find clouds in a chunk.

### Common Ore Generation (type=common)

This generator adds ores to the world in a manner similar to vanilla ore generation. Most ores use this generator.

| Key | Allowed Values | Default | Meaning |
| --- | --- | --- | --- |
| `min_y` | `[0,)` (integer) | - | Minimum Y level for generation |
| `max_y` | `[0,)` (integer) | - | Maximum Y level for generation |
| `size` | `[1,)` (integer) | - | Maximum generation amount |
| `selector` | ([Block Type Selector](#block-type-selector)) | `stone` | We will only replace blocks of the specified kind |

> The `max_y` value must be greater than the `min_y` value

Example config

```
type=common; min=20; max=40; size=5; selector=stone
```

### Bedrock Ore Generation (type=bedrock)

This generator adds ores to the bedrock layer. By default, *Broken Bedrock* uses this generator.

| Key | Allowed Values | Default | Meaning |
| --- | --- | --- | --- |
| `size` | `[1,)` (integer) | - | Maximum generation amount |
| `rate` | `[1,0]` (decimal) | `0.8` | Chance of a single success |

> This generator only replaces stone and cobblestone blocks

Example config

```
type=bedrock; size=7; rate=0.5
```

### Seabed Ore Generation (type=seabed)

This generator adds ores to the seabed. By default, *Seagrass* uses this generator.

This generator has no configurable entries.

Example config

```
type=seabed
```

### Cloud Ore Generation (type=cloud)

This generator creates clouds and distributes ores within them. By default, *Cloud Jade* uses this generator.

| Key | Allowed Values | Default | Meaning |
| --- | --- | --- | --- |
| `min_x` | `[1,8]` (integer) | `3` | Minimum X-axis length |
| `max_x` | `[1,8]` (integer) | `6` | Maximum X-axis length |
| `min_y` | `[1,8]` (integer) | `1` | Minimum Y-axis length |
| `max_y` | `[1,8]` (integer) | `2` | Maximum Y-axis length |
| `min_z` | `[1,8]` (integer) | `3` | Minimum Z-axis length |
| `max_z` | `[1,8]` (integer) | `6` | Maximum Z-axis length |
| `min_height` | `[0,)` (integer) | `150` | Minimum generation height |
| `max_height` | `[0,)` (integer) | `180` | Maximum generation height |
| `rate_ore` | `[0,1]` (decimal) | `0.00225` | Chance for a cloud block to become an ore |

> This generator only replaces logical air blocks

> By default, there is only a `0.015` chance of generating a *Cloud Jade* ore cloud in a chunk

Example config

```
type=cloud; min_x=5; max_x=7; min_y=2
```

### Tree Root Ore Generation (type=tree_root)

This ore generates below trees. By default, *Tree Root* uses this generator.

| Key | Allowed Values | Default | Meaning |
| --- | --- | --- | --- |
| `depth_base` | `[1,)` (integer) | `3` | Base depth |
| `depth_extra` | `[1,)` (integer) | `3` | Extra random depth |

> This generator forcibly skips generation in cold and ocean biomes, regardless of custom configuration

> This generator only replaces dirt, stone and cobblestone blocks

Example config

```
type=tree_root; depth_base=4; depth_extra=6;
```

### Lava Ball Ore Generation (type=lava_ball)

This generator has no configurable entries.

Example config

```
type=lava_ball
```

### Meteorite Ore Generation (type=meteo)

> **Meteorite Ore Generation** is not **Meteorite Ore Block**

> This generator is still not implemented

## Configuration Errors

If some config entry is filled in incorrectly (for example, missing a key-value pair), the creation of that world generator will be skipped, and the related error information will be output to the log.

> Yes, you can skip loading a config entry by deliberately entering invalid content, but we recommend using `disable=true` instead, so that no redundant error logs are produced

All world generator config error messages start with `An error occurred while creating world generator`, followed by the detailed error content.

Possible errors are as follows

| Error Message | Meaning | Possible Fix |
| --- | --- | --- |
| `The given value is below the minimum allowed` | The input value is below the allowed minimum | Change the value to fit the range |
| `The given value is higher than the maximum allowed` | The input value is above the allowed maximum | Change the value to fit the range |
| `Missing the specified key-value pair` | A specified key-value pair is missing | Add the specified key-value pair |
| `Invalid key-value pair` | Invalid key-value pair | Remove the specified key-value pair |
| `Duplicate parameter` | Duplicate key-value pair | Remove the specified key-value pair |
| `Unimplemented generator type` | A planned but still unimplemented generator type | Use a generator of another type |
| `Unknown world generator type` | Unknown world generator type | Use a correct world generator type |
| `Unexpected strategy value` | Incorrect generation strategy | Use a correct generation strategy |
| `Missing float value` | Missing decimal value | Add the decimal value |
| `Unexpected float value` | Decimal conversion failed | Provide a decimal in the correct format |
| `Missing integer value` | Missing integer value | Add the integer value |
| `Unexpected integer value` | Integer conversion failed | Provide an integer in the correct format |

## Generation Strategy

We provide 4 generation strategies, as shown in the table below

| Strategy Key | Meaning |
| --- | --- |
| `ALWAYS` | Always |
| `ONLY_WHITELIST` | Only when in the whitelist |
| `NONE_BLACKLIST` | Whenever not blacklisted |
| `NONE` | Never |

> Using the `NONE` strategy has an effect similar to `enable=false`; both disable all generation of this generator, but we recommend using `enable=false` to avoid unnecessary computation

## Biome Groups

We have preset several biome groups for use by various generation and logic checks.

* Plains
* Forest
* Badlands
* Mountains
* Desert
* Water
* Ocean
* Water and Ocean
* Special
* Hot
* Warm
* Normal Temperature
* Cool
* Cold

> Currently you can only modify biome groups; you cannot add or remove groups

> If you want to view all available biome ids in the current game environment, use the command `/tiths biomes`; see the mod command introduction screen

## Block Type Selector

We have preset several block type selectors, which can be used in some generators to control world generation and avoid replacing blocks you do not want to replace.

| Key | Blocks Matched |
| --- | --- |
| `any` | Can replace any block |
| `none` | Will not replace any block |
| `air` | Air blocks |
| `any_stone` | Stone or cobblestone |
| `stone` | Stone |
| `cobble_stone` | Cobblestone |
| `end_stone` | End stone |
| `netherrack` | Netherrack |
| `grass` | Grass block |
| `dirt` | Dirt |
| `sand` | Sand |
| `lava` | Lava |
| `magma` | Magma block |
