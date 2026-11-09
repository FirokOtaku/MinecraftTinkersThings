# OreSpawn 3 JSON Configuration Documentation

In OreSpawn 3 the JSON system has been expanded to include several new features and be extensible. There are, with "version 1" of this new system two special files introduced — `_features.json` and `_replacements.json`. With the latest format — tagged in the file as "2.0" — all system configuration or shared data is moved to the "sysconf" folder and `_features.json` and `_replacements.json` are now `sysconf/features-default.json` and `sysconf/replacements-default.json`, which are joined by `sysconf/known-configs.json` (which tracks what mods have had their default configs extracted, so that your hard work isn't overwritten) and `sysconf/presets.json` (not always present, but allows for defining preset variables shared among all your config files).

The "features-default" file defines the "feature generators" provided by MMD Ore Spawn (3.2) itself which should always be available to the end user. If you have a compatible feature generator (i.e. one that implements `com.mcmoddev.orespawn.api.IFeature` and optionally extends `com.mcmoddev.orespawn.api.FeatureBase`) you can provide a "feature" json of your own that tells MMD Ore Spawn the preferred name of the feature generator and its fully qualified class name so it can be instantiated and utilized. The format of a "features" file is covered in the [features file format](mmd_ore_spawn_doc_FeaturesFileFormat.en-us.md "Features File Format") page.

For the "replacements-default.json" it is, nominally, empty — the default action is for any "Stone" in overworld dimensions, "Netherrack" in the Nether and "Endstone" in the End. You can, of course, provide a "replacements" file of your own, but this feature is, at this time, incomplete and the file format has not been documented because it is expected to change as this part of OreSpawn matures.

Lastly, the "presets" file has a somewhat open format, only requiring that it be a JSON Object. Each of the names of objects in it actually becomes part of a final variable name, denoted in use as the json-path-like format `$...` — in this way you can define large chunks of repeated data to make writing your custom configurations easier. See the "Presets File" page of the upstream MMD Ore Spawn documentation for more details (that page is not mirrored in this repository).

Outside of the "system configuration" directory is where the actual files defining how OreSpawn should spawn things are stored. These are a somewhat complex format, containing a "version" key, a "spawns" key and, optionally, a "presets" key. The "version" key, for the format described in this document, is "2.0" and the optional "presets" key should be an object formatted like the "presets" file — the definitions given in the object in a given config file apply only to that file. Lastly is the "spawns" key, which is an object where each key represents the name of a spawn and the attached object to that key describes the spawn itself.

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

## Note (Tinkers' Things)

* All of these files live in the `config/orespawn3` folder of your game directory; the files outside of `sysconf` (such as OreSpawn's own `orespawn.json` and our `tiths.json`) are placed directly in `config/orespawn3`.
* `<<<NAME>>>` is the name of the spawn entry and only has meaning inside the file it is declared in; `<<<block 'registry name' here>>>` is a block registry name such as `minecraft:coal_ore`.
* Our generators try to use the replacement detectors provided by MMD Ore Spawn whenever possible, but this is not guaranteed — some of our generators use their own built-in replacement detection instead.
* `sysconf/presets.json` is not generated automatically; if you need it, you have to create it manually.
* The steps for providing a custom feature generator (implementing `IFeature`, optionally extending `FeatureBase`, and shipping a features json) are intended for developers. If you are only an end user who wants to tweak some spawns, you do not need to read that part.
