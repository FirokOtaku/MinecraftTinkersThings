package firok.tiths.common;

import firok.tiths.util.*;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerTraits;

import static firok.tiths.common.Keys.*;
import static firok.tiths.common.Traits.*;
import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;
import static slimeknights.tconstruct.tools.TinkerTraits.*;

@SuppressWarnings("all")
public class TCMaterials
{
	@Compo(nameImmersedSilver)
	@CompoHead(durability = 350,miningspeed = 4.5f,attack = 6.5f,harvestLevel = 2)
	@CompoHandle(modifier = 1.2f, durability = 85)
	@CompoExtra(extraDurability = 250)
	@CompoBow(drawSpeed = 1.15f,range = 0.95f,bonusDamage = 2.5f)
	@CompoArrowShaft(modifier = 0.85f,bonusAmmo = 15)
	public static final Material immersedSilver=new $Material(nameImmersedSilver, colorImmersedSilver)
			.setIconItem(Items.ingotImmersedSilver)
			.setFluid(Fluids.moltenImmersedSilver)
			.addTrait(insatiable);

	@Compo(nameMithril)
	@CompoHead(durability = 590,miningspeed = 6.5f,attack = 4.2f,harvestLevel = 4)
	@CompoHandle(modifier = 0.8f, durability = -80)
	@CompoExtra(extraDurability = 200)
	@CompoBow(drawSpeed = 1.65f, range=1.05f, bonusDamage = 2)
	@CompoArrowShaft(modifier = 1.65f,bonusAmmo = 25)
	public static final Material mithril=new $Material(nameMithril, colorMithril)
			.setIconItem(Items.ingotMithril)
			.setFluid(Fluids.moltenMithril)
			.addTrait(lightweight);

	@Compo(nameAdamantine)
	@CompoHead(durability = 680,miningspeed = 14.5f,attack = 5,harvestLevel = 4)
	@CompoHandle(modifier = 0.75f, durability = -150)
	@CompoExtra(extraDurability = 350)
	@CompoBow(drawSpeed = 0.8f, range = 1.45f, bonusDamage = 3.5f)
	@CompoArrowShaft(modifier = 1.55f,bonusAmmo = 35)
	public static final Material adamantine=new $Material(nameAdamantine, colorAdamantine)
			.setIconItem(Items.ingotAdamantine)
			.setFluid(Fluids.moltenAdamantine)
			.addTrait(unnatural)
			.addTrait(stonebound);

	@Compo(nameBlackrock)
	@CompoHead(durability = 150,miningspeed = 5f,attack = 3.5f,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = -20)
	@CompoExtra(extraDurability = 25)
	public static final Material blackrock=new $Material(nameBlackrock, colorBlackrock)
			.setIconItem(Items.blackrock)
			.addCraftableIngot(Items.blackrock)
			.addTrait(cheap)
			.addTrait(cheapskate);

	@Compo(nameInertWitherium)
	@CompoHead(durability = 1030,miningspeed = 2.6f,attack = 3.8f,harvestLevel = 2)
	@CompoHandle(modifier = 1.05f, durability = 370)
	@CompoExtra(extraDurability = 180)
	@CompoBow(drawSpeed = 1.15f,range = 1,bonusDamage = 0.5f)
	@CompoArrowShaft(modifier = 0.35f,bonusAmmo = 90)
	public static final Material inertWitherium=new $Material(nameInertWitherium, colorInertWitherium)
			.setIconItem(Items.ingotInertWitherium)
			.setFluid(Fluids.moltenInertWitherium);

	@Compo(nameWitherium)
	@CompoHead(durability = 690,miningspeed = 4.8f,attack = 9.4f,harvestLevel = 3)
	@CompoHandle(modifier = 0.95f, durability = 140)
	@CompoExtra(extraDurability = 40)
	@CompoBow(drawSpeed = 1.25f,range = 1.1f,bonusDamage = 2.5f)
	@CompoArrowShaft(modifier = 0.45f,bonusAmmo = 25)
	public static final Material witherium=new $Material(nameWitherium, colorWitherium)
			.setIconItem(Items.ingotWitherium)
			.setFluid(Fluids.moltenWitherium);


	@Compo(nameRoyalAlloy)
	@CompoHead(durability = 136,miningspeed = 9f,attack = 2.8f,harvestLevel = 2)
	@CompoHandle(modifier = 0.65f, durability = 45)
	@CompoExtra(extraDurability = 45)
	public static final Material royalAlloy=new $Material(nameRoyalAlloy, colorRoyalAlloy)
			.setIconItem(Items.ingotRoyalAlloy)
			.setFluid(Fluids.moltenRoyalAlloy)
			.addTrait(luxurious)
			.addTrait(magnetic2);

