# Changelog

> All Time Stamps: GMT+8

## 0.3.38.0 _2026-11-08 19:00_

* added orystal item and block
* added crystallizer block

## 0.3.37.0 _2026-11-04 23:00_

* added inky potion effect
* modified inky trait

## 0.3.36.0 _2026-11-01 21:30_

* added sound effects to icy and radiant trait
* added missing language file content for sound effects

## 0.3.35.0 _2026-10-26 23:00_

* added peaceful potion effect
* added ender turbulence candy

## 0.3.34.0 _2026-10-26 22:00_

* added more types of gravity block

## 0.3.33.0 _2026-10-25 23:00_

* fixed ore generation not working properly
* optimized fluid collector logic
* updated fluid ball texture
* added some potion brewing recipes
* added some missing crafting recipes
* fixed buoyant light block not dropping items when harvested

## 0.3.32.0 _2026-10-25 18:00_

* updated development environment to ForgeGradle 5, updated MCP version
* fixed ender interfering logic block being collectible and a potential crash
* optimized fluid collector logic
* explosions from Chemical Instable trait no longer destroy terrain by default, and damage source is now the player
* optimized treasure house spawning logic
* AOE damage from Cracking trait no longer hurts teammates
* fixed some server-client data sync issues
* fixed mineral storage blocks not having harvest hardness set
* significantly reduced ore spawn amounts

## 0.3.30.0 _2021-03-12 10:30_

* modified world generations

## 0.3.29.0 _2021-03-11 15:30_

* modified world generations

## 0.3.28.0 _2021-03-10 23:00_

* added blocks
  * mesh blocks
  * moonlight converging table
* added particle effect system
* new documents preview

## 0.3.27.0 _2021-02-25 14:00_

* added traits
  * web wandering
  * ender interfering

## 0.3.26.0 _2021-02-23 11:30_

* added blocks
  * gravity block (double)
  * gravity block (triple)
  * shacking dispenser block
* bug fixes

## 0.3.25.1 _2021-02-22 16:00_

* modified some textures

## 0.3.25.0 _2021-02-19 14:30_

