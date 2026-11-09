# Normal Cloud and Clusters Feature

The normal-cloud feature generator and the clusters generator have the types `normal-cloud` and `clusters` respectively, and both have similar underlying math to the placement. Both start by selecting a central point and then using a "normal distribution" model to create a "point cloud" around that central point where they will actually generate the feature.

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
