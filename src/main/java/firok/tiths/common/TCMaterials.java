package firok.tiths.common;

import firok.tiths.util.*;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerTraits;

import static firok.tiths.common.Keys.*;
import static firok.tiths.common.Traits.*;
import static slimeknights.tconstruct.library.materials.Material.VALUE_Ingot;
import static slimeknights.tconstruct.library.materials.MaterialTypes.EXTRA;
import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;
import static slimeknights.tconstruct.tools.TinkerTraits.*;

@SuppressWarnings("all")
public class TCMaterials
{
	@Compo(nameImmersedSilver)
	@CompoHead(durability = 365,miningspeed = 4.47f,attack = 6.6f,harvestLevel = 2)
	@CompoHandle(modifier = 1.22f, durability = 87)
	@CompoExtra(extraDurability = 250)
	@CompoBow(drawSpeed = 1.15f,range = 0.95f,bonusDamage = 2.5f)
	@CompoArrowShaft(modifier = 0.85f,bonusAmmo = 15)
	public static final Material immersedSilver=new $Material(nameImmersedSilver, colorImmersedSilver)
			.addItemAsIngot(Items.ingotImmersedSilver)
			.setFluid(Fluids.moltenImmersedSilver)
			.addTrait(insatiable);

	@Compo(nameMithril)
	@CompoHead(durability = 598,miningspeed = 6.74f,attack = 4.24f,harvestLevel = 4)
	@CompoHandle(modifier = 0.8f, durability = -80)
	@CompoExtra(extraDurability = 207)
	@CompoBow(drawSpeed = 1.65f, range=1.05f, bonusDamage = 2)
	@CompoArrowShaft(modifier = 1.65f,bonusAmmo = 25)
	public static final Material mithril=new $Material(nameMithril, colorMithril)
			.addItemAsIngot(Items.ingotMithril)
			.setFluid(Fluids.moltenMithril)
			.addTrait(lightweight);

	@Compo(nameAdamantine)
	@CompoHead(durability = 683,miningspeed = 14.43f,attack = 5.42f,harvestLevel = 4)
	@CompoHandle(modifier = 0.79f, durability = -150)
	@CompoExtra(extraDurability = 350)
	@CompoBow(drawSpeed = 0.8f, range = 1.45f, bonusDamage = 3.5f)
	@CompoArrowShaft(modifier = 1.55f,bonusAmmo = 35)
	public static final Material adamantine=new $Material(nameAdamantine, colorAdamantine)
			.addItemAsIngot(Items.ingotAdamantine)
			.setFluid(Fluids.moltenAdamantine)
			.addTrait(unnatural)
			.addTrait(stonebound);

	@Compo(nameBlackrock)
	@CompoHead(durability = 152,miningspeed = 4.36f,attack = 3.52f,harvestLevel = 1)
	@CompoHandle(modifier = 0.82f, durability = -20)
	@CompoExtra(extraDurability = 25)
	public static final Material blackrock=new $Material(nameBlackrock, colorBlackrock)
			.addItemAsIngot(Items.blackrock)
			.addTrait(cheap)
			.addTrait(cheapskate);

	@Compo(nameInertWitherium)
	@CompoHead(durability = 1030,miningspeed = 2.6f,attack = 3.8f,harvestLevel = 2)
	@CompoHandle(modifier = 1.05f, durability = 370)
	@CompoExtra(extraDurability = 180)
	@CompoBow(drawSpeed = 1.15f,range = 1,bonusDamage = 0.5f)
	@CompoArrowShaft(modifier = 0.35f,bonusAmmo = 90)
	public static final Material inertWitherium=new $Material(nameInertWitherium, colorInertWitherium)
			.addItemAsIngot(Items.ingotInertWitherium)
			.setFluid(Fluids.moltenInertWitherium)
			.addTrait(withering);

