# 生成器文件格式

生成器文件内容包含一个数组, 其中的每一项都包含`name`和`class`字段. 字符串类型的`name`字段用来指明生成器名称; `class`字段必须是一个类完整限定名, 指向一个符合生成器条件的类.

```json
[
  {
    "name": "default",
    "class": "com.mcmoddev.orespawn.impl.features.DefaultFeatureGenerator"
  },
  {
    "name": "vein",
    "class": "com.mcmoddev.orespawn.impl.features.VeinGenerator"
  },
  {
    "name": "normal-cloud",
    "class": "com.mcmoddev.orespawn.impl.features.NormalCloudGenerator"
  },
  {
    "name": "precision",
    "class": "com.mcmoddev.orespawn.impl.features.PrecisionGenerator"
  },
  {
    "name": "clusters",
    "class": "com.mcmoddev.orespawn.impl.features.ClusterGenerator"
  }
]
```

就这么简单.

<details>
<summary>原文</summary>
Any "Features File" (that is, any file in the "sysconf" directory that fits the pattern "features-.json") is simply an array of objects, each object having a "name" and a "class" field. The "name" field is the string that the feature will be referred to by in any spawn entry that uses said feature while the "class" must be the fully qualified class name of the class that provides the feature.

What follows is an example and the contents of the "features-default.json" that defines the built-in features of MMD OreSpawn 3.

```text
json内容
```

That's it - simple as.
</details>
