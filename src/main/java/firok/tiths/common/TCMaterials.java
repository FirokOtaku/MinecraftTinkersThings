package firok.tiths.common;

import firok.tiths.util.*;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerTraits;

import static firok.tiths.common.Keys.*;
import static firok.tiths.common.Traits.*;
import static slimeknights.tconstruct.library.materials.Material.VALUE_Ingot;
import static slimeknights.tconstruct.library.materials.MaterialTypes.*;
import static slimeknights.tconstruct.tools.TinkerTraits.*;

@SuppressWarnings("all")
public class TCMaterials
{
	@Compo(nameImmersedSilver)
	@CompoHead(durability = 365,miningspeed = 4.47,attack = 6.6,harvestLevel = 2)
	@CompoHandle(modifier = 1.22, durability = 87)
	@CompoExtra(extraDurability = 250)
	@CompoBow(drawSpeed = 1.15,range = 0.95,bonusDamage = 2.5)
	@CompoArrowShaft(modifier = 0.85,bonusAmmo = 15)
	public static final Material immersedSilver=new $Material(nameImmersedSilver, colorImmersedSilver)
			.addItemAsIngot(Items.ingotImmersedSilver)
			.setFluid(Fluids.moltenImmersedSilver)
			.addTrait(insatiable);

	@Compo(nameMithril)
	@CompoHead(durability = 598,miningspeed = 6.74,attack = 4.24,harvestLevel = 4)
	@CompoHandle(modifier = 0.8, durability = -80)
	@CompoExtra(extraDurability = 207)
	@CompoBow(drawSpeed = 1.65, range=1.05, bonusDamage = 2)
	@CompoArrowShaft(modifier = 1.65,bonusAmmo = 25)
	public static final Material mithril=new $Material(nameMithril, colorMithril)
			.addItemAsIngot(Items.ingotMithril)
			.setFluid(Fluids.moltenMithril)
			.addTrait(lightweight);

	@Compo(nameAdamantine)
	@CompoHead(durability = 683,miningspeed = 14.43,attack = 5.42,harvestLevel = 4)
	@CompoHandle(modifier = 0.79, durability = -150)
	@CompoExtra(extraDurability = 350)
	@CompoBow(drawSpeed = 0.8f, range = 1.45, bonusDamage = 3.5)
	@CompoArrowShaft(modifier = 1.55,bonusAmmo = 35)
	public static final Material adamantine=new $Material(nameAdamantine, colorAdamantine)
			.addItemAsIngot(Items.ingotAdamantine)
			.setFluid(Fluids.moltenAdamantine)
			.addTrait(unnatural)
			.addTrait(stonebound);

	@Compo(nameBlackrock)
	@CompoHead(durability = 152,miningspeed = 4.36,attack = 3.52,harvestLevel = 1)
	@CompoHandle(modifier = 0.82, durability = -20)
	@CompoExtra(extraDurability = 25)
	public static final Material blackrock=new $Material(nameBlackrock, colorBlackrock)
			.addItemAsIngot(Items.blackrock)
			.addTrait(cheap)
			.addTrait(cheapskate);

	@Compo(nameInertWitherium)
	@CompoHead(durability = 1030,miningspeed = 2.6,attack = 3.8,harvestLevel = 2)
	@CompoHandle(modifier = 1.05, durability = 370)
	@CompoExtra(extraDurability = 180)
	@CompoBow(drawSpeed = 1.15,range = 1,bonusDamage = 0.5)
	@CompoArrowShaft(modifier = 0.35,bonusAmmo = 90)
	public static final Material inertWitherium=new $Material(nameInertWitherium, colorInertWitherium)
			.addItemAsIngot(Items.ingotInertWitherium)
			.setFluid(Fluids.moltenInertWitherium)
			.addTrait(withering);

