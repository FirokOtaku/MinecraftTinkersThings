package firok.tiths.common;

import firok.tiths.util.*;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerTraits;

import static firok.tiths.common.Traits.*;
import static firok.tiths.util.Keys.*;
import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;
import static slimeknights.tconstruct.tools.TinkerTraits.*;

@SuppressWarnings("unused")
public class TCMaterials
{
	@Compo(name=nameImmersedSilver,fluid=nameImmersedSilver)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material immersedSilver=new Material(nameImmersedSilver, colorImmersedSilver);


	@Compo(name=nameMithril,fluid=nameMithril)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material mithril=new Material(nameMithril, colorMithril);

	@Compo(name=nameAdamantine,fluid=nameAdamantine)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material adamantine=new Material(nameAdamantine, colorAdamantine);

	@Compo(name=nameBlackrock,fluid=nameBlackrock)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material blackrock=new Material(nameBlackrock, colorBlackrock);

	@Compo(name=nameInertWitherium,fluid=nameInertWitherium)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material inertWitherium=new Material(nameInertWitherium, colorInertWitherium);

	@Compo(name=nameWitherium,fluid=nameWitherium)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material witherium=new Material(nameWitherium, colorWitherium);

	@Compo(name=nameRoyalAlloy,fluid=nameRoyalAlloy)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material royalAlloy=new Material(nameRoyalAlloy, colorRoyalAlloy)
			.addTrait(luxurious)
			.addTrait(TinkerTraits.magnetic2);

	@Compo(name= nameStellarium,fluid= nameStellarium)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material stellarium=new Material(nameStellarium, colorStellarium)
			.addTrait(radiant);

	@Compo(name=nameSpiderLeg)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoExtra(extraDurability = 100)
	public static Material spiderLeg=new Material(nameSpiderLeg, colorSpiderLeg)
			.addTrait(TinkerTraits.sharp)
			.addTrait(TinkerTraits.poisonous);

	@Compo(name=nameHardSpiderLeg)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoExtra(extraDurability = 100)
	public static Material hardSpiderLeg=new Material(nameHardSpiderLeg, colorHardSpiderLeg)
			.addTrait(TinkerTraits.sharp);

	@Compo(name=nameCinnabar,fluid=nameCinnabar)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material cinnabar=new Material(nameCinnabar,colorCinnabar)
			.addTrait(TinkerTraits.poisonous, HEAD);

//	@Compo(name= nameGlass, item="glass",fluid="glass")
//	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
//	@CompoHandle(modifier = 0.8f, durability = 100)
//	@CompoExtra(extraDurability = 100)
//	public static Material glass=new Material(nameGlass, colorGlass)
//			.addTrait(cheapskate)
//			.addTrait(lightweight);

	@Compo(name= nameGlass) // , item="consolidated_glass"
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material consolidatedGlass=new Material(nameConsolidatedGlass, colorConsolidatedGlass)
			.addTrait(cheap)
			.addTrait(lightweight);

	@Compo(name= nameBrokenIce, item=nameBrokenIce,castatble = false, craftable = true)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material brokenIce=new Material(nameBrokenIce,colorBrokenIce)
			.addTrait(cheap)
			.addTrait(icy);

	@Compo(name= nameShell, item=nameShell,castatble = false,craftable = true)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material shell=new Material(nameShell,colorShell)
			.addTrait(sharp);

	@Compo(name= nameRuby, item=nameRuby,castatble = false,craftable = true)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material ruby=new Material(nameRuby,colorRuby);

	@Compo(name= nameCorundum, item=nameCorundum,castatble = false,craftable = true)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material corundum=new Material(nameCorundum,colorCorundum)
			.addTrait(clustering);

	public static Material cloud=new Material("cloud", Colors.Silver);
	public static Material cloudStorm=new Material("cloud_storm", Colors.DarkBlue);
	public static Material skyCrystal=new Material("sky_crystal", Colors.Aqua);
	public static Material stormCrystal=new Material("storm_crystal", Colors.DarkBlue);
	public static Material antiGraCrystal=new Material("anti_gra_crystal", Colors.DarkGreen);
	public static Material phantomCrystal=new Material("phantom_crystal", Colors.Gray);
	public static Material brokenBedrock=new Material("broken_bedrock", Colors.DarkGray);
	public static Material paradiseMetal=new Material("paradise_metal", Colors.Yellow);

}
