# Features File Format

Any "Features File" (that is, any file in the "sysconf" directory that fits the pattern `features-*.json`) is simply an array of objects, each object having a `name` and a `class` field. The `name` field is the string that the feature will be referred to by in any spawn entry that uses said feature, while the `class` must be the fully qualified class name of the class that provides the feature.

What follows is an example and the contents of the `features-default.json` that defines the built-in features of MMD OreSpawn 3.

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

That's it — simple as.

## Note (Tinkers' Things)

* The `name` values are exactly what you put in the `feature` field of a spawn entry, e.g. `vein` for the vein generator, `precision` for the precision generator, and so on.