	@Compo(nameWitherium)
	@CompoHead(durability = 691,miningspeed = 4.88f,attack = 9.46f,harvestLevel = 3)
	@CompoHandle(modifier = 0.95f, durability = 140)
	@CompoExtra(extraDurability = 40)
	@CompoBow(drawSpeed = 1.25f,range = 1.1f,bonusDamage = 2.5f)
	@CompoArrowShaft(modifier = 0.45f,bonusAmmo = 25)
	public static final Material witherium=new $Material(nameWitherium, colorWitherium)
			.addItemAsIngot(Items.ingotWitherium)
			.setFluid(Fluids.moltenWitherium)
			.addTrait(lionheart)
			.addTrait(withering);


	@Compo(nameRoyalAlloy)
	@CompoHead(durability = 436,miningspeed = 8.79f,attack = 2.83f,harvestLevel = 2)
	@CompoHandle(modifier = 0.63f, durability = 45)
	@CompoExtra(extraDurability = 45)
	public static final Material royalAlloy=new $Material(nameRoyalAlloy, colorRoyalAlloy)
			.addItemAsIngot(Items.ingotRoyalAlloy)
			.setFluid(Fluids.moltenRoyalAlloy)
			.addTrait(luxurious)
			.addTrait(magnetic2);

	@Compo(nameStellarium)
	@CompoHead(durability = 211,miningspeed = 9.55f,attack = 8.54f,harvestLevel = 4)
	@CompoExtra(extraDurability = 0)
	@CompoBow(drawSpeed = 0.9f,range = 0.68f,bonusDamage = 5)
	@CompoArrowShaft(modifier = 0.65f,bonusAmmo = 0)
	public static final Material stellarium=new $Material(nameStellarium, colorStellarium)
			.addItemAsIngot(Items.ingotStellarium)
			.setFluid(Fluids.moltenStellarium)
			.addTrait(radiant);

	@Compo(nameStellariumObsidian)
	@CompoHead(durability = 540,miningspeed = 6.3f,attack = 7.4f,harvestLevel = 4)
	@CompoHandle(modifier = 1,durability = -250)
	@CompoExtra(extraDurability = 20)
	@CompoArrowShaft(modifier = 0.55f,bonusAmmo = 10)
	public static final Material stellariumObsidian=new $Material(nameStellariumObsidian, colorStellariumObsidian)
			.addItemAsIngot(Blocks.blockStellariumObsidian)
			.addTrait(radiant);

	@Compo(nameHothium)
	@CompoHead(durability = 760,miningspeed = 6.2f,attack = 7.6f,harvestLevel = 4)
	@CompoHandle(modifier = 0.85f, durability = 145)
	@CompoExtra(extraDurability = 45)
	@CompoBow(drawSpeed = 0.95f,range=1.2f,bonusDamage = 3.5f)
	public static final Material hothium=new $Material(nameHothium, colorHothium)
			.addItemAsIngot(Items.ingotHothium)
			.setFluid(Fluids.moltenHothium)
			.addTrait(Traits.extremeFreezing);

	@Compo(nameSpiderLeg)
	@CompoHead(durability = 45,miningspeed = 0.55f,attack = 3.1f,harvestLevel = 0)
	public static final Material spiderLeg=new $Material(nameSpiderLeg, colorSpiderLeg)
			.addItemAsIngot(Items.spiderLeg)
			.addTrait(TinkerTraits.sharp)
			.addTrait(TinkerTraits.poisonous);

	@Compo(nameHardSpiderLeg)
	@CompoHead(durability = 75,miningspeed = 0.9f,attack = 4.5f,harvestLevel = 0)
	@CompoHandle(modifier = 0.25f,durability = 30)
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
	@CompoHead(durability = 60,miningspeed = 3.8f,attack = 5.6f,harvestLevel = 0)
	@CompoExtra(extraDurability = -50)
	@CompoArrowShaft(modifier = 0.55f,bonusAmmo = 0)
	public static final Material consolidatedGlass=new $Material(nameConsolidatedGlass, colorConsolidatedGlass)
			.addItemAsIngot(Blocks.blockConsolidatedGlass)
			.addTrait(cheap)
			.addTrait(lightweight);

