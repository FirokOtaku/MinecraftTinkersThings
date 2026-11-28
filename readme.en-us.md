# [TITHS] Tinkers' Things

An addon of Tinkers' Construct.

[Chinese version](readme.md)

* Changelog:
  [link](changelog.en-us.md)
* Contributors:
  [link](doc/contributors.en-us.md)
* How to customize material stats:
  [link](doc/doc_customize_materials.en-us.md)
* How to customize world generations:
  [link](doc/doc_world_generations.en-us.md)

> All world generations depend on **MMD Ore Spawn** after 0.3.25.0.
> If you want to customize generations, please check the docs provided by this mod.

> This release requires **MMD OreSpawn 4.1.6.112021** or later for Minecraft 1.12.2 Forge. OreSpawn loads the bundled ore rules directly. Pack overrides in `config/tiths-orespawn.json` take precedence.
> When upgrading from OreSpawn 3, the mod archives a recognized stock `config/orespawn3/tiths.json` to `config/orespawn-migration/tiths-os3-default.json`. OreSpawn migrates customized legacy files. If the migrated rules contain `tiths:legacy/test`, remove that old cloud rule. The compatibility plugin now generates Brume Jade clouds separately.
> New worlds use the current rules. Existing worlds have their own saved rules. Back up the world before editing `<world>/serverconfig/orespawn-worldgen.json`. By default, generation applies to new chunks.

## Latest update

See [changelog](changelog.en-us.md)

## Differences between versions

Version|Fatal problem|General problem|Gameplay problem|For server use|For personal use
-|-|-|-|-|-
alpha|No known|Included|Included|Not recommended|Preview only
beta|No known|No known|Included|Not recommended|Can be used for survival mode

* **Fatal problem** will cause the game to crash, etc.
* **General problem** wrong values, etc.
* **Gameplay problem** missing textures, balance issues, etc.

> "No known" means "no known problems", but "may contain potential problems"

> Although the beta version can be used for personal survival mode, the content itself is not final, and content may be added, deleted, or changed in future updates