	@Compo(nameWitherium)
	@CompoHead(durability = 691,miningspeed = 4.88,attack = 9.46,harvestLevel = 3)
	@CompoHandle(modifier = 0.95, durability = 140)
	@CompoExtra(extraDurability = 40)
	@CompoBow(drawSpeed = 1.25,range = 1.1,bonusDamage = 2.5)
	@CompoArrowShaft(modifier = 0.45,bonusAmmo = 25)
	public static final Material witherium=new $Material(nameWitherium, colorWitherium)
			.addItemAsIngot(Items.ingotWitherium)
			.setFluid(Fluids.moltenWitherium)
			.addTrait(lionheart)
			.addTrait(withering);


	@Compo(nameRoyalAlloy)
	@CompoHead(durability = 436,miningspeed = 8.79,attack = 2.83,harvestLevel = 2)
	@CompoHandle(modifier = 0.63, durability = 45)
	@CompoExtra(extraDurability = 45)
	public static final Material royalAlloy=new $Material(nameRoyalAlloy, colorRoyalAlloy)
			.addItemAsIngot(Items.ingotRoyalAlloy)
			.setFluid(Fluids.moltenRoyalAlloy)
			.addTrait(luxurious)
			.addTrait(magnetic2);

	@Compo(nameStellarium)
	@CompoHead(durability = 211,miningspeed = 9.55,attack = 8.54,harvestLevel = 4)
	@CompoExtra(extraDurability = 0)
	@CompoBow(drawSpeed = 0.9,range = 0.68,bonusDamage = 5)
	@CompoArrowShaft(modifier = 0.65,bonusAmmo = 0)
	public static final Material stellarium=new $Material(nameStellarium, colorStellarium)
			.addItemAsIngot(Items.ingotStellarium)
			.setFluid(Fluids.moltenStellarium)
			.addTrait(radiant);

	@Compo(nameStellariumObsidian)
	@CompoHead(durability = 540,miningspeed = 6.3,attack = 7.4,harvestLevel = 4)
	@CompoHandle(modifier = 1,durability = -250)
	@CompoExtra(extraDurability = 20)
	@CompoArrowShaft(modifier = 0.55,bonusAmmo = 10)
	public static final Material stellariumObsidian=new $Material(nameStellariumObsidian, colorStellariumObsidian)
			.addItemAsIngot(Blocks.blockStellariumObsidian)
			.addTrait(radiant);

	@Compo(nameHothium)
	@CompoHead(durability = 760,miningspeed = 6.2,attack = 7.6,harvestLevel = 4)
	@CompoHandle(modifier = 0.85, durability = 145)
	@CompoExtra(extraDurability = 45)
	@CompoBow(drawSpeed = 0.95f,range=1.2,bonusDamage = 3.5)
	public static final Material hothium=new $Material(nameHothium, colorHothium)
			.addItemAsIngot(Items.ingotHothium)
			.setFluid(Fluids.moltenHothium)
			.addTrait(Traits.extremeFreezing);

	@Compo(nameSpiderLeg)
	@CompoHead(durability = 45,miningspeed = 0.55,attack = 3.1,harvestLevel = 0)
	public static final Material spiderLeg=new $Material(nameSpiderLeg, colorSpiderLeg)
			.addItemAsIngot(Items.spiderLeg)
			.addTrait(TinkerTraits.sharp)
			.addTrait(TinkerTraits.poisonous);

	@Compo(nameHardSpiderLeg)
	@CompoHead(durability = 75,miningspeed = 0.9,attack = 4.5,harvestLevel = 0)
	@CompoHandle(modifier = 0.25,durability = 30)
	public static final Material hardSpiderLeg=new $Material(nameHardSpiderLeg, colorHardSpiderLeg)
			.addItemAsIngot(Items.hardSpiderLeg)
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
	@CompoHead(durability = 60,miningspeed = 3.8,attack = 5.6,harvestLevel = 0)
	@CompoExtra(extraDurability = -50)
	@CompoArrowShaft(modifier = 0.55,bonusAmmo = 0)
	public static final Material consolidatedGlass=new $Material(nameConsolidatedGlass, colorConsolidatedGlass)
			.addItemAsIngot(Blocks.blockConsolidatedGlass)
			.addTrait(cheap)
			.addTrait(lightweight);