	@Compo(nameCoagulatedBloodSand)
	@CompoHead(durability = 45,miningspeed = 2.43f,attack = 2.79f,harvestLevel = 0)
	@CompoExtra(extraDurability = -95)
	public static final Material coagulatedBloodSand=new $Material(nameCoagulatedBloodSand, colorCoagulatedBloodSand)
			.addItemAsIngot(Blocks.blockCoagulatedBloodSand)
			.addTrait(hemolytic);

	@Compo(nameBrokenIce)
	@CompoHead(durability = 20,miningspeed = 1.6f,attack = 1.8f,harvestLevel = 0)
	@CompoHandle(modifier = 0.54f, durability = 10)
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
	@CompoHead(durability = 64,miningspeed = 4.2f,attack = 5.7f,harvestLevel = 1)
	@CompoHandle(modifier = 0.69f, durability = 121)
	@CompoExtra(extraDurability = 23)
	public static final Material shell=new $Material(nameShell,colorShell)
			.addItemAsIngot(Items.shell)
			.addTrait(sharp);

	@Compo(nameRuby)
	@CompoHead(durability = 1440,miningspeed = 7.68f,attack = 7.4f,harvestLevel = 3)
	@CompoHandle(modifier = 1.3f, durability = 50)
	@CompoExtra(extraDurability = 350)
	public static final Material ruby=new $Material(nameRuby,colorRuby)
			.addItemAsIngot(Items.ruby)
			.addTrait(established)
			.addTrait(dense);

	@Compo(nameCorundum)
	@CompoHead(durability = 450,miningspeed = 6.4f,attack = 5.1f,harvestLevel = 3)
	@CompoHandle(modifier = 1.00f, durability = -50)
	@CompoExtra(extraDurability = 50)
	public static final Material corundum=new $Material(nameCorundum,colorCorundum)
			.addItemAsIngot(Items.corundum)
			.addTrait(maiming, HEAD)
			.addTrait(clustering,HEAD)
			.addTrait(clustering);

	@Compo(nameTitanium)
	@CompoHead(durability = 612,miningspeed = 6.6f,attack = 3.6f,harvestLevel = 2)
	@CompoHandle(modifier = 0.95f, durability = 180)
	@CompoExtra(extraDurability = 150)
	@CompoBow(drawSpeed = 0.45f,range = 1.7f,bonusDamage = 4)
	@CompoArrowShaft(modifier = 1.35f,bonusAmmo = 25)
	public static final Material titanium=new $Material(nameTitanium,colorTitanium)
			.addItemAsIngot(Items.ingotTitanium)
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
			.addItemAsIngot(Items.ingotPolarium)
			.setFluid(Fluids.moltenPolarium)
			.addTrait(unnatural);

	@Compo(nameHalleium)
	@CompoHead(durability = 560,miningspeed = 7.8f,attack = 3.9f,harvestLevel = 4)
	@CompoHandle(modifier = 1.05f, durability = 140)
	@CompoExtra(extraDurability = 80)
	@CompoBow(drawSpeed = 1.45f,range = 0.65f,bonusDamage = 0.5f)
	@CompoArrowShaft(modifier = 1.1f,bonusAmmo = 45)
	public static final Material halleium=new $Material(nameHalleium,colorHalleium)
			.addItemAsIngot(Items.ingotHalleium)
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
			.addItemAsIngot(Items.ingotAltairium)
			.setFluid(Fluids.moltenAltairium)
			.addTrait(starDashing);

	@Compo(nameCocoa)
	@CompoHead(durability = 30,miningspeed = 0.8f,attack = 1.2f,harvestLevel = 0)
	@CompoHandle(modifier = 0.4f, durability = -20)
	@CompoExtra(extraDurability = 15)
	public static final Material cocoa=new $Material(nameCocoa,colorCocoa)
			.addItemAsIngot(Items.ingotCocoa)
			.setFluid(Fluids.moltenCocoa)
			.addTrait(tasty);

