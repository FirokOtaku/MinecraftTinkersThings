# 自定义矿物生成 customize ore gens

通过修改`/config/tiths_ores.json`文件即可自定义材料属性 you can customized ore gens by editing `/config/tiths_ores.json` file

_⚠ 只能修改**本模组添加的**方块 only blocks **added by this mod** can be customized_


## 整体格式 format

配置文件遵循`{"tile_name1":{ configs }, "tile_name2":{ configs }, ...}`格式 the config file follows the format of `{"tile_name1":{ configs }, "tile_name2":{ configs }, ...}`

    {
        "tile_name":{
            "min_y": 20,
            "max_y": 30,
            "times": 3,
            "rate": 0.8,
            "dims": [ 1, -1 ]
        }
    }
ℹ `"min_y"`和`"max_y"`属性为生成时允许的最低和最高中心y轴高度

ℹ `"times"`属性为每区块尝试进行多少次矿物生成 `"times"` is the number of generation attempts for each chunk

ℹ `"rate"`属性为每次矿物生成成功几率 `"rate"` is probability of success of each attempt

ℹ `"size"`属性为每次矿物生成最大矿物数量 `"size"` is the  maximum number of ore blocks in one attempt

ℹ `"dims"`属性会禁用指定维度中的矿物生成 `"dims"` will disable ore generation in those dimensions

ℹ 每区块最大生成数量为 `times × rate × size` the maximum amount of a kind of ore in a chunk is `times × rate × size`

ℹ 本模组矿物生成只会监测并替换掉**Minecraft原版**的石头, 不支持替换其它种类方块. 如果某维度中没有原版石头, 即使配置文件中没有禁用此维度的矿物生成, 最终依旧不会有矿物生成在此维度 only stone of **Minecraft itself** (`minecraft:stone`) will be detected and replaced into ores. stones added by other mod are not supported. if there is no `minecraft:stone` in a dimension, no ores will generated even that dimension has not been disabled

## 示例配置文件 example

    {
        "ore_ruby":{
            "min_y": 10,
            "max_y": 20,
            "times": 3,
            "rate": 0.8,
            "size": 10,
            "dims": [ 1, -1 ]
        }
    }