	@Compo(nameCoagulatedBloodSand)
	@CompoHead(durability = 45,miningspeed = 2.43,attack = 2.79,harvestLevel = 0)
	@CompoExtra(extraDurability = -95)
	public static final Material coagulatedBloodSand=new $Material(nameCoagulatedBloodSand, colorCoagulatedBloodSand)
			.addItemAsIngot(Blocks.blockCoagulatedBloodSand)
			.addTrait(hemolytic);

	@Compo(nameBrokenIce)
	@CompoHead(durability = 20,miningspeed = 1.6,attack = 1.8,harvestLevel = 0)
	@CompoHandle(modifier = 0.54, durability = 10)
	@CompoExtra(extraDurability = 15)
	public static final Material brokenIce=new $Material(nameBrokenIce,colorBrokenIce)
			.addTrait(cheap)
			.addTrait(icy);
	static
	{
		brokenIce.addItem(Items.brokenIce,4, VALUE_Ingot);
		brokenIce.setRepresentativeItem(Items.brokenIce);
	}

	@Compo(nameShell)
	@CompoHead(durability = 64,miningspeed = 4.2,attack = 5.7,harvestLevel = 1)
	@CompoHandle(modifier = 0.69, durability = 121)
	@CompoExtra(extraDurability = 23)
	public static final Material shell=new $Material(nameShell,colorShell)
			.addItemAsIngot(Items.shell)
			.addTrait(sharp);

	@Compo(nameRuby)
	@CompoHead(durability = 1440,miningspeed = 7.68,attack = 7.4,harvestLevel = 3)
	@CompoHandle(modifier = 1.3, durability = 50)
	@CompoExtra(extraDurability = 350)
	public static final Material ruby=new $Material(nameRuby,colorRuby)
			.addItemAsIngot(Items.ruby)
			.addTrait(established)
			.addTrait(dense);

	@Compo(nameCorundum)
	@CompoHead(durability = 450,miningspeed = 6.4,attack = 5.1,harvestLevel = 3)
	@CompoHandle(modifier = 1, durability = -50)
	@CompoExtra(extraDurability = 50)
	public static final Material corundum=new $Material(nameCorundum,colorCorundum)
			.addItemAsIngot(Items.corundum)
			.addTrait(maiming, HEAD)
			.addTrait(clustering,HEAD)
			.addTrait(clustering);

	@Compo(nameTitanium)
	@CompoHead(durability = 612,miningspeed = 6.6,attack = 3.6,harvestLevel = 2)
	@CompoHandle(modifier = 0.95, durability = 180)
	@CompoExtra(extraDurability = 150)
	@CompoBow(drawSpeed = 0.45,range = 1.7,bonusDamage = 4)
	@CompoArrowShaft(modifier = 1.35,bonusAmmo = 25)
	public static final Material titanium=new $Material(nameTitanium,colorTitanium)
			.addItemAsIngot(Items.ingotTitanium)
			.setFluid(Fluids.moltenTitanium)
			.addTrait(sharp)
			.addTrait(lightweight);

	@Compo(namePolarium)
	@CompoHead(durability = 620,miningspeed = 4.8,attack = 4.9,harvestLevel = 4)
	@CompoHandle(modifier = 1.1, durability = 90)
	@CompoExtra(extraDurability = 90)
	@CompoBow(drawSpeed = 0.9,range = 1.25,bonusDamage = 2.5)
	@CompoArrowShaft(modifier = 1.25,bonusAmmo = 35)
	public static final Material polarium=new $Material(namePolarium,colorPolarium)
			.addItemAsIngot(Items.ingotPolarium)
			.setFluid(Fluids.moltenPolarium)
			.addTrait(unnatural);