	@Compo(nameStellarium)
	@CompoHead(durability = 210,miningspeed = 9.5f,attack = 8.5f,harvestLevel = 4)
	@CompoExtra(extraDurability = 0)
	@CompoBow(drawSpeed = 0.9f,range = 0.7f,bonusDamage = 5)
	@CompoArrowShaft(modifier = 0.65f,bonusAmmo = 0)
	public static final Material stellarium=new $Material(nameStellarium, colorStellarium)
			.setIconItem(Items.ingotStellarium)
			.setFluid(Fluids.moltenStellarium)
			.addTrait(radiant);

	@Compo(nameStellariumObsidian)
	@CompoHead(durability = 540,miningspeed = 6.5f,attack = 7.5f,harvestLevel = 4)
	@CompoHandle(modifier = 1,durability = -250)
	@CompoExtra(extraDurability = 20)
	@CompoArrowShaft(modifier = 0.55f,bonusAmmo = 10)
	public static final Material stellariumObsidian=new $Material(nameStellariumObsidian, colorStellariumObsidian)
			.setIconItem(Blocks.blockStellariumObsidian)
			.addCraftableIngot(Blocks.blockStellariumObsidian)
			.addTrait(radiant);

	@Compo(nameHothium)
	@CompoHead(durability = 760,miningspeed = 6.5f,attack = 7,harvestLevel = 4)
	@CompoHandle(modifier = 0.85f, durability = 145)
	@CompoExtra(extraDurability = 45)
	@CompoBow(drawSpeed = 0.95f,range=1.2f,bonusDamage = 3.5f)
	public static final Material hothium=new $Material(nameHothium, colorHothium)
			.setIconItem(Items.ingotHothium)
			.setFluid(Fluids.moltenHothium)
			.addTrait(Traits.extremeFreezing);

	@Compo(nameSpiderLeg)
	@CompoHead(durability = 45,miningspeed = 0.5f,attack = 3,harvestLevel = 0)
	public static final Material spiderLeg=new $Material(nameSpiderLeg, colorSpiderLeg)
			.setIconItem(Items.spiderLeg)
			.addCraftableIngot(Items.spiderLeg)
			.addTrait(TinkerTraits.sharp)
			.addTrait(TinkerTraits.poisonous);

	@Compo(nameHardSpiderLeg)
	@CompoHead(durability = 75,miningspeed = 0.9f,attack = 4.5f,harvestLevel = 0)
	@CompoHandle(modifier = 0.25f,durability = 30)
	public static final Material hardSpiderLeg=new $Material(nameHardSpiderLeg, colorHardSpiderLeg)
			.setIconItem(Items.hardSpiderLeg)
			.addCraftableIngot(Items.hardSpiderLeg)
			.addTrait(TinkerTraits.sharp);

//	@Compo(nameCinnabar)
//	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
//	@CompoHandle(modifier = 0.8f, durability = 100)
//	@CompoExtra(extraDurability = 100)
//	public static final Material cinnabar=new $Material(nameCinnabar,colorCinnabar)
//			.setIconItem(Items.cinnabar)
//			.setFluid(Fluids.moltenCinnabar)
//			.addTrait(TinkerTraits.poisonous, HEAD);

	@Compo(nameConsolidatedGlass)
	@CompoHead(durability = 60,miningspeed = 3.5f,attack = 5.5f,harvestLevel = 0)
	@CompoExtra(extraDurability = -50)
	@CompoArrowShaft(modifier = 0.55f,bonusAmmo = 0)
	public static final Material consolidatedGlass=new $Material(nameConsolidatedGlass, colorConsolidatedGlass)
			.setIconItem(Blocks.blockConsolidatedGlass)
			.addCraftableIngot(Blocks.blockConsolidatedGlass)
			.addTrait(cheap)
			.addTrait(lightweight);

	@Compo(nameCoagulatedBloodSand)
	@CompoHead(durability = 45,miningspeed = 2.5f,attack = 2.8f,harvestLevel = 0)
	@CompoExtra(extraDurability = -95)
	public static final Material coagulatedBloodSand=new $Material(nameCoagulatedBloodSand, colorCoagulatedBloodSand)
			.setIconItem(Blocks.blockCoagulatedBloodSand)
			.addCraftableIngot(Blocks.blockCoagulatedBloodSand)
			.addTrait(hemolytic);

	@Compo(nameBrokenIce)
	@CompoHead(durability = 20,miningspeed = 1,attack = 1.8f,harvestLevel = 0)
	@CompoHandle(modifier = 0.6f, durability = 10)
	@CompoExtra(extraDurability = 15)
	public static final Material brokenIce=new $Material(nameBrokenIce,colorBrokenIce)
			.setIconItem(Items.brokenIce)
			.addCraftableItem(Items.brokenIce,4,Material.VALUE_Ingot)
			.addTrait(cheap)
			.addTrait(icy);