	@Compo(nameNitre)
	@CompoHead(durability = 140,miningspeed = 2.3f,attack = 4.4f,harvestLevel = 1)
	@CompoHandle(modifier = 0.6f, durability = -60)
	@CompoExtra(extraDurability = 40)
	public static final Material nitre=new $Material(nameNitre,colorNitre)
			.addItemAsIngot(Items.nitre)
			.addTrait(soluble)
			.addTrait(jagged);

	@Compo(nameIcelandSpar)
	@CompoHead(durability = 340,miningspeed = 1.5f,attack = 5.6f,harvestLevel = 1)
	@CompoHandle(modifier = 0.65f, durability = -200)
	@CompoExtra(extraDurability = 80)
	public static final Material icelandSpar=new $Material(nameIcelandSpar,colorIcelandSpar)
			.addItemAsIngot(Items.icelandSpar)
			.addTrait(birefringent);

	@Compo(namePyrophyllite)
	@CompoHead(durability = 380,miningspeed = 1.9f,attack = 4.5f,harvestLevel = 1)
	@CompoHandle(modifier = 0.75f, durability = 250)
	@CompoExtra(extraDurability = 65)
	public static final Material pyrophyllite=new $Material(namePyrophyllite,colorPyrophyllite)
			.addItemAsIngot(Items.pyrophyllite)
			.addTrait(ecological);

	@Compo(nameSpinel)
	@CompoHead(durability = 1250,miningspeed = 6.5f,attack = 8,harvestLevel = 3)
	@CompoHandle(modifier = 1.25f, durability = 150)
	@CompoExtra(extraDurability = 450)
	public static final Material spinel=new $Material(nameSpinel,colorSpinel)
			.addItemAsIngot(Items.spinel)
			.addTrait(shaking);

	@Compo(nameTalcum)
	@CompoHead(durability = 140,miningspeed = 3.1f,attack = 3.3f,harvestLevel = 1)
	@CompoExtra(extraDurability = 190)
	public static final Material talcum=new $Material(nameTalcum,colorTalcum)
			.addItemAsIngot(Items.talcum)
			.addTrait(writable);

	@Compo(nameTourmaline)
	@CompoHead(durability = 420,miningspeed = 5.9f,attack = 6.5f,harvestLevel = 2)
	@CompoHandle(modifier = 0.45f, durability = -250)
	@CompoExtra(extraDurability = 180)
	public static final Material tourmaline=new $Material(nameTourmaline,colorTourmaline)
			.addItemAsIngot(Items.tourmaline)
			.addTrait(pyroelectric);

	@Compo(nameTonium)
	@CompoHead(durability = 1980,miningspeed = 3.5f,attack = 9.5f,harvestLevel = 4)
	@CompoHandle(modifier = 1.15f, durability = 280)
	@CompoExtra(extraDurability = 490)
	@CompoBow(drawSpeed = 0.4f,range = 2.85f,bonusDamage = 6.5f)
	@CompoArrowShaft(modifier = 0.35f,bonusAmmo = 65)
	public static final Material tonium=new $Material(nameTonium,colorTonium)
			.addItemAsIngot(Items.ingotTonium)
			.addTrait(repressing);

	@Compo(nameMeteorolite)
	@CompoHead(durability = 940,miningspeed = 5.5f,attack = 6.5f,harvestLevel = 3)
	@CompoHandle(modifier = 0.75f, durability = 20)
	@CompoExtra(extraDurability = 140)
	public static final Material meteorolite=new $Material(nameMeteorolite,colorMeteorolite)
			.addItemAsIngot(Blocks.blockMeteorolite)
			.addTrait(alien);

	@Compo(nameCoal)
	@CompoHead(durability = 50,miningspeed = 0.5f,attack = 1f,harvestLevel = 0)
	@CompoHandle(modifier = 0.5f,durability = -100)
	@CompoExtra(extraDurability = 150)
	public static final Material coal=new $Material(nameCoal,colorCoal)
			.addItemAsIngot(net.minecraft.init.Items.COAL)
			.addTrait(carbonizing);