	@Compo(nameHalleium)
	@CompoHead(durability = 560,miningspeed = 7.8,attack = 3.9,harvestLevel = 4)
	@CompoHandle(modifier = 1.05, durability = 140)
	@CompoExtra(extraDurability = 80)
	@CompoBow(drawSpeed = 1.45,range = 0.65f,bonusDamage = 0.5)
	@CompoArrowShaft(modifier = 1.1,bonusAmmo = 45)
	public static final Material halleium=new $Material(nameHalleium,colorHalleium)
			.addItemAsIngot(Items.ingotHalleium)
			.setFluid(Fluids.moltenHalleium)
			.addTrait(jagged)
			.addTrait(switching);

	@Compo(nameAltairium)
	@CompoHead(durability = 490,miningspeed = 5.8,attack = 6.5,harvestLevel = 4)
	@CompoHandle(modifier = 1.25, durability = 55)
	@CompoExtra(extraDurability = 25)
	@CompoBow(drawSpeed = 0.85,range=1.15,bonusDamage = 4)
	@CompoArrowShaft(modifier = 1,bonusAmmo = 40)
	public static final Material altairium=new $Material(nameAltairium,colorAltairium)
			.addItemAsIngot(Items.ingotAltairium)
			.setFluid(Fluids.moltenAltairium)
			.addTrait(starDashing);

	@Compo(nameCocoa)
	@CompoHead(durability = 30,miningspeed = 0.8,attack = 1.2,harvestLevel = 0)
	@CompoHandle(modifier = 0.4, durability = -20)
	@CompoExtra(extraDurability = 15)
	public static final Material cocoa=new $Material(nameCocoa,colorCocoa)
			.addItemAsIngot(Items.ingotCocoa)
			.setFluid(Fluids.moltenCocoa)
			.addTrait(tasty);

	@Compo(nameNitre)
	@CompoHead(durability = 140,miningspeed = 2.3,attack = 4.4,harvestLevel = 1)
	@CompoHandle(modifier = 0.6, durability = -60)
	@CompoExtra(extraDurability = 40)
	public static final Material nitre=new $Material(nameNitre,colorNitre)
			.addItemAsIngot(Items.nitre)
			.addTrait(soluble)
			.addTrait(jagged);

	@Compo(nameIcelandSpar)
	@CompoHead(durability = 340,miningspeed = 1.5,attack = 5.6,harvestLevel = 1)
	@CompoHandle(modifier = 0.65, durability = -200)
	@CompoExtra(extraDurability = 80)
	public static final Material icelandSpar=new $Material(nameIcelandSpar,colorIcelandSpar)
			.addItemAsIngot(Items.icelandSpar)
			.addTrait(birefringent);

	@Compo(namePyrophyllite)
	@CompoHead(durability = 380,miningspeed = 1.9,attack = 4.5,harvestLevel = 1)
	@CompoHandle(modifier = 0.75, durability = 250)
	@CompoExtra(extraDurability = 65)
	public static final Material pyrophyllite=new $Material(namePyrophyllite,colorPyrophyllite)
			.addItemAsIngot(Items.pyrophyllite)
			.addTrait(ecological);

	@Compo(nameSpinel)
	@CompoHead(durability = 1250,miningspeed = 6.5,attack = 8,harvestLevel = 3)
	@CompoHandle(modifier = 1.25, durability = 150)
	@CompoExtra(extraDurability = 450)
	public static final Material spinel=new $Material(nameSpinel,colorSpinel)
			.addItemAsIngot(Items.spinel)
			.addTrait(shaking);

	@Compo(nameTalcum)
	@CompoHead(durability = 140,miningspeed = 3.1,attack = 3.3,harvestLevel = 1)
	@CompoExtra(extraDurability = 190)
	public static final Material talcum=new $Material(nameTalcum,colorTalcum)
			.addItemAsIngot(Items.talcum)
			.addTrait(writable);

	@Compo(nameTourmaline)
	@CompoHead(durability = 420,miningspeed = 5.9,attack = 6.5,harvestLevel = 2)
	@CompoHandle(modifier = 0.45, durability = -250)
	@CompoExtra(extraDurability = 180)
	public static final Material tourmaline=new $Material(nameTourmaline,colorTourmaline)
			.addItemAsIngot(Items.tourmaline)
			.addTrait(pyroelectric);