	@Compo(nameShell)
	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
	@CompoHandle(modifier = 0.8f, durability = 100)
	@CompoExtra(extraDurability = 100)
	public static final Material shell=new $Material(nameShell,colorShell)
			.setIconItem(Items.shell)
			.addCraftableIngot(Items.shell)
			.addTrait(sharp);

	@Compo(nameRuby)
	@CompoHead(durability = 1450,miningspeed = 7.5f,attack = 7,harvestLevel = 3)
	@CompoHandle(modifier = 1.3f, durability = 50)
	@CompoExtra(extraDurability = 350)
	public static final Material ruby=new $Material(nameRuby,colorRuby)
			.setIconItem(Items.ruby)
			.addCraftableIngot(Items.ruby)
			.addTrait(established)
			.addTrait(dense);

	@Compo(nameCorundum)
	@CompoHead(durability = 450,miningspeed = 6.5f,attack = 5.5f,harvestLevel = 3)
	@CompoHandle(modifier = 1.05f, durability = -50)
	@CompoExtra(extraDurability = 200)
	public static final Material corundum=new $Material(nameCorundum,colorCorundum)
			.setIconItem(Items.corundum)
			.addCraftableIngot(Items.corundum)
			.addTrait(maiming, HEAD)
			.addTrait(clustering);

	@Compo(nameTitanium)
	@CompoHead(durability = 612,miningspeed = 6.5f,attack = 3,harvestLevel = 2)
	@CompoHandle(modifier = 0.95f, durability = 180)
	@CompoExtra(extraDurability = 150)
	@CompoBow(drawSpeed = 0.45f,range = 1.7f,bonusDamage = 4)
	@CompoArrowShaft(modifier = 1.35f,bonusAmmo = 25)
	public static final Material titanium=new $Material(nameTitanium,colorTitanium)
			.setIconItem(Items.ingotTitanium)
			.setFluid(Fluids.moltenTitanium)
			.addTrait(sharp)
			.addTrait(lightweight);

	@Compo(namePolarium)
	@CompoHead(durability = 620,miningspeed = 4.8f,attack = 4.9f,harvestLevel = 4)
	@CompoHandle(modifier = 1.1f, durability = 90)
	@CompoExtra(extraDurability = 90)
	@CompoBow(drawSpeed = 0.9f,range = 1.25f,bonusDamage = 2.5f)
	@CompoArrowShaft(modifier = 1.25f,bonusAmmo = 35)
	public static final Material polarium=new $Material(namePolarium,colorPolarium)
			.setIconItem(Items.ingotPolarium)
			.setFluid(Fluids.moltenPolarium)
			.addTrait(unnatural);

	@Compo(nameHalleium)
	@CompoHead(durability = 560,miningspeed = 7.8f,attack = 3.9f,harvestLevel = 4)
	@CompoHandle(modifier = 1.05f, durability = 140)
	@CompoExtra(extraDurability = 80)
	@CompoBow(drawSpeed = 1.45f,range = 0.65f,bonusDamage = 0.5f)
	@CompoArrowShaft(modifier = 1.1f,bonusAmmo = 45)
	public static final Material halleium=new $Material(nameHalleium,colorHalleium)
			.setIconItem(Items.ingotHalleium)
			.setFluid(Fluids.moltenHalleium)
			.addTrait(jagged)
			.addTrait(switching);

	@Compo(nameAltairium)
	@CompoHead(durability = 490,miningspeed = 5.8f,attack = 6.5f,harvestLevel = 4)
	@CompoHandle(modifier = 1.25f, durability = 55)
	@CompoExtra(extraDurability = 25)
	@CompoBow(drawSpeed = 0.85f,range=1.15f,bonusDamage = 4)
	@CompoArrowShaft(modifier = 1f,bonusAmmo = 40)
	public static final Material altairium=new $Material(nameAltairium,colorAltairium)
			.setIconItem(Items.ingotAltairium)
			.setFluid(Fluids.moltenAltairium)
			.addTrait(starDashing);

	@Compo(nameCocoa)
	@CompoHead(durability = 30,miningspeed = 0.8f,attack = 1.2f,harvestLevel = 0)
	@CompoHandle(modifier = 0.4f, durability = -20)
	@CompoExtra(extraDurability = 15)
	public static final Material cocoa=new $Material(nameCocoa,colorCocoa)
			.setIconItem(Items.ingotCocoa)
			.setFluid(Fluids.moltenCocoa)
			.addTrait(tasty);

