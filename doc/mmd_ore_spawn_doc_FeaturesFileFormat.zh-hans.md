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

> **译注**
> 任何位于`sysconf`目录下, 且文件名符合`features-*.json`模式的 JSON 文件都会被当作生成器文件加载  
> 上面代码块的内容就是 MMD Ore Spawn 3 内置生成器文件`features-default.json`的内容  
> 这些`name`就是在配置文件中`feature`字段里填写的名称,  
> 例如`vein`对应岩脉生成器, `precision`对应精确生成器等