	@Compo(nameTonium)
	@CompoHead(durability = 1980,miningspeed = 3.5,attack = 9.5,harvestLevel = 4)
	@CompoHandle(modifier = 1.15, durability = 280)
	@CompoExtra(extraDurability = 490)
	@CompoBow(drawSpeed = 0.4,range = 2.85,bonusDamage = 6.5)
	@CompoArrowShaft(modifier = 0.35,bonusAmmo = 65)
	public static final Material tonium=new $Material(nameTonium,colorTonium)
			.addItemAsIngot(Items.ingotTonium)
			.addTrait(repressing);

	@Compo(nameMeteorolite)
	@CompoHead(durability = 940,miningspeed = 5.5,attack = 6.5,harvestLevel = 3)
	@CompoHandle(modifier = 0.75f, durability = 20)
	@CompoExtra(extraDurability = 140)
	public static final Material meteorolite=new $Material(nameMeteorolite,colorMeteorolite)
			.addItemAsIngot(Blocks.blockMeteorolite)
			.addTrait(alien);

	@Compo(nameCoal)
	@CompoHead(durability = 50,miningspeed = 0.5,attack = 1,harvestLevel = 0)
	@CompoHandle(modifier = 0.5,durability = -100)
	@CompoExtra(extraDurability = 150)
	public static final Material coal=new $Material(nameCoal,colorCoal)
			.addItemAsIngot(net.minecraft.init.Items.COAL)
			.addTrait(carbonizing);

	@Compo(nameSunStone)
	@CompoHead(durability = 545,miningspeed = 5.75,attack = 6.25,harvestLevel = 3)
	@CompoHandle(modifier = 1.1, durability = 50)
	@CompoExtra(extraDurability = 70)
	@CompoBow(drawSpeed = 2.75,range=1,bonusDamage = 4)
	public static final Material sunStone=new $Material(nameSunStone,colorSunStone)
			.addItemAsIngot(Items.sunStone)
			.addTrait(sunPower)
			.addTrait(flammable,HEAD)
			.addTrait(sunPower,HEAD);

	@Compo(nameMoonStone)
	@CompoHead(durability = 545,miningspeed = 6.25,attack = 5.75,harvestLevel = 3)
	@CompoHandle(modifier = 1.1, durability = 50)
	@CompoExtra(extraDurability = 70)
	@CompoBow(drawSpeed = 2.75,range=1,bonusDamage = 4)
	public static final Material moonStone=new $Material(nameMoonStone,colorMoonStone)
			.addItemAsIngot(Items.moonStone)
			.addTrait(moonPower)
			.addTrait(moonlight,HEAD)
			.addTrait(moonPower,HEAD);

	@Compo(nameEnderDragonSquama)
	@CompoHead(durability = 675,miningspeed = 5.8, attack = 6.7,harvestLevel = 2)
	@CompoHandle(modifier = 1.05, durability = 40)
	@CompoExtra(extraDurability = 120)
	@CompoFletching(accuracy = 0.8,modifier = 3.5f)
	public static final Material enderDragonSquama=new $Material(nameEnderDragonSquama,colorEnderDragonSquama)
			.addItemAsIngot(Items.enderDragonSquama)
			.addTrait(enderference)
			.addTrait(dense)
			.addTrait(lionheart,HEAD)
			.addTrait(enderference,HEAD);

	@Compo(nameFulgurite)
	@CompoHead(durability = 880,miningspeed = 5.36, attack = 7.5, harvestLevel = 4)
	@CompoExtra(extraDurability = 50)
	public static final Material fulgurite=new $Material(nameFulgurite,colorFulgurite)
			.addItemAsIngot(Blocks.blockFulgurite)
			.addTrait(thundering);