	@Compo(nameNitre)
	@CompoHead(durability = 140,miningspeed = 2.0f,attack = 4.4f,harvestLevel = 1)
	@CompoHandle(modifier = 0.6f, durability = -60)
	@CompoExtra(extraDurability = 40)
	public static final Material nitre=new $Material(nameNitre,colorNitre)
			.setIconItem(Items.nitre)
			.addCraftableIngot(Items.nitre)
			.addTrait(soluble)
			.addTrait(jagged);

	@Compo(nameIcelandSpar)
	@CompoHead(durability = 340,miningspeed = 1.5f,attack = 5.6f,harvestLevel = 1)
	@CompoHandle(modifier = 0.65f, durability = -200)
	@CompoExtra(extraDurability = 80)
	public static final Material icelandSpar=new $Material(nameIcelandSpar,colorIcelandSpar)
			.setIconItem(Items.icelandSpar)
			.addCraftableIngot(Items.icelandSpar)
			.addTrait(birefringent);

	@Compo(namePyrophyllite)
	@CompoHead(durability = 380,miningspeed = 1.9f,attack = 4.5f,harvestLevel = 1)
	@CompoHandle(modifier = 0.75f, durability = 250)
	@CompoExtra(extraDurability = 65)
	public static final Material pyrophyllite=new $Material(namePyrophyllite,colorPyrophyllite)
			.setIconItem(Items.pyrophyllite)
			.addCraftableIngot(Items.pyrophyllite)
			.addTrait(ecological);

	@Compo(nameSpinel)
	@CompoHead(durability = 1250,miningspeed = 6.5f,attack = 8,harvestLevel = 3)
	@CompoHandle(modifier = 1.25f, durability = 150)
	@CompoExtra(extraDurability = 450)
	public static final Material spinel=new $Material(nameSpinel,colorSpinel)
			.setIconItem(Items.spinel)
			.addCraftableIngot(Items.spinel)
			.addTrait(shaking);

	@Compo(nameTalcum)
	@CompoHead(durability = 140,miningspeed = 3.5f,attack = 3.5f,harvestLevel = 1)
	@CompoExtra(extraDurability = 190)
	public static final Material talcum=new $Material(nameTalcum,colorTalcum)
			.setIconItem(Items.talcum)
			.addCraftableIngot(Items.talcum)
			.addTrait(writable);

	@Compo(nameTourmaline)
	@CompoHead(durability = 420,miningspeed = 6f,attack = 6.5f,harvestLevel = 2)
	@CompoHandle(modifier = 0.45f, durability = -250)
	@CompoExtra(extraDurability = 180)
	public static final Material tourmaline=new $Material(nameTourmaline,colorTourmaline)
			.setIconItem(Items.tourmaline)
			.addCraftableIngot(Items.tourmaline)
			.addTrait(pyroelectric);

	@Compo(nameTonium)
	@CompoHead(durability = 1980,miningspeed = 3.5f,attack = 9.5f,harvestLevel = 4)
	@CompoHandle(modifier = 1.15f, durability = 280)
	@CompoExtra(extraDurability = 490)
	@CompoBow(drawSpeed = 0.4f,range = 2.85f,bonusDamage = 6.5f)
	@CompoArrowShaft(modifier = 0.35f,bonusAmmo = 65)
	public static final Material tonium=new $Material(nameTonium,colorTonium)
			.setIconItem(Items.ingotTonium)
			.setFluid(Fluids.moltenTonium)
			.addTrait(repressing);

	@Compo(nameMeteorolite)
	@CompoHead(durability = 940,miningspeed = 5.5f,attack = 6.5f,harvestLevel = 3)
	@CompoHandle(modifier = 0.75f, durability = 20)
	@CompoExtra(extraDurability = 140)
	public static final Material meteorolite=new $Material(nameMeteorolite,colorMeteorolite)
			.setIconItem(Blocks.blockMeteorolite)
			.addCraftableIngot(Blocks.blockMeteorolite)
			.addTrait(alien);

	public static Material cloud=new Material("cloud", Colors.Silver);
	public static Material cloudStorm=new Material("cloud_storm", Colors.DarkBlue);
	public static Material skyCrystal=new Material("sky_crystal", Colors.Aqua);
	public static Material stormCrystal=new Material("storm_crystal", Colors.DarkBlue);
	public static Material antiGraCrystal=new Material("anti_gra_crystal", Colors.DarkGreen);
	public static Material phantomCrystal=new Material("phantom_crystal", Colors.Gray);
	public static Material brokenBedrock=new Material("broken_bedrock", Colors.DarkGray);
	public static Material paradiseMetal=new Material("paradise_metal", Colors.Yellow);

}
