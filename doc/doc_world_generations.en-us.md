# Tinkers' Things World Generation Documentation

To read this document, you **probably need** some knowledge of the Minecraft game directory structure and you **do need** some knowledge of the JSON format.

* [What is JSON? - W3School](https://www.w3schools.com/whatis/whatis_json.asp)
* [JSON Standard](https://www.json.org/json-en.html)

> If you don't understand JSON format,  
> you may encounter many problems when editing config files

## Index

For English readers:  
If you want to customize any of the generations, please refer to the [upstream documentation](https://github.com/MinecraftModDevelopmentMods/OreSpawn/wiki) of **MMD Ore Spawn** first.  
We only provide the documentation of our own generators here.

### MMD Ore Spawn 3 documentation

> The English pages below are copies kept in this repository, reconstructed from the Chinese translations that we maintain.
> They are provided for convenience only — the [upstream wiki](https://github.com/MinecraftModDevelopmentMods/OreSpawn/wiki) is the authoritative source.

[OreSpawn 3 JSON Documentation](mmd_ore_spawn_doc_JSONDocumentation.en-us.md)

[OreSpawn 3 Block Definition](mmd_ore_spawn_doc_BlockDefinition.en-us.md)

[OreSpawn 3 Feature File Format (developer facing)](mmd_ore_spawn_doc_FeaturesFileFormat.en-us.md)

[OreSpawn 3 Default Feature](mmd_ore_spawn_doc_DefaultFeature.en-us.md)

[OreSpawn 3 Precision Feature](mmd_ore_spawn_doc_PrecisionFeature.en-us.md)

[OreSpawn 3 Normal Cloud and Clusters Feature](mmd_ore_spawn_doc_NormalCloudAndClustersFeature.en-us.md)

[OreSpawn 3 Vein Feature](mmd_ore_spawn_doc_VeinFeature.en-us.md)

### Our generators

[Crystal Cave Feature Generator](doc_CrystalCaveGenerator.en-us.md)

[Cloud Feature Generator](doc_CloudGenerator.en-us.md)

[Seabed Feature Generator](doc_SeabedGenerator.en-us.md)

[Tree Root Feature Generator](doc_TreeRootGenerator.en-us.md)

> If you are looking for how older versions (0.2.x) customized world generation through the Forge config system,  
> please refer to [customize world generations](customize_world_generations.en-us.md).

## Translation disclaimer

This repository provides **Chinese translations** of the [documentation](https://github.com/MinecraftModDevelopmentMods/OreSpawn/wiki) of the **MMD Ore Spawn** mod.  
The English pages kept here are reconstructed from those Chinese translations (and from the original text where available), so wording may differ from upstream.

The translator (Firok):
1. does not take responsibility for explaining the content of the documents
2. does not take responsibility for any problem that may arise from reading these documents
3. does not write, adjust, or fix config files on behalf of others

> If you have questions about the original documentation,  
> please contact the developers or community of MMD Ore Spawn directly;  
> if you have questions about our generators,  
> please ask in our group chat or open an issue

Chinese and English versions are kept as separate `.zh-hans.md` / `.en-us.md` files.  
For authoritative English wording of MMD Ore Spawn docs, prefer the [upstream wiki](https://github.com/MinecraftModDevelopmentMods/OreSpawn/wiki).

> **Example note**
> This is an example translator's note

## Glossary

Original term **feature generator**

A feature generator represents one way of generating ores,  
for example "generate ores underground like vanilla ores do",  
or "generate ores in the sky like clouds".

Original term **end user**

The end user basically means the player, or someone who makes modpacks.