	@Compo(nameIrisia)
	@CompoHead(durability = 130,miningspeed = 7.7,attack = 9.3,harvestLevel = 4)
	@CompoHandle(modifier = 0.75,durability = 0)
	@CompoExtra(extraDurability = 20)
	public static final Material irisia=new $Material(nameIrisia,colorIrisia)
			.addItemAsIngot(Items.ingotIrisia)
			.addTrait(lightweight);

	@Compo(nameTreeRoot)
	@CompoHead(durability = 165,miningspeed = 3.6,attack = 3.2,harvestLevel = 1)
	@CompoHandle(modifier = 0.9,durability = 40)
	@CompoExtra(extraDurability = 25)
	@CompoBow(drawSpeed = 0.8,range=1.15,bonusDamage = 0.5)
	@CompoArrowShaft(modifier = 1.2,bonusAmmo = 25)
	public static final Material treeRoot=new $Material(nameTreeRoot,colorTreeRoot)
			.addItemAsIngot(Items.treeRoot)
			.addTrait(ecological)
			.addTrait(dense);

	@Compo(nameFlesh)
	@CompoHead(durability = 220,miningspeed = 3.8,attack = 4,harvestLevel = 0)
	@CompoHandle(modifier = 0.8,durability = -50)
	@CompoExtra(extraDurability = 59)
	public static final Material flesh=new $Material(nameFlesh,colorFlesh)
			.addItemAsIngot(Items.flesh)
			.addTrait(gluttonic);

	@Compo(nameBrokenBedrock)
	@CompoHead(durability = 1850,miningspeed = 0.5,attack = 1.5,harvestLevel = 4)
	@CompoHandle(modifier = 1.35,durability = 160)
	@CompoExtra(extraDurability = 270)
	public static Material brokenBedrock=new $Material(nameBrokenBedrock, colorBrokenBedrock)
			.addItemAsIngot(Items.brokenBedrock)
			.addTrait(dense,HEAD)
			.addTrait(duritos);

	@Compo(nameCloud)
	@CompoHead(durability = 20,miningspeed = 0.5,attack = 0,harvestLevel = 0)
	@CompoExtra(extraDurability = -300)
	public static Material cloud=new $Material(nameCloud, colorCloud)
			.addItemAsIngot(Blocks.blockCloud)
			.addTrait(squeaky,HEAD)
			.addTrait(lightweight,EXTRA);

	@Compo(nameOpal)
	@CompoHead(durability = 230,miningspeed = 3.1,attack = 3.7,harvestLevel = 0)
	@CompoHandle(modifier = 0.84,durability = -45)
	@CompoExtra(extraDurability = 115)
	public static Material opal=new $Material(nameOpal, colorOpal)
			.addItemAsIngot(Items.opal)
			.addTrait(gorgeous);

	@Compo(nameTopaz)
	@CompoHead(durability = 490,miningspeed = 5.6,attack = 4.4,harvestLevel = 3)
	@CompoHandle(modifier = 1.15,durability = 90)
	@CompoExtra(extraDurability = 280)
	public static Material topaz=new $Material(nameTopaz, colorTopaz)
			.addItemAsIngot(Items.topaz)
			.addTrait(peaceEnergetic);

	@Compo(nameTanzanite)
	@CompoHead(durability = 730,miningspeed = 6.2,attack = 6.9,harvestLevel = 3)
	@CompoHandle(modifier = 1.15,durability = 90)
	@CompoExtra(extraDurability = 310)
	public static Material tanzanite=new $Material(nameTanzanite, colorTanzanite)
			.addItemAsIngot(Items.tanzanite)
			.addTrait(hyper);

	@Compo(nameCordierite)
	@CompoHead(durability = 425,miningspeed = 5.9,attack = 3.9,harvestLevel = 3)
	@CompoHandle(modifier = 1.05,durability = 35)
	@CompoExtra(extraDurability = 215)
	public static Material cordierite=new $Material(nameCordierite, colorCordierite)
			.addItemAsIngot(Items.cordierite)
			.addTrait(dichroic);