* now world generations depend on [MMD Ore Spawn](https://github.com/MinecraftModDevelopmentMods/OreSpawn)

## 0.3.24.0 _2021-02-12 17:30_

* added traits
  * piercing
  * tranquilizing
  * netting(projectile)
* added effects
  * tranquilized
* added blocks
  * shadow fire

## 0.3.23.0 _2021-01-26 09:30_

* added traits
  * farmland protective(armor)
  * anger of farmer
  * cherishing
  * anti griefing(armor)

## 0.3.22.0 _2021-01-22 11:00_

* add potion effects
  * midas vision

## 0.3.21.0 _2020-09-08 22:00_

* modified some textures

## 0.3.20.0 _2020-09-04 16:30_

* added new type of book pages

## 0.3.19.0 _2020-08-30 18:30_

* rewrote world generations

## 0.3.18.0 _2020-08-16 19:30_

* bug fixes
  * now Steamy trait can deal knockback correctly
  * now Threshold Limiting and Smooth traits can reduce damage correctly
  * now Star Dashing trait cannot deal damage to player himself and entities tamed by him

## 0.3.17.0 _2020-08-13 23:30_

* added blocks
  * iron punji block
  * sponge punji block
  * cloud punji block
* added potion effects
  * hard ice

## 0.3.16.0 _2020-08-10 20:00_

* removed blocks
  * channel placer
* added blocks
  * channel door

## 0.3.15.0 _2020-08-09 20:00_

* updated some recipes

## 0.3.14.0 _2020-08-08 19:00_

* updated lang files
* more traits are configurable now

## 0.3.13.0 _2020-08-06 10:00_

* added traits
  * aquatic(armor)
* added effects
  * empathic
* bug fixes
  * fixed several possible server-side crashes

## 0.3.12.0 _2020-07-29 19:00_

* added traits
  * moist(armor)
  * klein field(armor)
  * inductance
  * capacitor(armor)
  * carrier(armor)
  * recombining(armor)
  * empathy
* added effects
  * paralysed
* rewrite the way that saving data of some traits
* added permission checking to commands
* added cross-chunk checking to world generations

## 0.3.11.0 _2020-07-17 11:30_

* added items
  * carrot stack
  * wheat stack
  * fish stack
  * beetroot stack
  * seed stack
* added traits
  * ponderous(projectile)
  * carrot upgraded(armor)
  * wheat upgraded(armor)
  * fish upgraded(armor)
  * beetroot upgraded(armor)
  * seed upgraded(armor)

## 0.3.10.0 _2020-07-11 16:00_

* added traits
  * farmer
  * rancher

## 0.3.9.0 _2020-07-08 00:30_

* added items
  * leveling belt item
* removed blocks and items
  * mithril ore block
  * adamantine ore block
* renamed materials
  * adamantine -> magiga
  * mithril -> solita
* added traits
  * cavious(armor)
  * stellar falling(projectile)
  * cracking(projectile)
* modified traits
  * life inspiring and life inspiring(armor) now cost exp

## 0.3.8.0 _2020-07-04 12:00_

* added items
  * fluorescent gel
* added traits
  * infiltrating
* bug fixes
  * now configurations can be modified correctly

## 0.3.7.0 _2020-07-03 11:30_

* added items
  * lapsing charm item
  * chloroplast light item

## 0.3.6.0 _2020-06-23 01:00_

* added blocks and items
  * oxygen mask item
  * buoy block
  * buoyant light block
  * channel
  * heavy belt
  * ice light
  * fluid collector
  * fluid ball
  * cloud brick block
* removed blocks and items
  * channel pack wood
  * channel pack stone
* added fluids
  * shining gel
* added traits
  * lingering(armor)
  * green
* added potion effects
  * bruming
* renamed materials
  * aventurine -> brume jade
* bug fixes
  * now some of armor traits will not react to damage sources which bypass armor
  * we no longer use direct reflection to read or write values of classes of Minecraft

## 0.3.5.5 _2020-06-14 17:00_

* modified ore generations
* added blocks and items
  * belt oceanic item
  * icelit ore block
  * icelit item
  * icelit block
  * decurrium block
  * decurrium ingot item
  * decurrium nugget item
* added fluids
  * molten decurrium
* added materials
  * decurrium
  * icelit
* added traits
  * respecting
  * leaves hiding(armor)
  * surging(armor)
  * ablaze
  * quick freezing(armor)
* added potion effects
  * leaves hiding
  * ablaze

## 0.3.4.0 _2020-06-09 12:30_

* added blocks and items
  * tanatonium dust block
  * tanatonium nugget item
  * tanatonium ingot item
  * tanatonium block
  * imitatium ore block
  * imitatium nugget item
  * imitatium ingot item
  * imitatium block
  * perturbance amulet
* added fluids
  * molten tanatonium
* added materials
  * tanatonium
  * imitatium
* added traits
  * eroding
  * annihilating
  * degenerating
  * recombining
  * aquatic
* added potion effects
  * eroded

## 0.3.3.0 _2020-06-08 18:00_

* now you need to wear related baubles to see the fallen soul items
* modified modifiers
  * drilled
    * more durability limitation are reduced per level now(-650 -> -850)
    * cannot coexist with modifiers duritos, dense, diamond, emerald now
* modified ore generations
  * aventurine @cloud
  * lava crystal @nether(-netherrack)
  * lizanite @nether(-netherrack)
  * vibrating crystal @end(-endstone)
* added blocks and items
  * creep soil block
* added traits
  * eddying
* added potion effects
  * eddying

## 0.3.2.0 _2020-05-31 17:00_

* now we need Baubles as dependence
* added soul gathering and storing system
* now some blocks can be used as beacon base
* removed blocks
  * searing vent block
* removed traits
  * stone phasing
* added blocks and items
  * soul
  * soul beacon
  * iron amulet
  * soul stone amulet
  * infinite soul stone amulet(creative mode only)
  * leather belt
  * stone phasing belt
  * chloroplast dressing
  * stone dust item
  * stone-coal mixed dust item
* added traits
  * photosynthetic(armor)
* now the following traits need to consume soul
  * life inspiring
  * life inspiring(armor)

## 0.3.1.0 _2020-05-27 22:30_

* added materials
  * torrential crystal
* added traits
  * turbulent(armor)

## 0.3.0.2 _2020-05-26 17:00_

* added blocks and items
  * solid dirt ore block
  * solid sand ore block
  * solid stone ore block
  * colorful glass block
  * torrential thruster
  * tube item
  * disposable air tank item
  * air tank item
  * air pump block
  * air bomb item
* added traits
  * tube upgraded(armor)
  * torrential
  * hydrating(armor)
* added potion effects
  * bubbling
  * turbulent

## 0.3.0.1 _2020-05-20 18:00_

* rewrote world generation
* added items
  * mesh item
  * buoy item
  * torrential crystal item
  * torrential crystal ore block
  * torrential crystal block
  * blackrock dust item
* added traits
  * meshing(armor)
  * buoyant(armor)
  * sovereign
* updated rendering information of materials
* modified some textures

## 0.3.0.0 _2020-05-05 19:00_