	@Compo(nameSunStone)
	@CompoHead(durability = 545,miningspeed = 5.75f,attack = 6.25f,harvestLevel = 3)
	@CompoHandle(modifier = 1.1f, durability = 50)
	@CompoExtra(extraDurability = 70)
	@CompoBow(drawSpeed = 2.75f,range=1,bonusDamage = 4)
	public static final Material sunStone=new $Material(nameSunStone,colorSunStone)
			.addItemAsIngot(Items.sunStone)
			.addTrait(sunPower)
			.addTrait(flammable,HEAD)
			.addTrait(sunPower,HEAD);

	@Compo(nameMoonStone)
	@CompoHead(durability = 545,miningspeed = 6.25f,attack = 5.75f,harvestLevel = 3)
	@CompoHandle(modifier = 1.1f, durability = 50)
	@CompoExtra(extraDurability = 70)
	@CompoBow(drawSpeed = 2.75f,range=1,bonusDamage = 4)
	public static final Material moonStone=new $Material(nameMoonStone,colorMoonStone)
			.addItemAsIngot(Items.moonStone)
			.addTrait(moonPower)
			.addTrait(moonlight,HEAD)
			.addTrait(moonPower,HEAD);

	@Compo(nameEnderDragonSquama)
	@CompoHead(durability = 675,miningspeed = 5.8f, attack = 6.7f,harvestLevel = 2)
	@CompoHandle(modifier = 1.05f, durability = 40)
	@CompoExtra(extraDurability = 120)
	@CompoFletching(accuracy = 0.8f,modifier = 3.5f)
	public static final Material enderDragonSquama=new $Material(nameEnderDragonSquama,colorEnderDragonSquama)
			.addItemAsIngot(Items.enderDragonSquama)
			.addTrait(enderference)
			.addTrait(dense)
			.addTrait(lionheart,HEAD)
			.addTrait(enderference,HEAD);

	@Compo(nameFulgurite)
	@CompoHead(durability = 880,miningspeed = 5.36f, attack = 7.5f, harvestLevel = 4)
	@CompoExtra(extraDurability = 50)
	public static final Material fulgurite=new $Material(nameFulgurite,colorFulgurite)
			.addItemAsIngot(Blocks.blockFulgurite)
			.addTrait(thundering);

	@Compo(nameIrisia)
	@CompoHead(durability = 130,miningspeed = 7.7f,attack = 9.3f,harvestLevel = 4)
	@CompoHandle(modifier = 0.75f,durability = 0)
	@CompoExtra(extraDurability = 20)
	public static final Material irisia=new $Material(nameIrisia,colorIrisia)
			.addItemAsIngot(Items.ingotIrisia)
			.addTrait(lightweight);

	@Compo(nameTreeRoot)
	@CompoHead(durability = 165,miningspeed = 3.6f,attack = 3.2f,harvestLevel = 1)
	@CompoHandle(modifier = 0.9f,durability = 40)
	@CompoExtra(extraDurability = 25)
	@CompoBow(drawSpeed = 0.8f,range=1.15f,bonusDamage = 0.5f)
	@CompoArrowShaft(modifier = 1.2f,bonusAmmo = 25)
	public static final Material treeRoot=new $Material(nameTreeRoot,colorTreeRoot)
			.addItemAsIngot(Items.treeRoot)
			.addTrait(ecological)
			.addTrait(dense);

	@Compo(nameFlesh)
	@CompoHead(durability = 220,miningspeed = 3.8f,attack = 4,harvestLevel = 0)
	@CompoHandle(modifier = 0.8f,durability = -50)
	@CompoExtra(extraDurability = 59)
	public static final Material flesh=new $Material(nameFlesh,colorFlesh)
			.addItemAsIngot(Items.flesh)
			.addTrait(gluttonic);