	@Compo(namePrehnite)
	@CompoHead(durability = 370,miningspeed = 4.1f,attack = 3.3f,harvestLevel = 3)
	@CompoHandle(modifier = 0.95,durability = 20)
	@CompoExtra(extraDurability = 55)
	public static Material prehnite=new $Material(namePrehnite, colorPrehnite)
			.addItemAsIngot(Items.prehnite)
			.addTrait(lifeInspiring);

	@Compo(nameProustite)
	@CompoHead(durability = 290,miningspeed = 3.5,attack = 4.7,harvestLevel = 2)
	@CompoHandle(modifier = 0.85,durability = 80)
	@CompoExtra(extraDurability = 35)
	public static Material proustite=new $Material(nameProustite, colorProustite)
			.addItemAsIngot(Items.proustite);

	@Compo(nameOraclium)
	@CompoHead(durability = 740,miningspeed = 5.6,attack = 6.8,harvestLevel = 4)
	@CompoHandle(modifier = 1.05,durability = 140)
	@CompoExtra(extraDurability = 45)
	public static Material oraclium=new $Material(nameOraclium, colorOraclium)
			.addItemAsIngot(Items.ingotOraclium)
			.setFluid(Fluids.moltenOraclium)
			.addTrait(oracular);

	@Compo(nameVibratingCrystal)
	@CompoHead(durability = 800,miningspeed = 7.4,attack = 4.5,harvestLevel = 3)
	@CompoHandle(modifier = 0.7,durability = 230)
	@CompoExtra(extraDurability = 30)
	public static Material vibratingCrystal=new $Material(nameVibratingCrystal, colorVibratingCrystal)
			.addItemAsIngot(Items.vibratingCrystal)
			.addTrait(treasureDetecting);

	@Compo(nameLavaCrystal)
	@CompoHead(durability = 540,miningspeed = 6.5,attack = 6.4,harvestLevel = 3)
	@CompoHandle(modifier = 0.98,durability = 120)
	@CompoExtra(extraDurability = 45)
	public static Material lavaCrystal=new $Material(nameLavaCrystal, colorLavaCrystal)
			.addItemAsIngot(Items.lavaCrystal)
			.addTrait(flammable,HEAD)
			.addTrait(flammable,EXTRA)
			.addTrait(thermalGathering,HANDLE);

	@Compo(nameSteamium)
	@CompoHead(durability = 690,miningspeed = 5.5,attack = 6.5,harvestLevel = 2)
	@CompoHandle(modifier = 0.65,durability = 100)
	@CompoExtra(extraDurability = -80)
	public static Material steamium=new $Material(nameSteamium, colorSteamium)
			.addItemAsIngot(Items.ingotSteamium)
			.addTrait(steamy);

	@Compo(nameGrain)
	@CompoHead(durability = 240,miningspeed = 3.6,attack = 3.5,harvestLevel = 1)
	@CompoHandle(modifier = 1.05,durability = 10)
	@CompoExtra(extraDurability = 28)
	public static Material grain=new $Material(nameGrain, colorGrain)
			.addItemAsIngot(Items.ingotGrain)
			.addTrait(decoying);

	@Compo(nameAventurine)
	@CompoHead(durability = 720,miningspeed = 6,attack = 7.3,harvestLevel = 3)
	@CompoHandle(modifier = 0.9,durability = 40)
	@CompoExtra(extraDurability = 50)
	public static Material Aventurine=new $Material(nameAventurine, colorAventurine)
			.addItemAsIngot(Items.aventurine)
			.addTrait(staminaFocusing);

	public static Material cloudStorm=new Material("cloud_storm", Colors.DarkBlue);
	public static Material skyCrystal=new Material("sky_crystal", Colors.Aqua);
	public static Material stormCrystal=new Material("storm_crystal", Colors.DarkBlue);
	public static Material antiGraCrystal=new Material("anti_gra_crystal", Colors.DarkGreen);
	public static Material phantomCrystal=new Material("phantom_crystal", Colors.Gray);
	public static Material paradiseMetal=new Material("paradise_metal", Colors.Yellow);

}
