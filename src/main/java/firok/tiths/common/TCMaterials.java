package firok.tiths.common;

import firok.tiths.util.*;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerTraits;

import static firok.tiths.common.Keys.*;
import static firok.tiths.common.Traits.*;
import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;
import static slimeknights.tconstruct.tools.TinkerTraits.*;

@SuppressWarnings("unused")
public class TCMaterials
{
	@Compo(name=nameImmersedSilver,fluid=nameImmersedSilver)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material immersedSilver=new Material(nameImmersedSilver, colorImmersedSilver);


	@Compo(name=nameMithril,fluid=nameMithril)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material mithril=new Material(nameMithril, colorMithril);

	@Compo(name=nameAdamantine,fluid=nameAdamantine)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material adamantine=new Material(nameAdamantine, colorAdamantine)
			.addTrait(starDashing);

	@Compo(name=nameBlackrock,fluid=nameBlackrock)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material blackrock=new Material(nameBlackrock, colorBlackrock);

	@Compo(name=nameInertWitherium,fluid=nameInertWitherium)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material inertWitherium=new Material(nameInertWitherium, colorInertWitherium);

	@Compo(name=nameWitherium,fluid=nameWitherium)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material witherium=new Material(nameWitherium, colorWitherium);

	@Compo(name=nameRoyalAlloy,fluid=nameRoyalAlloy)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material royalAlloy=new Material(nameRoyalAlloy, colorRoyalAlloy)
			.addTrait(luxurious)
			.addTrait(TinkerTraits.magnetic2);

	@Compo(name= nameStellarium,fluid= nameStellarium)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material stellarium=new Material(nameStellarium, colorStellarium)
			.addTrait(radiant);

	@Compo(name=nameSpiderLeg)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoExtra(extraDurability = 100)
	public static final Material spiderLeg=new Material(nameSpiderLeg, colorSpiderLeg)
			.addTrait(TinkerTraits.sharp)
			.addTrait(TinkerTraits.poisonous);

	@Compo(name=nameHardSpiderLeg)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoExtra(extraDurability = 100)
	public static final Material hardSpiderLeg=new Material(nameHardSpiderLeg, colorHardSpiderLeg)
			.addTrait(TinkerTraits.sharp);

	@Compo(name=nameCinnabar,fluid=nameCinnabar)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material cinnabar=new Material(nameCinnabar,colorCinnabar)
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
	public static final Material consolidatedGlass=new Material(nameConsolidatedGlass, colorConsolidatedGlass)
			.addTrait(cheap)
			.addTrait(lightweight);

	@Compo(name= nameBrokenIce, item=nameBrokenIce,castatble = false, craftable = true)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material brokenIce=new Material(nameBrokenIce,colorBrokenIce)
			.addTrait(cheap)
			.addTrait(icy);

	@Compo(name= nameShell, item=nameShell,castatble = false,craftable = true)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material shell=new Material(nameShell,colorShell)
			.addTrait(sharp);

	@Compo(name= nameRuby, item=nameRuby,castatble = false,craftable = true)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material ruby=new Material(nameRuby,colorRuby);

	@Compo(name= nameCorundum, item=nameCorundum,castatble = false,craftable = true)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material corundum=new Material(nameCorundum,colorCorundum)
			.addTrait(clustering);

	@Compo(name= nameTitanium, item=nameTitanium,castatble = true,craftable = false)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material titanium=new Material(nameTitanium,colorTitanium)
			.addTrait(sharp)
			.addTrait(lightweight);

	@Compo(name= namePolarium, item=namePolarium,castatble = true,craftable = false)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material polarium=new Material(namePolarium,colorPolarium);

	@Compo(name= nameHalleium, item=nameHalleium,castatble = true,craftable = false)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material halleium=new Material(nameHalleium,colorHalleium);

	@Compo(name= nameAltairium, item=nameAltairium,castatble = true,craftable = false)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material altairium=new Material(nameAltairium,colorAltairium);

	@Compo(name= nameCocoa, item=nameCocoa,castatble = true,craftable = false)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material cocoa=new Material(nameCocoa,colorCocoa);

	@Compo(name= nameNitre, item=nameNitre,castatble = false,craftable = true)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material nitre=new Material(nameNitre,colorNitre)
			.addTrait(soluble);
	static
	{
		nitre.addItem(Items.nitre);
	}

	@Compo(name= nameIcelandSpar, item=nameIcelandSpar,castatble = false,craftable = true)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material icelandSpar=new Material(nameIcelandSpar,colorIcelandSpar)
			.addTrait(birefringent);

	@Compo(name= namePyrophyllite, item=namePyrophyllite,castatble = false,craftable = true)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material pyrophyllite=new Material(namePyrophyllite,colorPyrophyllite);

	public static Material cloud=new Material("cloud", Colors.Silver);
	public static Material cloudStorm=new Material("cloud_storm", Colors.DarkBlue);
	public static Material skyCrystal=new Material("sky_crystal", Colors.Aqua);
	public static Material stormCrystal=new Material("storm_crystal", Colors.DarkBlue);
	public static Material antiGraCrystal=new Material("anti_gra_crystal", Colors.DarkGreen);
	public static Material phantomCrystal=new Material("phantom_crystal", Colors.Gray);
	public static Material brokenBedrock=new Material("broken_bedrock", Colors.DarkGray);
	public static Material paradiseMetal=new Material("paradise_metal", Colors.Yellow);

}