	@Compo(nameBrokenBedrock)
	@CompoHead(durability = 1850,miningspeed = 0.5f,attack = 1.5f,harvestLevel = 4)
	@CompoHandle(modifier = 1.35f,durability = 160)
	@CompoExtra(extraDurability = 270)
	public static Material brokenBedrock=new $Material(nameBrokenBedrock, colorBrokenBedrock)
			.addItemAsIngot(Items.brokenBedrock)
			.addTrait(dense,HEAD)
			.addTrait(duritos);

	@Compo(nameCloud)
	@CompoHead(durability = 20,miningspeed = 0.5f,attack = 0,harvestLevel = 0)
	@CompoExtra(extraDurability = -300)
	public static Material cloud=new $Material(nameCloud, colorCloud)
			.addItemAsIngot(Blocks.blockCloud)
			.addTrait(squeaky,HEAD)
			.addTrait(lightweight,EXTRA);

	@Compo(nameOpal)
	@CompoHead(durability = 230,miningspeed = 3.1f,attack = 3.7f,harvestLevel = 0)
	@CompoHandle(modifier = 0.84f,durability = -45)
	@CompoExtra(extraDurability = 115)
	public static Material opal=new $Material(nameOpal, colorOpal)
			.addItemAsIngot(Items.opal)
			.addTrait(gorgeous);

	@Compo(nameTopaz)
	@CompoHead(durability = 490,miningspeed = 5.6f,attack = 4.4f,harvestLevel = 3)
	@CompoHandle(modifier = 1.15f,durability = 90)
	@CompoExtra(extraDurability = 280)
	public static Material topaz=new $Material(nameTopaz, colorTopaz)
			.addItemAsIngot(Items.topaz)
			.addTrait(peaceEnergetic);

	@Compo(nameTanzanite)
	@CompoHead(durability = 730,miningspeed = 6.2f,attack = 6.9f,harvestLevel = 3)
	@CompoHandle(modifier = 1.15f,durability = 90)
	@CompoExtra(extraDurability = 310)
	public static Material tanzanite=new $Material(nameTanzanite, colorTanzanite)
			.addItemAsIngot(Items.tanzanite)
			.addTrait(hyper);

	@Compo(nameCordierite)
	@CompoHead(durability = 425,miningspeed = 5.9f,attack = 3.9f,harvestLevel = 3)
	@CompoHandle(modifier = 1.05f,durability = 35)
	@CompoExtra(extraDurability = 215)
	public static Material cordierite=new $Material(nameCordierite, colorCordierite)
			.addItemAsIngot(Items.cordierite)
			.addTrait(dichroic);

	@Compo(namePrehnite)
	@CompoHead(durability = 370,miningspeed = 4.1f,attack = 3.3f,harvestLevel = 3)
	@CompoHandle(modifier = 0.95f,durability = 20)
	@CompoExtra(extraDurability = 55)
	public static Material prehnite=new $Material(namePrehnite, colorPrehnite)
			.addItemAsIngot(Items.prehnite)
			.addTrait(lifeInspiring);

	@Compo(nameProustite)
	@CompoHead(durability = 290,miningspeed = 3.5f,attack = 4.7f,harvestLevel = 2)
	@CompoHandle(modifier = 0.85f,durability = 80)
	@CompoExtra(extraDurability = 35)
	public static Material proustite=new $Material(nameProustite, colorProustite)
			.addItemAsIngot(Items.proustite);

	public static Material cloudStorm=new Material("cloud_storm", Colors.DarkBlue);
	public static Material skyCrystal=new Material("sky_crystal", Colors.Aqua);
	public static Material stormCrystal=new Material("storm_crystal", Colors.DarkBlue);
	public static Material antiGraCrystal=new Material("anti_gra_crystal", Colors.DarkGreen);
	public static Material phantomCrystal=new Material("phantom_crystal", Colors.Gray);
	public static Material paradiseMetal=new Material("paradise_metal", Colors.Yellow);

}
