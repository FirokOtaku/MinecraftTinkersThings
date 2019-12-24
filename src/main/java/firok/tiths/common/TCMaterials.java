package firok.tiths.common;

import firok.tiths.util.*;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.shared.TinkerFluids;
import slimeknights.tconstruct.tools.TinkerTraits;

import static firok.tiths.common.Keys.*;
import static firok.tiths.common.Traits.*;
import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;
import static slimeknights.tconstruct.tools.TinkerTraits.*;

@SuppressWarnings("all")
public class TCMaterials
{
	@Compo(nameImmersedSilver)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material immersedSilver=new Material(nameImmersedSilver, colorImmersedSilver)
			.setFluid(Fluids.moltenImmersedSilver);

	@Compo(nameMithril)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material mithril=new Material(nameMithril, colorMithril)
			.setFluid(Fluids.moltenMithril);

	@Compo(nameAdamantine)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material adamantine=new Material(nameAdamantine, colorAdamantine)
			.setFluid(Fluids.moltenAdamantine);

	@Compo(nameBlackrock)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material blackrock=new $Material(nameBlackrock, colorBlackrock)
			.addCraftableIngot(Items.blackrock);

	@Compo(nameInertWitherium)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material inertWitherium=new Material(nameInertWitherium, colorInertWitherium)
			.setFluid(Fluids.moltenInertWitherium);

	@Compo(nameWitherium)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material witherium=new Material(nameWitherium, colorWitherium)
			.setFluid(Fluids.moltenWitherium);

	@Compo(nameRoyalAlloy)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material royalAlloy=new Material(nameRoyalAlloy, colorRoyalAlloy)
			.setFluid(Fluids.moltenRoyalAlloy)
			.addTrait(luxurious)
			.addTrait(magnetic2);

	@Compo(nameStellarium)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material stellarium=new Material(nameStellarium, colorStellarium)
			.setFluid(Fluids.moltenStellarium)
			.addTrait(radiant);

	@Compo(nameHothium)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material hothium=new Material(nameHothium, colorHothium)
			.setFluid(Fluids.moltenHothium)
			.addTrait(Traits.extremeFreezing);

	@Compo(nameSpiderLeg)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoExtra(extraDurability = 100)
	public static final Material spiderLeg=new $Material(nameSpiderLeg, colorSpiderLeg)
			.addCraftableIngot(Items.spiderLeg)
			.addTrait(TinkerTraits.sharp)
			.addTrait(TinkerTraits.poisonous);

	@Compo(nameHardSpiderLeg)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoExtra(extraDurability = 100)
	public static final Material hardSpiderLeg=new $Material(nameHardSpiderLeg, colorHardSpiderLeg)
			.addCraftableIngot(Items.hardSpiderLeg)
			.addTrait(TinkerTraits.sharp);

	@Compo(nameCinnabar)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material cinnabar=new Material(nameCinnabar,colorCinnabar)
			.setFluid(Fluids.moltenCinnabar)
			.addTrait(TinkerTraits.poisonous, HEAD);

	@Compo(nameGlass)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static Material glass=new Material(nameGlass, colorGlass)
			.setFluid(TinkerFluids.glass)
			.addTrait(cheapskate)
			.addTrait(lightweight);

	@Compo(nameConsolidatedGlass)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material consolidatedGlass=new $Material(nameConsolidatedGlass, colorConsolidatedGlass)
			.addCraftableIngot(Blocks.blockConsolidatedGlass)
			.addTrait(cheap)
			.addTrait(lightweight);

	@Compo(nameBrokenIce)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material brokenIce=new $Material(nameBrokenIce,colorBrokenIce)
			.addCraftableItem(Items.brokenIce,4,Material.VALUE_Ingot)
			.addTrait(cheap)
			.addTrait(icy);

	@Compo(nameShell)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material shell=new $Material(nameShell,colorShell)
			.addCraftableIngot(Items.shell)
			.addTrait(sharp);

	@Compo(nameRuby)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material ruby=new $Material(nameRuby,colorRuby)
			.addCraftableIngot(Items.ruby);

	@Compo(nameCorundum)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material corundum=new $Material(nameCorundum,colorCorundum)
			.addCraftableIngot(Items.corundum)
			.addTrait(clustering);

	@Compo(nameTitanium)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material titanium=new Material(nameTitanium,colorTitanium)
			.setFluid(Fluids.moltenTitanium)
			.addTrait(sharp)
			.addTrait(lightweight);

	@Compo(namePolarium)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material polarium=new Material(namePolarium,colorPolarium)
			.setFluid(Fluids.moltenPolarium);

	@Compo(nameHalleium)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material halleium=new Material(nameHalleium,colorHalleium)
			.setFluid(Fluids.moltenHalleium);

	@Compo(nameAltairium)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material altairium=new Material(nameAltairium,colorAltairium)
			.setFluid(Fluids.moltenAltairium);

	@Compo(nameCocoa)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material cocoa=new Material(nameCocoa,colorCocoa)
			.setFluid(Fluids.moltenCocoa);

	@Compo(nameNitre)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material nitre=new $Material(nameNitre,colorNitre)
			.addCraftableIngot(Items.nitre)
			.addTrait(soluble);

	@Compo(nameIcelandSpar)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material icelandSpar=new $Material(nameIcelandSpar,colorIcelandSpar)
			.addCraftableIngot(Items.icelandSpar)
			.addTrait(birefringent);

	@Compo(namePyrophyllite)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material pyrophyllite=new $Material(namePyrophyllite,colorPyrophyllite)
			.addCraftableIngot(Items.pyrophyllite);

	@Compo(nameSpinel)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material spinel=new $Material(nameSpinel,colorSpinel)
			.addCraftableIngot(Items.spinel)
			.addTrait(shaking);

	@Compo(nameTalcum)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material talcum=new $Material(nameTalcum,colorTalcum)
			.addCraftableIngot(Items.talcum);

	@Compo(nameTourmaline)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material tourmaline=new $Material(nameTourmaline,colorTourmaline)
			.addCraftableIngot(Items.tourmaline)
			.addTrait(pyroelectric);

	@Compo(nameTalcum)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material tonium=new $Material(nameTonium,colorTonium)
			.setFluid(Fluids.moltenTonium)
			.addTrait(repressing);

	@Compo(nameMeteorolite)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material meteorolite=new $Material(nameMeteorolite,colorMeteorolite)
			.addCraftableIngot(Blocks.blockMeteorolite);

	public static Material cloud=new Material("cloud", Colors.Silver);
	public static Material cloudStorm=new Material("cloud_storm", Colors.DarkBlue);
	public static Material skyCrystal=new Material("sky_crystal", Colors.Aqua);
	public static Material stormCrystal=new Material("storm_crystal", Colors.DarkBlue);
	public static Material antiGraCrystal=new Material("anti_gra_crystal", Colors.DarkGreen);
	public static Material phantomCrystal=new Material("phantom_crystal", Colors.Gray);
	public static Material brokenBedrock=new Material("broken_bedrock", Colors.DarkGray);
	public static Material paradiseMetal=new Material("paradise_metal", Colors.Yellow);

}
