# 普通云生成器和集束生成器

普通云生成器和集束生成器的类型分别为`normal-cloud`和`clusters`.

> **译注**
> 请注意, 这里的"云"不是"云朵",  
> 更类似于"[电子云](https://baike.baidu.com/item/%E7%94%B5%E5%AD%90%E4%BA%91)"

这两者的数学特征类似, 都是在地下选中一个起始点, 然后使用正态分布模型在起始点附近生成一片"点云".
普通云特性生成器和集群生成器在布局方面都具有类似的基础数学。两者都是从选择一个中心点开始，然后使用“正态分布”模型在中心点周围创建一个“点云”，在这个中心点上，它们将实际生成特征。

普通云生成器会在每个随机生成点生成一块矿物方块; 集束生成器则会由终端用户指定生成数量的矿物.

当下没有什么例子, 我只列出这两个生成器接收的参数. 你可以从共有参数开始填写, 有需要的话补充上独有参数即可.

### 共有参数

* maxSpread - 云的最大直径, 或是衍生节点与中心点的最大距离
* medianSize - 云的平均直径, 由`variance`字段动态调整上下限. 请注意, 在检查代码时发现了一个错误, 这些变量并未生效
* variation - 决定`medianSize`的最大最小值
* frequency - 生成几率
* minAttempts - 最小尝试生成次数
* maxAttempts - 最大尝试生成次数

### 集束生成器独有参数

* size - 每集束平均生成矿物数量
* numObjects - 集束数量

<details>
<summary>原文</summary>
The normal-cloud feature generator and the clusters generator both have similar underlying math to the placement. Both start by selecting a central point and then using a "normal distribution" model to create a "point cloud" around that central point where they will actually generate the feature.

Where the normal-cloud will put a single ore at each of those outlying points, the clusters will generate small spawns of user-determined size at each of those points.

There are no definitive examples available at this time, so instead I'll just list the parameters here, starting with the ones that are shared and then covering the ones that are unique to either generator.

### Shared Parameters

* maxSpread - the maximum diameter of the cloud or center-to-center maximum separation of spawned nodes across the chosen center
* medianSize - average diameter of the cloud, modified by "variance" - please note that a bug was found when reviewing the code to make this documentation that indicates these numbers are not properly utilized.
* variation - +/- deviation from medianSize (see note on mediaSize)
* frequency - how often this will succeed at being spawned as a feature
* minAttempts - minimum number of times to try to place this feature
* maxAttempts - maximum number of times to try to place this feature

### Clusters Only Parameters

* size - average size of a spawned "cluster"
* numObjects - number of clusters to try and spawn
</details>
